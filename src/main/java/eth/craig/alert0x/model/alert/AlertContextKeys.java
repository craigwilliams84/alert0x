package eth.craig.alert0x.model.alert;

public class AlertContextKeys {

    //        private String hash;
//        private BigInteger nonce;
//        private String blockHash;
//        private BigInteger blockNumber;
//        private BigInteger transactionIndex;
//        private String from;
//        private String to;
//        private BigInteger value;
//        private String nodeName;
//        private String contractAddress;
//        private String data;
//        private String revertReason;
//        private BigInteger timestamp;
//        private TransactionStatus status;
//
//        private String monitorId;

    //For Transactions
    public static final String FROM = "from";
    public static final String TO = "to";
    public static final String VALUE = "value";
    public static final String ETH_VALUE = "ethValue";
    public static final String TX_HASH = "txHash";
    public static final String SPEC_ID = "specId";

    //For Events
    public static final String EVENT_NAME = "eventName";
    public static final String MONITOR_ID = "monitorId";
    public static final String INDEXED_PARAM_PREFIX = "indexedParam";
    public static final String PARAM_PREFIX = "param";
}
