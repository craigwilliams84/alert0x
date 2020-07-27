package eth.craig.alert0x.service;

import eth.craig.alert0x.spec.Criterion;

public interface CriterionHandler {

    boolean isSupported(Criterion criterion);

    void handle(String id, Criterion criterion);
}
