package fr.d2factory.libraryapp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.d2factory.libraryapp.book.Book;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Contains some util method
 */
public class UtilMethod {

    /**
     * Read a JSON file containing books informations and return the corresponding list of books
     *
     * @param filePath path of the file to read
     * @return List of book read
     * @throws IOException in case there is any problem with the reading of the file
     */
    static public List<Book> jsonFileToObject(String filePath) throws IOException {
        List<Book> result = new ObjectMapper().readValue(new File(filePath), new TypeReference<List<Book>>() {
        });
        return result;
    }
}
