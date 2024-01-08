package az.egov.libraryproject.repository;

import az.egov.libraryproject.model.entity.Author;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepositoryImplementation<Author, Long> {
}