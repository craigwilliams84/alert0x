package eth.craig.alert0x.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import eth.craig.alert0x.exception.ApiKeyNotSetException;
import eth.craig.alert0x.model.EmailMessage;
import eth.craig.alert0x.settings.Alert0xSettings;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.shade.org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MailgunService implements EmailService {

    private static final String USERNAME = "api";

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private Alert0xSettings settings;

    private RestTemplate restTemplate;

    private Cache<Integer, EmailMessage> sentEmailsCache;

    @Autowired
    public MailgunService(Alert0xSettings settings) {
        this.settings = settings;
        restTemplate = createRestTemplate();
        sentEmailsCache = createCache(EmailMessage.class);
    }

    public void sendEmail(EmailMessage email) {
        if (shouldSendEmail(email)) {
            final String apiKey = settings.getMailgunApiKey();

            if (apiKey == null || apiKey.isEmpty()) {
                throw new ApiKeyNotSetException();
            }

            restTemplate.exchange(settings.getMailgunBaseUrl() + "/messages", HttpMethod.POST,
                    new HttpEntity<>(getValuesFromEmailMessage(email), getHeaders()), String.class);

            sentEmailsCache.put(Integer.valueOf(sentEmailsCache.hashCode()), email);
        } else {
            log.info(String.format("Email to %s with subject %s has already been sent...ignoring.",
                    email.getTo(), email.getSubject()));
        }
    }

    @Scheduled(fixedRateString = "#{alert0xSettings.emailCacheExpiry}")
    public void cleanUpCache() {
        sentEmailsCache.cleanUp();
    }

    private boolean shouldSendEmail(EmailMessage email) {
        return sentEmailsCache.getIfPresent(Integer.valueOf(email.hashCode())) == null;
    }

    private RestTemplate createRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();

        return restTemplate;
    }

    private MultiValueMap<String, Object> getValuesFromEmailMessage(EmailMessage message) {
        final MultiValueMap<String, Object> messageData = new LinkedMultiValueMap<>();

        messageData.add("to", message.getTo());
        messageData.add("from", message.getFrom());
        messageData.add("html", message.getContentText());

        if (!StringUtils.isEmpty(message.getSubject())) {
            messageData.add("subject", message.getSubject());
        }

        return messageData;
    }

    private HttpHeaders getHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        String auth = USERNAME + ":" + settings.getMailgunApiKey();
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")) );
        String authHeader = "Basic " + new String( encodedAuth );
        headers.set(AUTHORIZATION_HEADER, authHeader);

        return headers;
    }

    private <T> Cache<Integer, T> createCache(Class<T> clazz) {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(settings.getEmailCacheExpiry(), TimeUnit.MILLISECONDS)
                .build();
    }
}
