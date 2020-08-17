package eth.craig.alert0x.spec.verifier;


public interface CriterionVerifier<A, E> {

    boolean isMatching(A alert, E event);
}
