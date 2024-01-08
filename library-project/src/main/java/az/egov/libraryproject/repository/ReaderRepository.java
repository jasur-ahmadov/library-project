package az.egov.libraryproject.repository;

import az.egov.libraryproject.model.entity.Reader;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepositoryImplementation<Reader, Long> {
    Optional<Reader> findByUser_Email(String username);
}