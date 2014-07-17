package de.holisticon.monitoring.tools.metricsgrabber.core.tools;

import com.google.gson.Gson;

import javax.annotation.Nonnull;
import java.io.*;

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
            Reader reader = new FileReader(fileName);
            return new Gson().fromJson(reader,type);

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File passed filename '" + fileName + "' can not be read.",e);
        } catch (Exception e) {
            throw new IllegalStateException("File passed filename '" + fileName + "' can not be deserialized to type '" + type.getCanonicalName() + "' - Please check validity of json",e);
        }
    }

    /**
     * Reads a resource and tries to deserialize it to the passed type.
     * @param resourceName the name of the resource to parse
     * @param type the type of the class the json should be deserialized to
     * @param <T> the generic type for deserialization
     * @return the deserialized json
     */
    public static <T> T readJsonFromResource (@Nonnull String resourceName, @Nonnull Class<T> type) {

        try {

            InputStream inputStream = JsonUtil.class.getResourceAsStream(resourceName);

            if (inputStream == null) {
                throw new IllegalArgumentException("Passed resource name '" + resourceName + "' can not be read.");
            }

            return new Gson().fromJson(new InputStreamReader(inputStream),type);

        }  catch (Exception e) {
            throw new IllegalStateException("passed resource name '" + resourceName + "' can not be deserialized to type '" + type.getCanonicalName() + "' - Please check validity of json",e);
        }
    }



}
