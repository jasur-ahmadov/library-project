package az.egov.libraryproject.controller;

import az.egov.libraryproject.model.dto.AuthorDto;
import az.egov.libraryproject.model.dto.ReaderDto;
import az.egov.libraryproject.model.groups.ValidAuthor;
import az.egov.libraryproject.model.entity.Author;
import az.egov.libraryproject.service.AuthorService;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated(ValidAuthor.class)
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    // get all authors
    @GetMapping
    @PreAuthorize("hasAuthority('read:authors')")
    @ResponseStatus(HttpStatus.OK)
    public Page<AuthorDto> getAll(
            @And({
                    @Spec(path = "name", spec = Like.class),
                    @Spec(path = "surname", spec = Like.class)
            })
            Specification<Author> spec,
            Pageable pageable) {
        return service.getAll(spec, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read:authors_by_id')")
    public AuthorDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // create author
    @PostMapping
    @PreAuthorize("hasAuthority('create:authors')")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto create(@RequestBody @Valid AuthorDto authorDto) {
        return service.create(authorDto);
    }

    // delete author
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('delete:authors')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // update author
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('update:authors')")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDto update(@RequestBody @Valid AuthorDto authorDto, @PathVariable Long id) {
        return service.update(authorDto, id);
    }
}