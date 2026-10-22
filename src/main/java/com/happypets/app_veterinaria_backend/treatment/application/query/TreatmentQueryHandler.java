package com.happypets.app_veterinaria_backend.treatment.application.query;

import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.InventoryBatch;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.port.InventoryBatchRepositoryPort;
import com.happypets.app_veterinaria_backend.treatment.domain.entity.Treatment;
import com.happypets.app_veterinaria_backend.treatment.domain.entity.TreatmentProduct;
import com.happypets.app_veterinaria_backend.treatment.domain.exception.TreatmentNotFoundException;
import com.happypets.app_veterinaria_backend.treatment.domain.port.TreatmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentQueryHandler {

    private final TreatmentRepositoryPort treatmentPort;
    private final InventoryBatchRepositoryPort batchPort;

    @Transactional(readOnly = true)
    public TreatmentView getById(Long id) {
        Treatment treatment = treatmentPort.findById(id)
                .orElseThrow(() -> new TreatmentNotFoundException(id));
        return toView(treatment);
    }

    @Transactional(readOnly = true)
    public List<TreatmentView> getAll() {
        return treatmentPort.findAll().stream().map(this::toView).toList();
    }

    private TreatmentView toView(Treatment t) {
        return new TreatmentView(
                t.getIdTreatment(), t.getName(), t.getDescription(), t.getIntervalDays(),
                t.isStatus(), t.getSpecies(), t.getProducts(), estimateCost(t));
    }

    /**
     * Costo estimado usando el lote más antiguo de cada producto (UEPS).
     * Es una referencia: el costo real se calcula al aplicar el tratamiento.
     */
    private BigDecimal estimateCost(Treatment t) {
        BigDecimal total = BigDecimal.ZERO;
        for (TreatmentProduct p : t.getProducts()) {
            List<InventoryBatch> batches = batchPort.findAvailableBatchesByProductIdUEPS(p.getProductId());
            if (batches.isEmpty()) {
                return null;
            }
            total = total.add(batches.get(0).getUnitaryCost()
                    .multiply(BigDecimal.valueOf(p.getQuantity())));
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }
}