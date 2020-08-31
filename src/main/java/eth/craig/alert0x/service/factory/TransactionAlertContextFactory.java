package eth.craig.alert0x.service.factory;

import eth.craig.alert0x.model.alert.AlertContext;
import eth.craig.alert0x.model.alert.AlertContextKeys;
import eth.craig.alert0x.model.event.TransactionEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static eth.craig.alert0x.model.alert.AlertContextKeys.*;

@Component
public class TransactionAlertContextFactory implements AlertContextFactory<TransactionEvent> {

    @Override
    public AlertContext build(TransactionEvent input) {
        final Map<String, String> values = new HashMap<>();

        putIfNotEmpty(FROM, input.getFrom(), values);
        putIfNotEmpty(TO, input.getTo(), values);
        putIfNotEmpty(VALUE, input.getValue().toString(), values);

        if (input.getValue() != null) {
            putIfNotEmpty(ETH_VALUE, Convert.fromWei(
                    new BigDecimal(input.getValue()), Convert.Unit.ETHER).toString(), values);
        }
        putIfNotEmpty(TX_HASH, input.getTransactionHash(), values);

        putIfNotEmpty(SPEC_ID, input.getMonitorId(), values);

        putIfNotEmpty(STATUS, input.getStatus().name(), values);

        return AlertContext
                .builder()
                .values(values)
                .build();
    }

    private void putIfNotEmpty(String key, String value, Map<String, String> theMap) {
        if (!StringUtils.isEmpty(value)) {
            theMap.put(key, value);
        }
    }
}
