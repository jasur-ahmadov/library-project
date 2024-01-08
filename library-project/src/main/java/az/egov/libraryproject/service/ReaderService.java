package az.egov.libraryproject.service;

import az.egov.libraryproject.auth.AppUser;
import az.egov.libraryproject.auth.AppUserRole;
import az.egov.libraryproject.exception.NotFoundException;
import az.egov.libraryproject.model.dto.ReaderDto;
import az.egov.libraryproject.model.entity.Reader;
import az.egov.libraryproject.model.mapper.ReaderMapper;
import az.egov.libraryproject.repository.AppUserRepository;
import az.egov.libraryproject.repository.ReaderRepository;
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
public class ReaderService {

    private final ReaderMapper mapper;
    private final ReaderRepository repository;
    private final AppUserRepository userRepository;

    public Page<ReaderDto> getAll(Specification<Reader> spec, Pageable pageable) {
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }

    public ReaderDto getById(Long id, String username) {
        return Optional.of(username)
                .flatMap(userRepository::findByEmail)
                .filter(appUser -> AppUserRole.LIBRARIAN == appUser.getAppUserRole())
                .flatMap(appUser -> repository.findById(id))
                .or(() -> repository.findByUser_Email(username))
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Reader not found with id: " + id));
    }

    public ReaderDto create(ReaderDto readerDto) {
        return Optional.ofNullable(readerDto)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow(IllegalArgumentException::new);
    }

    public void delete(Long id) {
        if (id != null && repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException("Reader not found with id: " + id);
        }
    }

    public ReaderDto update(ReaderDto readerDto, Long id) {
        return Optional.ofNullable(id)
                .flatMap(repository::findById)
                .map(reader -> mapper.toEntity(reader, readerDto))
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Reader not found with id: " + id));
    }
}