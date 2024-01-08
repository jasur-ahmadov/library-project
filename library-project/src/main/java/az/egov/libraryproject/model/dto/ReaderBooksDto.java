package az.egov.libraryproject.model.dto;

import az.egov.libraryproject.model.groups.ValidReaderBook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReaderBooksDto {

    @Null(groups = {ValidReaderBook.class})
    private Long id;

    @NotNull(groups = {ValidReaderBook.class}) // it should be @Null
    private ReaderDto reader;

    @NotNull(groups = {ValidReaderBook.class})
    private BookDto book;

    @NotNull(groups = {ValidReaderBook.class}) // 2022-06-14T12:59:11.332Z
    private LocalDateTime bookTakenDate;

    @NotNull(groups = {ValidReaderBook.class})
    private LocalDateTime bookExpectedDate;

    private LocalDateTime bookBroughtDate;
}