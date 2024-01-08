package az.egov.libraryproject.controller;

import az.egov.libraryproject.model.dto.BookDto;
import az.egov.libraryproject.model.entity.Book;
import az.egov.libraryproject.model.groups.ValidBook;
import az.egov.libraryproject.service.BookService;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated(ValidBook.class)
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // get all books
    @GetMapping
    @PreAuthorize("hasAuthority('read:books')")
    @ResponseStatus(HttpStatus.OK)
    public Page<BookDto> getAll(
            @And({
                    @Spec(path = "name", spec = Like.class),
                    @Spec(path = "count", spec = Equal.class),
                    @Spec(path = "year", spec = GreaterThanOrEqual.class)
            })
            Specification<Book> spec,
            Pageable pageable) {
        return service.getAll(spec, pageable);
    }

    // get book by id
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read:books_by_id')")
    public BookDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // create book
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('create:books')")
    public BookDto create(@RequestBody @Valid BookDto bookDto) {
        return service.create(bookDto);
    }

    // delete book
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('delete:books')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // update book
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('update:books')")
    public BookDto update(@RequestBody @Valid BookDto bookDto, @PathVariable Long id) {
        return service.update(bookDto, id);
    }
}