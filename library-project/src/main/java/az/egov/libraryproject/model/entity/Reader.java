package az.egov.libraryproject.model.entity;

import az.egov.libraryproject.auth.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "readers")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String surname;

    @OneToOne
    private AppUser user;

    @OneToMany(mappedBy = "reader")
    private List<ReaderBooks> readerBooks;
}