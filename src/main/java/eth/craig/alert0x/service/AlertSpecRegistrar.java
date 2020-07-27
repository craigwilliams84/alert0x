package eth.craig.alert0x.service;

import eth.craig.alert0x.spec.AlertSpec;

import java.util.List;
import java.util.Map;

public interface AlertSpecRegistrar {

    void register(AlertSpec alertSpec);

    void register(List<AlertSpec> alertSpecs);

    Map<String, AlertSpec> getRegisteredAlertSpecs();
}
