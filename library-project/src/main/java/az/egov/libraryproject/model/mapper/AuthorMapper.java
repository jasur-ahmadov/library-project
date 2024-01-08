package az.egov.libraryproject.model.mapper;

import az.egov.libraryproject.model.dto.AuthorDto;
import az.egov.libraryproject.model.entity.Author;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {

    @Mapping(target = "books", ignore = true)
    Author toEntity(AuthorDto dto);

    @Mapping(target = "books", ignore = true)
    @Mapping(target = "id", ignore = true)
    Author toEntity(@MappingTarget Author entity, AuthorDto dto);

    AuthorDto toDto(Author entity);
}