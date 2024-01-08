package az.egov.libraryproject.model.dto;

import az.egov.libraryproject.model.groups.ValidReader;
import az.egov.libraryproject.model.groups.ValidReaderBook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReaderDto {

    @Null(groups = {ValidReader.class})
    @NotNull(groups = {ValidReaderBook.class})
    private Long id;

    @Pattern(regexp = "^[A-Z][a-z]{1,99}$", message = "{input.pattern}", groups = {ValidReader.class})
    @NotNull(groups = {ValidReader.class})
    @Null(groups = {ValidReaderBook.class})
    private String name;

    @Pattern(regexp = "^[A-Z][a-z]{1,99}$", message = "{input.pattern}", groups = {ValidReader.class})
    @NotNull(groups = {ValidReader.class})
    @Null(groups = {ValidReaderBook.class})
    private String surname;
}