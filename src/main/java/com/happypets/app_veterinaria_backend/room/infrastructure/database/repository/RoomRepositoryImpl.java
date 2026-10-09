package com.happypets.app_veterinaria_backend.room.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.room.domain.entity.Room;
import com.happypets.app_veterinaria_backend.room.domain.exceptions.RoomNotFoundException;
import com.happypets.app_veterinaria_backend.room.domain.filter.RoomFilter;
import com.happypets.app_veterinaria_backend.room.domain.port.RoomRepositoryPort;
import com.happypets.app_veterinaria_backend.room.infrastructure.database.entity.RoomEntity;
import com.happypets.app_veterinaria_backend.room.infrastructure.database.mapper.RoomEntityMapper;
import com.happypets.app_veterinaria_backend.room.infrastructure.database.specification.RoomSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Room repository implementation class
 *
 */
@RequiredArgsConstructor
@Repository
public class RoomRepositoryImpl implements RoomRepositoryPort {

    private static final Set<String> SORTABLE_FIELDS =
            Set.of("id", "name", "location", "number", "status", "createdAt");

    //Dependencies
    private final QueryRoomRepository queryRoomRepository;
    private final RoomEntityMapper roomEntityMapper;

    /**
     * Method to create a new room
     *
     */
    @Override
    public Room create(Room room) {
        RoomEntity saved = queryRoomRepository.save(roomEntityMapper.toEntity(room));
        return roomEntityMapper.toDomain(saved);
    }

    /**
     * Method to update the room
     *
     */
    @Override
    public Room update(Room room) {
        RoomEntity entity = queryRoomRepository.findById(room.getId())
                .orElseThrow(() -> new RoomNotFoundException("Sala no encontrada: " + room.getId()));

        entity.setName(room.getName());
        entity.setNormalizedName(room.getNormalizedName());
        entity.setLocation(room.getLocation());
        entity.setDescription(room.getDescription());
        entity.setNumber(room.getNumber());
        entity.setStatus(room.isStatus());

        RoomEntity updated = queryRoomRepository.save(entity);
        return roomEntityMapper.toDomain(updated);
    }

    /**
     * Principal method to find by id
     *
     */
    @Override
    public Optional<Room> findById(Long id) {
        return queryRoomRepository.findById(id).map(roomEntityMapper::toDomain);
    }

    /**
     * Pagination get all with filters
     *
     */
    @Override
    public PaginationResult<Room> findAll(RoomFilter filter, PaginationQuery paginationQuery) {

        Pageable pageable = buildPageable(paginationQuery);

        Page<RoomEntity> pageResult = queryRoomRepository
                .findAll(RoomSpecification.withCriteria(filter), pageable);

        List<Room> rooms = pageResult.getContent()
                .stream()
                .map(roomEntityMapper::toDomain)
                .toList();

        return new PaginationResult<>(
                rooms,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );
    }

    /**
     * Validation exist by number method
     *
     */
    @Override
    public boolean existsByNumber(int number) {
        return queryRoomRepository.existsByNumber(number);
    }

    /**
     * Validation exist by normalized name method
     *
     */
    @Override
    public boolean existsByNormalizedName(String normalizedName) {
        return queryRoomRepository.existsByNormalizedName(normalizedName);
    }

    /**
     * Validation exist by number method without itself
     *
     */
    @Override
    public boolean existsByNumberAndIdNot(int number, Long id) {
        return queryRoomRepository.existsByNumberAndIdNot(number, id);
    }

    /**
     * Validation exist by normalized name method without itself
     *
     */
    @Override
    public boolean existsByNormalizedNameAndIdNot(String normalizedName, Long id) {
        return queryRoomRepository.existsByNormalizedNameAndIdNot(normalizedName, id);
    }

    /**
     * Method to build the page helper
     *
     */
    private Pageable buildPageable(PaginationQuery paginationQuery) {
        String sortBy = paginationQuery.getSortBy();

        if (sortBy == null || sortBy.isBlank() || !SORTABLE_FIELDS.contains(sortBy)) {
            return PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize(), Sort.by("id"));
        }

        Sort.Direction direction = "DESC".equalsIgnoreCase(paginationQuery.getDirection())
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize(), Sort.by(direction, sortBy));
    }
}
