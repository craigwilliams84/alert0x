package eth.craig.alert0x.spec.verifier;

import eth.craig.alert0x.model.event.ContractEvent;
import eth.craig.alert0x.model.event.EventParameter;
import eth.craig.alert0x.spec.EventEmitted;

import java.util.List;

public class EventArgumentValueVerifier implements CriterionVerifier<EventEmitted, ContractEvent> {

    @Override
    public boolean isMatching(EventEmitted alert, ContractEvent event) {

        if (alert.getArgValues() == null || alert.getArgValues().isEmpty()) {
            return true;
        }

        for (EventEmitted.ArgValue argValue : alert.getArgValues()) {
            if (!isArgumentMatch(argValue.getValue(), argValue.getIndex(),
                    argValue.isIndexed() ? event.getIndexedParameters() : event.getNonIndexedParameters())) {
                return false;
            }
        }

        return true;
    }

    private boolean isArgumentMatch(String value, int index, List<EventParameter> parameters) {

        return parameters.get(index).getValue().equalsIgnoreCase(value);
    }
}
