package az.egov.libraryproject.model.mapper;

import az.egov.libraryproject.model.dto.BookDto;
import az.egov.libraryproject.model.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = AuthorMapper.class)
public interface BookMapper {

    BookDto toDto(Book entity);

    Book toEntity(BookDto dto);

    @Mapping(target = "id", ignore = true)
    Book toEntity(@MappingTarget Book entity, BookDto dto);
}