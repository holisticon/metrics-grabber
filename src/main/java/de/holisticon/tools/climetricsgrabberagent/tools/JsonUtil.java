package de.holisticon.tools.climetricsgrabberagent.tools;

import com.google.gson.Gson;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Utility class for deserializing json strings.
 * @author Tobias Gindler, Holisticon AG on 17.07.14.
 */
public final class JsonUtil {

    /**
     * Hide constructor.
     */
    private JsonUtil() {

    }

    /**
     * Reads a file and tries to deserialize it to the passed type.
     * @param fileName the name of the file to parse
     * @param type the type of the class the json should be deserialized to
     * @param <T> the generic type for deserialization
     * @return the deserialized json
     */
    public static <T> T readJsonFromFile (@Nonnull String fileName, @Nonnull Class<T> type) {

        try {
            FileReader fileReader = new FileReader(fileName);
            return new Gson().fromJson(fileReader,type);

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File passed filename '" + fileName + "' can not be read.",e);
        } catch (Exception e) {
            throw new IllegalStateException("File passed filename '" + fileName + "' can not be deserialized to type '" + type.getCanonicalName() + "' - Please check validity of json",e);
        }
    }

}
