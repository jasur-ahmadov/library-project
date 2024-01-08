package az.egov.libraryproject.controller;

import az.egov.libraryproject.model.dto.ReaderBooksDto;
import az.egov.libraryproject.model.entity.ReaderBooks;
import az.egov.libraryproject.model.groups.ValidReaderBook;
import az.egov.libraryproject.service.ReaderBooksService;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated(ValidReaderBook.class)
@RequestMapping("/api/reader_books")
public class ReaderBooksController {

    private final ReaderBooksService service;

    public ReaderBooksController(ReaderBooksService service) {
        this.service = service;
    }

    // get all readerBooks
    @GetMapping
    @PreAuthorize("hasAuthority('read:reader_books')")
    @ResponseStatus(HttpStatus.OK)
    public Page<ReaderBooksDto> getAll(
            @AuthenticationPrincipal String username,
            @RequestParam(required = false) Long userId,
            @And({
                    @Spec(path = "reader.id", params = "readerId", spec = Equal.class),
                    @Spec(path = "book.id", params = "bookId", spec = Equal.class),
                    @Spec(path = "bookTakenDate", spec = GreaterThanOrEqual.class),
                    @Spec(path = "bookExpectedDate", spec = GreaterThanOrEqual.class),
                    @Spec(path = "bookBroughtDate", spec = GreaterThanOrEqual.class)
            })
            Specification<ReaderBooks> spec,
            Pageable pageable) {
        return service.getAll(username, userId, spec, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read:reader_books_by_id')")
    public ReaderBooksDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // create readerBooks
    @PostMapping
    @PreAuthorize("hasAuthority('create:reader_books')")
    @ResponseStatus(HttpStatus.CREATED)
    public ReaderBooksDto create(@RequestBody @Valid ReaderBooksDto readerBooksDto) {
        return service.create(readerBooksDto);
    }

    // delete readerBooks by id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('delete:reader_books')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // update readerBooks by id
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('update:reader_books')")
    @ResponseStatus(HttpStatus.OK)
    public ReaderBooksDto update(@RequestBody @Valid ReaderBooksDto readerBooksDto, @PathVariable Long id) {
        return service.update(readerBooksDto, id);
    }
}