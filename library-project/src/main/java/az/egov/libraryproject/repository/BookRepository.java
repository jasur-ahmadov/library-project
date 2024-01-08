package az.egov.libraryproject.repository;

import az.egov.libraryproject.model.entity.Book;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepositoryImplementation<Book, Long> {
}