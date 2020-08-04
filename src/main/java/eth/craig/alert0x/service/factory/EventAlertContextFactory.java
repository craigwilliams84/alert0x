package eth.craig.alert0x.service.factory;

import eth.craig.alert0x.model.alert.AlertContext;
import eth.craig.alert0x.model.event.ContractEvent;
import eth.craig.alert0x.model.event.EventParameter;
import eth.craig.alert0x.model.event.TransactionEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static eth.craig.alert0x.model.alert.AlertContextKeys.*;

@Component
public class EventAlertContextFactory implements AlertContextFactory<ContractEvent> {

    @Override
    public AlertContext build(ContractEvent input) {
        final Map<String, String> values = new HashMap<>();

        putIfNotEmpty(TX_HASH, input.getTransactionHash(), values);

        putIfNotEmpty(MONITOR_ID, input.getMonitorId(), values);

        setParameterContextValues(input.getIndexedParameters(), INDEXED_PARAM_PREFIX, values);
        setParameterContextValues(input.getIndexedParameters(), PARAM_PREFIX, values);

        return AlertContext
                .builder()
                .values(values)
                .build();
    }

    private void setParameterContextValues(List<EventParameter> eventParameters,
                                           String prefix,
                                           Map<String, String> contextValues) {
        for (int i = 0; i < eventParameters.size(); i++) {
            contextValues.put(prefix + i, eventParameters.get(i).getValue());
        }
    }

    private void putIfNotEmpty(String key, String value, Map<String, String> theMap) {
        if (!StringUtils.isEmpty(value)) {
            theMap.put(key, value);
        }
    }
}
