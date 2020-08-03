package eth.craig.alert0x.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSON {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String stringify(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "<Error parsing>";
        }
    }
}
