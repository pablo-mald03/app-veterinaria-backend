package com.happypets.app_veterinaria_backend.vaccinationrecord.application.command;

import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.InventoryBatch;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.port.InventoryBatchRepositoryPort;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.InventoryMovement;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.MovementType;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.port.InventoryMovementRepositoryPort;
import com.happypets.app_veterinaria_backend.sale.domain.exception.InsufficientStockException;
import com.happypets.app_veterinaria_backend.treatment.domain.entity.Treatment;
import com.happypets.app_veterinaria_backend.treatment.domain.entity.TreatmentProduct;
import com.happypets.app_veterinaria_backend.treatment.domain.exception.TreatmentNotFoundException;
import com.happypets.app_veterinaria_backend.treatment.domain.port.TreatmentRepositoryPort;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.exception.VaccinationCardNotFoundException;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.model.VaccinationCard;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.port.VaccinationCardRepositoryPort;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.exception.SpeciesNotAllowedException;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model.TreatmentApplicationItem;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model.VaccinationRecord;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.port.PetSpeciesPort;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.port.VaccinationRecordRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterVaccinationRecordHandler {

    private static final ZoneId GUATEMALA = ZoneId.of("America/Guatemala");

    private final VaccinationRecordRepositoryPort recordPort;
    private final VaccinationCardRepositoryPort cardPort;
    private final TreatmentRepositoryPort treatmentPort;
    private final PetSpeciesPort petSpeciesPort;
    private final InventoryBatchRepositoryPort batchPort;
    private final InventoryMovementRepositoryPort movementPort;

    @Transactional(rollbackFor = Exception.class)
    public VaccinationRecord execute(RegisterVaccinationRecordCommand command) {

        VaccinationCard card = cardPort.findById(command.idCard())
                .orElseThrow(() -> new VaccinationCardNotFoundException(command.idCard()));

        Treatment treatment = treatmentPort.findById(command.idTreatment())
                .orElseThrow(() -> new TreatmentNotFoundException(command.idTreatment()));

        if (!treatment.isStatus()) {
            throw new IllegalStateException("El tratamiento '" + treatment.getName() + "' está desactivado");
        }

        String species = petSpeciesPort.findSpeciesByPetId(card.getIdPet())
                .orElseThrow(() -> new IllegalStateException("La mascota no tiene especie registrada"));
        if (!treatment.appliesTo(species)) {
            throw new SpeciesNotAllowedException(treatment.getName(), species);
        }

        // 1) Validar stock de todos los productos antes de descontar nada
        for (TreatmentProduct product : treatment.getProducts()) {
            long available = batchPort.getTotalAvailableStock(product.getProductId());
            if (available < product.getQuantity()) {
                throw new InsufficientStockException(
                        "Stock insuficiente para producto ID " + product.getProductId()
                                + ". Requerido: " + product.getQuantity() + ", Disponible: " + available);
            }
        }

        // 2) Descontar lotes por UEPS
        List<TreatmentApplicationItem> items = new ArrayList<>();
        for (TreatmentProduct product : treatment.getProducts()) {
            items.addAll(consumeBatches(product));
        }

        BigDecimal totalCost = items.stream()
                .map(i -> i.getUnitCost().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        int applicationNumber = (int) (recordPort.countByIdCardAndIdTreatment(
                card.getIdCard(), treatment.getIdTreatment()) + 1);

        VaccinationRecord saved = recordPort.save(VaccinationRecord.register(
                card.getIdCard(), treatment, command.idDoctor(), applicationNumber,
                command.applicationDate(), command.notes(), totalCost, items));

        // 3) Movimientos OUT ligados a la aplicación
        LocalDateTime now = LocalDateTime.now(GUATEMALA);
        for (TreatmentApplicationItem item : items) {
            movementPort.save(InventoryMovement.builder()
                    .type(MovementType.OUT)
                    .quantity(item.getQuantity())
                    .unitCost(item.getUnitCost())
                    .date(now)
                    .origin("TRATAMIENTO " + treatment.getName() + " - CARNET " + card.getIdCard())
                    .productId(item.getProductId())
                    .batchId(item.getBatchId())
                    .treatmentApplicationId(saved.getIdRecord())
                    .build());
        }

        return saved;
    }

    private List<TreatmentApplicationItem> consumeBatches(TreatmentProduct product) {
        long remaining = product.getQuantity();
        List<TreatmentApplicationItem> consumed = new ArrayList<>();

        for (InventoryBatch batch : batchPort.findAvailableBatchesByProductIdUEPSWithLock(product.getProductId())) {
            if (remaining <= 0) break;

            long current = batch.getAvailableQuantity();
            long taken = Math.min(current, remaining);
            long newAvailable = current - taken;

            batchPort.updateAvailableQuantity(batch.getId(), newAvailable, newAvailable > 0);

            consumed.add(TreatmentApplicationItem.builder()
                    .productId(product.getProductId())
                    .batchId(batch.getId())
                    .quantity(taken)
                    .unitCost(batch.getUnitaryCost())
                    .build());

            remaining -= taken;
        }

        if (remaining > 0) {
            throw new IllegalStateException("Inconsistencia de inventario para producto ID " + product.getProductId());
        }
        return consumed;
    }
}