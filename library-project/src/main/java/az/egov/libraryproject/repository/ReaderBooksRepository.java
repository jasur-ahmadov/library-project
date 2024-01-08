package az.egov.libraryproject.repository;

import az.egov.libraryproject.model.entity.ReaderBooks;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReaderBooksRepository extends JpaRepositoryImplementation<ReaderBooks, Long> {
    Optional<ReaderBooks> findByIdAndReader_User_Email(Long id, String username);
}