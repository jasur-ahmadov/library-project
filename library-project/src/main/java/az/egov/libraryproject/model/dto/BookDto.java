package az.egov.libraryproject.model.dto;

import az.egov.libraryproject.model.groups.ValidBook;
import az.egov.libraryproject.model.groups.ValidReaderBook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto {

    @Null(groups = {ValidBook.class})
    @NotNull(groups = {ValidReaderBook.class})
    private Long id;

    @Pattern(regexp = "^[A-Za-z\\d\\s\\-_,\\.;:()]+$", message = "{input.capital}", groups = {ValidBook.class})
    @NotNull(groups = {ValidBook.class})
    @Null(groups = {ValidReaderBook.class})
    private String name;

    @Max(value = 2022, message = "{input.futureDate}", groups = {ValidBook.class}) // custom annotation usage
    @NotNull(groups = {ValidBook.class})
    @Null(groups = {ValidReaderBook.class})
    private Integer year;

    @PositiveOrZero(groups = {ValidBook.class})
    @NotNull(groups = {ValidBook.class})
    @Null(groups = {ValidReaderBook.class})
    private Integer count;

    @Valid
    @NotNull(groups = {ValidBook.class})
    @Null(groups = {ValidReaderBook.class})
    private Set<AuthorDto> authors;
}