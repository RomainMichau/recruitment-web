package fr.d2factory.libraryapp.book;

import java.util.Objects;

public class ISBN {

    public void setIsbnCode(long isbnCode) {
        this.isbnCode = isbnCode;
    }

    public long getIsbnCode() {
        return isbnCode;
    }

    public ISBN() {
    }
    long isbnCode;

    public ISBN(long isbnCode) {
        this.isbnCode = isbnCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ISBN isbn = (ISBN) o;
        return isbnCode == isbn.isbnCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbnCode);
    }
}
