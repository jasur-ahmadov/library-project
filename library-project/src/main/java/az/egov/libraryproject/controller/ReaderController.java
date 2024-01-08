package az.egov.libraryproject.controller;

import az.egov.libraryproject.model.dto.ReaderDto;
import az.egov.libraryproject.model.entity.Reader;
import az.egov.libraryproject.model.groups.ValidReader;
import az.egov.libraryproject.repository.AppUserRepository;
import az.egov.libraryproject.service.ReaderService;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
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
import java.security.Principal;

@RestController
@Validated(ValidReader.class)
@RequestMapping("/api/readers")
public class ReaderController {
    private final ReaderService service;

    public ReaderController(ReaderService service) {
        this.service = service;
    }

    // get all readers
    @GetMapping
    @PreAuthorize("hasAuthority('read:readers')")
    @ResponseStatus(HttpStatus.OK)
    public Page<ReaderDto> getAll(
            @And({
                    @Spec(path = "name", spec = Like.class),
                    @Spec(path = "surname", spec = Like.class)
            })
            Specification<Reader> spec,
            Pageable pageable) {
        return service.getAll(spec, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read:readers_by_id')")
    public ReaderDto getById(@PathVariable("id") Long userId,
                             @AuthenticationPrincipal String username) {
        return service.getById(userId, username);
    }

    // create reader
    @PostMapping
    @PreAuthorize("hasAuthority('create:readers')")
    @ResponseStatus(HttpStatus.CREATED)
    public ReaderDto create(@RequestBody @Valid ReaderDto readerDto) {
        return service.create(readerDto);
    }

    // delete reader by id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('delete:readers')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // update reader by id
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('update:readers')")
    @ResponseStatus(HttpStatus.OK)
    public ReaderDto update(@RequestBody @Valid ReaderDto readerDto, @PathVariable Long id) {
        return service.update(readerDto, id);
    }
}