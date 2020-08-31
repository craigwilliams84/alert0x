package eth.craig.alert0x.service.alert;

import eth.craig.alert0x.exception.Alert0xException;
import eth.craig.alert0x.model.alert.Alert;
import eth.craig.alert0x.model.alert.AlertContext;
import eth.craig.alert0x.model.alert.AlertType;
import eth.craig.alert0x.model.alert.HttpPost;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;

@Component
public class HttpPostAlertHandler implements AlertHandler {

    private HttpPostBodyFactory defaultBodyFactory;

    private RetryTemplate httpPostRetryTemplate;

    private RestTemplateBuilder restTemplateBuilder;

    public HttpPostAlertHandler(final HttpPostBodyFactory defaultBodyFactory,
                                @Qualifier("httpPostRetryTemplate") final RetryTemplate httpPostRetryTemplate,
                                RestTemplateBuilder restTemplateBuilder) {
        this.defaultBodyFactory = defaultBodyFactory;
        this.httpPostRetryTemplate = httpPostRetryTemplate;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public boolean isSupported(Alert alert) {
        return alert.getType() == AlertType.POST;
    }

    @Override
    public void handle(Alert alert, AlertContext context) {
        final HttpPost postAlert = (HttpPost) alert;

        final HttpPostBodyFactory bodyFactory = postAlert.getBodyFactory() == null
                ? defaultBodyFactory : postAlert.getBodyFactory();

        httpPostRetryTemplate.execute(retryContext -> executePost(postAlert, context, bodyFactory));
    }

    protected RestTemplate getRestTemplate() {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }

    private HttpStatus executePost(HttpPost postAlert, AlertContext context, HttpPostBodyFactory bodyFactory) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(bodyFactory.getContentType(postAlert, context));

        final HttpEntity<String> entity = new HttpEntity<>(bodyFactory.getBody(postAlert, context), headers);

        final ResponseEntity<Void> result = getRestTemplate().exchange(
                postAlert.getUrl(), HttpMethod.POST, entity, Void.class);

        if (!result.getStatusCode().is2xxSuccessful()) {
            throw new Alert0xException("Failed to post to url " + postAlert.getUrl());
        }

        return result.getStatusCode();
    }
}
