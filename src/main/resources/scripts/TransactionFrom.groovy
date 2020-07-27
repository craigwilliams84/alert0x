package scripts

import eth.craig.alert0x.model.alert.Email
import eth.craig.alert0x.spec.TransactionMined

def when() {
    TransactionMined
            .builder()
            .from("<SET ME>")
            .build()
}

def sendAlerts() {
    [Email
            .builder()
            .from("transaction@alert0x.com")
            .to("craigwilliams84@gmail.com")
            .subject("Transaction Sent")
            .bodyTemplate("tx_from.template")
            .build()]
}
