package eth.craig.alert0x.model.alert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class AlertContext {

    private Map<String, String> values = new HashMap<>();

    public String getValue(String key) {
        return values.get(key);
    }

}
