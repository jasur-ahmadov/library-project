package az.egov.libraryproject.auth;

import com.google.common.collect.Sets;

import java.util.Set;

import static az.egov.libraryproject.auth.AppPermission.*;

public enum AppUserRole {

    READER(Sets.newHashSet(
            READ_AUTHORS, READ_AUTHORS_BY_ID, READ_BOOKS, READ_BOOKS_BY_ID,
            READ_READERS_BY_ID, READ_READER_BOOKS)),
    LIBRARIAN(Sets.newHashSet(
            READ_AUTHORS, READ_AUTHORS_BY_ID, CREATE_AUTHORS, DELETE_AUTHORS, UPDATE_AUTHORS,
            READ_BOOKS, READ_BOOKS_BY_ID, CREATE_BOOKS, DELETE_BOOKS, UPDATE_BOOKS,
            READ_READERS, READ_READERS_BY_ID, CREATE_READERS, DELETE_READERS, UPDATE_READERS,
            READ_READER_BOOKS, READ_READER_BOOKS_BY_ID, CREATE_READER_BOOKS, DELETE_READER_BOOKS, UPDATE_READER_BOOKS));

    private final Set<AppPermission> permissions;

    AppUserRole(Set<AppPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<AppPermission> getPermissions() {
        return permissions;
    }
}