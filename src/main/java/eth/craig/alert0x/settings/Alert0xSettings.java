package eth.craig.alert0x.settings;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Alert0xSettings {

    private long emailCacheExpiry;

    private String mailgunApiKey;

    private String mailgunBaseUrl;

    public Alert0xSettings(@Value("${alert0x.email.cacheExpiry}") String emailCacheExpiry,
                           @Value("${alert0x.mailgun.apiKey}") String mailgunApiKey,
                           @Value("${alert0x.mailgun.baseUrl}") String mailgunBaseUrl) {
        this.emailCacheExpiry = Long.parseLong(emailCacheExpiry);
        this.mailgunApiKey = mailgunApiKey;
        this.mailgunBaseUrl = mailgunBaseUrl;
    }
}
