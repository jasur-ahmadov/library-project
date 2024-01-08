package az.egov.libraryproject.service;

import az.egov.libraryproject.exception.NotFoundException;
import az.egov.libraryproject.model.dto.AuthorDto;
import az.egov.libraryproject.model.dto.ReaderDto;
import az.egov.libraryproject.model.entity.Author;
import az.egov.libraryproject.model.mapper.AuthorMapper;
import az.egov.libraryproject.repository.AuthorRepository;
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
public class AuthorService {

    private final AuthorRepository repository;
    private final AuthorMapper mapper;

    public Page<AuthorDto> getAll(Specification<Author> spec, Pageable pageable) {
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }

    public AuthorDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Author not found with id: " + id));
    }

    public AuthorDto create(AuthorDto authorDto) {
        return Optional.ofNullable(authorDto)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow(IllegalArgumentException::new);
    }

    public void delete(Long id) {
        if (id != null && repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException("Author not found with id: " + id);
        }
    }

    public AuthorDto update(AuthorDto authorDto, Long id) {
        return Optional.ofNullable(id)
                .flatMap(repository::findById)
                .map(author -> mapper.toEntity(author, authorDto))
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Author not found with id: " + id));
    }
}