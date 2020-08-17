package eth.craig.alert0x.service;

import eth.craig.alert0x.exception.ApiKeyNotSetException;
import eth.craig.alert0x.model.ethereum.InternalTransaction;
import eth.craig.alert0x.settings.Alert0xSettings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Component
public class EtherscanClient {

    private RestTemplate restTemplate;

    private Alert0xSettings settings;

    @Autowired
    public EtherscanClient(Alert0xSettings settings) {
        this.settings = settings;
        this.restTemplate = createRestTemplate();
    }

    public List<InternalTransaction> getInternalTransactions(String txHash) {
        final String apiKey = settings.getEtherscanApiKey();

        if (apiKey == null || apiKey.isEmpty()) {
            throw new ApiKeyNotSetException();
        }

        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(settings.getEtherscanBaseUrl())
                .queryParam("module", "account")
                .queryParam("action", "txlistinternal")
                .queryParam("txhash", txHash)
                .queryParam("apiKey", apiKey);

        //log.info("Calling etherscan api: {}", builder.toUriString());

        final ResponseEntity<EtherscanResponse<List<InternalTransaction>>> response =
                restTemplate.exchange(URI.create(builder.toUriString()), HttpMethod.GET, null,
                new ParameterizedTypeReference<EtherscanResponse<List<InternalTransaction>>>() {});

        if (response.getBody().getStatus().equals("0")) {
            throw new RuntimeException(
                    "Error when obtaining internal transactions from etherscan: " + response.getBody().message);
        }

        return response.getBody().getResult();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class EtherscanResponse<T> {

        private String status;

        private String message;

        private T result;
    }

    private RestTemplate createRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();

        return restTemplate;
    }
}
