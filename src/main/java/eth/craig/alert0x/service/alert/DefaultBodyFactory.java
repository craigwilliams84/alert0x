package eth.craig.alert0x.service.alert;

import com.fasterxml.jackson.databind.ObjectMapper;
import eth.craig.alert0x.exception.Alert0xException;
import eth.craig.alert0x.model.alert.AlertContext;
import eth.craig.alert0x.model.alert.HttpPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class DefaultBodyFactory implements HttpPostBodyFactory {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getBody(HttpPost postAlert, AlertContext context) {

        try {
            return objectMapper.writeValueAsString(PostBody
                    .builder()
                    .txHash(context.getValue("txHash"))
                    .reason(postAlert.getReason())
                    .build());
        } catch (Exception e) {
            throw new Alert0xException("Error when creating body", e);
        }
    }

    @Override
    public MediaType getContentType(HttpPost postAlert, AlertContext context) {
        return MediaType.APPLICATION_JSON;
    }

    @Data
    @Builder
    @AllArgsConstructor
    private static final class PostBody {
        private String txHash;

        private String reason;
    }
}
