package com.happypets.app_veterinaria_backend.logs.infrastructure.api.mapper;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.logs.application.getAll.GetAllLogsRequest;
import com.happypets.app_veterinaria_backend.logs.application.getAll.GetAllLogsResponse;
import com.happypets.app_veterinaria_backend.logs.domain.entity.Log;
import com.happypets.app_veterinaria_backend.logs.infrastructure.api.dto.request.GetAllLogsQueryDto;
import com.happypets.app_veterinaria_backend.logs.infrastructure.api.dto.response.GetAllLogsResponseDto;
import com.happypets.app_veterinaria_backend.logs.infrastructure.api.dto.response.LogResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Principal controller mapper
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface LogMapper {

    /**
     * Method to map the request dto to the request
     *
     */
    @Mapping(target = "paginationQuery", source = "paginationQuery")
    GetAllLogsRequest toGetAllLogsRequest(GetAllLogsQueryDto queryDto, PaginationQuery paginationQuery);

    /**
     * Method to map the log response to the response log dto
     *
     */
    LogResponseDto toLogResponseDto(Log log);

    /**
     * Method to map the log response lis to the response log dto
     *
     */
    List<LogResponseDto> toLogResponseDtoList(List<Log> logs);

    /**
     * Mapper helper
     *
     */
    default GetAllLogsResponseDto toGetAllLogsResponseDto(GetAllLogsResponse response) {
        PaginationResult<Log> result = response.getLogs();
        return new GetAllLogsResponseDto(
                toLogResponseDtoList(result.getContent()),
                result.getPage(),
                result.getSize(),
                result.getTotalPages(),
                result.getTotalElements()
        );
    }
}
