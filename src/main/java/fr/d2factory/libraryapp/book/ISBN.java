package fr.d2factory.libraryapp.book;

import java.util.Objects;

/**
 * Represent the ISBN number which identified books
 */
public class ISBN {

    /**
     * ISBN code
     */
    long isbnCode;

    /**
     * Default constructor used by Jackson for deserialize the JSON file
     */
    public ISBN() {
    }

    public long getIsbnCode() {
        return isbnCode;
    }

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
