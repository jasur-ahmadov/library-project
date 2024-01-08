package az.egov.libraryproject.model.dto;

import az.egov.libraryproject.model.groups.ValidBook;
import az.egov.libraryproject.model.groups.ValidAuthor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorDto {

    @Null(groups = {ValidAuthor.class})
    @NotNull(groups = {ValidBook.class})
    private Long id;

    @Pattern(regexp = "^[A-Z][a-z]{1,99}$", message = "{input.pattern}", groups = {ValidAuthor.class})
    @NotNull(groups = {ValidAuthor.class})
    @Null(groups = {ValidBook.class})
    private String name;

    @Pattern(regexp = "^[A-Z][a-z]{1,99}$", message = "{input.pattern}", groups = {ValidAuthor.class})
    @NotNull(groups = {ValidAuthor.class})
    @Null(groups = {ValidBook.class})
    private String surname;
}