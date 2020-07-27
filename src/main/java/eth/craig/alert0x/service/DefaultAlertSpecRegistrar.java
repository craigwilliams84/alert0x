package eth.craig.alert0x.service;

import eth.craig.alert0x.spec.AlertSpec;
import eth.craig.alert0x.spec.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DefaultAlertSpecRegistrar implements AlertSpecRegistrar {

    private List<CriterionHandler> criterionHandlers;

    private Map<String, AlertSpec> registeredAlertSpecs = new HashMap<>();

    @Autowired
    public DefaultAlertSpecRegistrar(List<CriterionHandler> criterionHandlers) {
        this.criterionHandlers = criterionHandlers;
    }

    @Override
    public void register(AlertSpec alertSpec) {
        final Criterion criterion = alertSpec.getCriterion();

        registeredAlertSpecs.put(alertSpec.getId(), alertSpec);

        criterionHandlers
                .stream()
                .filter(handler -> handler.isSupported(criterion))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Unsupported criterion type"))
                .handle(alertSpec.getId(), criterion);
    }

    @Override
    public void register(List<AlertSpec> alertSpecs) {
        alertSpecs.forEach(this::register);
    }

    @Override
    public Map<String, AlertSpec> getRegisteredAlertSpecs() {
        return registeredAlertSpecs;
    }
}
