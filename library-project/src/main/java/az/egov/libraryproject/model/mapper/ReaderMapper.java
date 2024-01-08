package az.egov.libraryproject.model.mapper;

import az.egov.libraryproject.model.dto.ReaderDto;
import az.egov.libraryproject.model.entity.Reader;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ReaderMapper {

    ReaderDto toDto(Reader entity);

    @Mapping(target = "readerBooks", ignore = true)
    Reader toEntity(ReaderDto dto);

    @Mapping(target = "readerBooks", ignore = true)
    @Mapping(target = "id", ignore = true)
    Reader toEntity(@MappingTarget Reader entity, ReaderDto dto);
}