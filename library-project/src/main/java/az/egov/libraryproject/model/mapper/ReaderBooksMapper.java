package az.egov.libraryproject.model.mapper;

import az.egov.libraryproject.model.dto.ReaderBooksDto;
import az.egov.libraryproject.model.entity.ReaderBooks;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = ReaderMapper.class)
public interface ReaderBooksMapper {

    ReaderBooksDto toDto(ReaderBooks entity);

    ReaderBooks toEntity(ReaderBooksDto dto);

    @Mapping(target = "id", ignore = true)
    ReaderBooks toEntity(@MappingTarget ReaderBooks entity, ReaderBooksDto dto);
}