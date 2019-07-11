package fr.d2factory.libraryapp.util;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.d2factory.libraryapp.book.Book;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UtilMethod {

    static public <T> List<T> jsonFileToObject(String filePath) throws IOException {
        List<T> result=new ObjectMapper().readValue(new File(filePath),new TypeReference<List<T>>(){});
        return result;
    }
}
