package az.egov.libraryproject.auth;

public enum AppPermission {

    READ_AUTHORS("read:authors"),
    READ_AUTHORS_BY_ID("read:authors_by_id"),
    CREATE_AUTHORS("create:authors"),
    DELETE_AUTHORS("delete:authors"),
    UPDATE_AUTHORS("update:authors"),
    READ_BOOKS("read:books"),
    READ_BOOKS_BY_ID("read:books_by_id"),
    CREATE_BOOKS("create:books"),
    DELETE_BOOKS("delete:books"),
    UPDATE_BOOKS("update:books"),
    READ_READERS("read:readers"),
    READ_READERS_BY_ID("read:readers_by_id"),
    CREATE_READERS("create:readers"),
    DELETE_READERS("delete:readers"),
    UPDATE_READERS("update:readers"),
    READ_READER_BOOKS("read:reader_books"),
    READ_READER_BOOKS_BY_ID("read:reader_books_by_id"),
    CREATE_READER_BOOKS("create:reader_books"),
    DELETE_READER_BOOKS("delete:reader_books"),
    UPDATE_READER_BOOKS("update:reader_books");

    private final String permission;

    AppPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}