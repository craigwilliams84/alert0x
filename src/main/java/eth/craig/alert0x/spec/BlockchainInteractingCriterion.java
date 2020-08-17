package eth.craig.alert0x.spec;

import eth.craig.alert0x.service.BlockchainService;

public interface BlockchainInteractingCriterion extends Criterion {

    void setBlockchainService(BlockchainService blockchainService);
}
