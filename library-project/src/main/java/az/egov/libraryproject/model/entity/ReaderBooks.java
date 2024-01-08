package az.egov.libraryproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reader_books")
public class ReaderBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Reader reader;

    @OneToOne(fetch = FetchType.LAZY)
    private Book book;

    @Column
    private LocalDateTime bookTakenDate;

    @Column
    private LocalDateTime bookExpectedDate;

    @Column
    private LocalDateTime bookBroughtDate;
}