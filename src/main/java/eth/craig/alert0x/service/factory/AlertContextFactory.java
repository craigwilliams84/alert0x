package eth.craig.alert0x.service.factory;

import eth.craig.alert0x.model.alert.AlertContext;

public interface AlertContextFactory<T> {

    AlertContext build(T input);
}
