package eth.craig.alert0x.model.alert;

import eth.craig.alert0x.service.alert.HttpPostBodyFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HttpPost implements Alert {

    private String url;

    private String reason;

    private HttpPostBodyFactory bodyFactory;

    @Override
    public AlertType getType() {
        return AlertType.POST;
    }
}
