package eth.craig.alert0x.service.criterion;

import eth.craig.alert0x.spec.Criterion;

public interface CriterionHandler {

    boolean isSupported(Criterion criterion);

    void register(String id, Criterion criterion);
}
