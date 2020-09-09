package scripts

import eth.craig.alert0x.model.alert.Email
import eth.craig.alert0x.spec.TransactionMined

def when() {
    TransactionMined
            .builder()
            .from("0x4d91838268f6d6D4e590e8fd2a001Cd91c32e7A4")
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

def isDisabled() {
    true
}
