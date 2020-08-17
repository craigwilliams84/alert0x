package eth.craig.alert0x.model.alert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HttpPost implements Alert {

    private String url;

    @Override
    public AlertType getType() {
        return AlertType.POST;
    }
}
