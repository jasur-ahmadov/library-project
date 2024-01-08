package az.egov.libraryproject.service;

import az.egov.libraryproject.exception.NotFoundException;
import az.egov.libraryproject.model.dto.AuthorDto;
import az.egov.libraryproject.model.dto.BookDto;
import az.egov.libraryproject.model.entity.Book;
import az.egov.libraryproject.model.mapper.BookMapper;
import az.egov.libraryproject.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookMapper mapper;
    private final BookRepository repository;

    public Page<BookDto> getAll(Specification<Book> spec, Pageable pageable) {
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }

    public BookDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + id));
    }

    public BookDto create(BookDto bookDto) {
        return Optional.ofNullable(bookDto)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow(IllegalArgumentException::new);
    }

    public void delete(Long id) {
        if (id != null && repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException("Book not found with id: " + id);
        }
    }

    public BookDto update(BookDto bookDto, Long id) {
        return Optional.ofNullable(id)
                .flatMap(repository::findById)
                .map(book -> mapper.toEntity(book, bookDto))
                .map(repository::save).map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + id));
    }
}