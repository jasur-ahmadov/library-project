package az.egov.libraryproject.service;

import az.egov.libraryproject.auth.AppUser;
import az.egov.libraryproject.auth.AppUserRole;
import az.egov.libraryproject.exception.NotFoundException;
import az.egov.libraryproject.model.dto.ReaderBooksDto;
import az.egov.libraryproject.model.entity.ReaderBooks;
import az.egov.libraryproject.model.mapper.ReaderBooksMapper;
import az.egov.libraryproject.repository.AppUserRepository;
import az.egov.libraryproject.repository.ReaderBooksRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReaderBooksService {

    private final ReaderBooksMapper mapper;
    private final ReaderBooksRepository repository;
    private final AppUserRepository userRepository;

    public Page<ReaderBooksDto> getAll(String username, Long userId, Specification<ReaderBooks> spec, Pageable pageable) {
        Specification<ReaderBooks> specification = Optional.of(username)
                .flatMap(userRepository::findByEmail)
                .filter(appUser -> AppUserRole.LIBRARIAN != appUser.getAppUserRole())
                .map(AppUser::getId)
                .or(() -> Optional.ofNullable(userId))
                .map(id -> (Specification<ReaderBooks>) ((root, query, cb) -> cb.equal(root.get("reader").get("user").get("id"), id)))
                .map(userSpec -> userSpec.and(spec))
                .orElse(spec);
        return repository.findAll(specification, pageable).map(mapper::toDto);
    }

    public ReaderBooksDto getById(Long id) {
        return findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("ReaderBooks not found with id: " + id));
    }

    @NotNull
    private Optional<ReaderBooks> findById(Long id) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(username)
                .flatMap(userRepository::findByEmail)
                .filter(appUser -> AppUserRole.LIBRARIAN == appUser.getAppUserRole())
                .flatMap(appUser -> repository.findById(id))
                .or(() -> repository.findByIdAndReader_User_Email(id, username));
    }

    public ReaderBooksDto create(ReaderBooksDto readerBooksDto) {
        return Optional.ofNullable(readerBooksDto)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow(IllegalArgumentException::new);

//        Reader reader; // todo find by username
//        return Optional.ofNullable(readerBooksDto)
//                .map(dto -> mapper.toEntity(dto, reader))
//                .map(repository::save)
//                .map(mapper::toDto)
//                .orElseThrow(IllegalArgumentException::new);
    }

    public void delete(Long id) {
        if (id != null && repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException("ReaderBooks not found with id: " + id);
        }
    }

    public ReaderBooksDto update(ReaderBooksDto readerBooksDto, Long id) {
        return Optional.ofNullable(id)
                .flatMap(this::findById)
                .map(readerBooks -> mapper.toEntity(readerBooks, readerBooksDto))
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("ReaderBooks not found with id: " + id));
    }
}