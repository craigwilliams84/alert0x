package scripts

import eth.craig.alert0x.model.alert.Email
import eth.craig.alert0x.spec.EventEmitted

def when() {
    EventEmitted
            .builder()
            .withSpec("WalletCreated(indexed address wallet, indexed address owner, indexed address guardian")
            .fromContract("0x40C84310Ef15B0c0E5c69d25138e0E16e8000fE9")
            .build()
}

def sendAlerts() {
    [Email
            .builder()
            .from("event@alert0x.com")
            .to("craigwilliams84@gmail.com")
            .subject("Wallet Created")
            .bodyTemplate("wallet_created.template")
            .build()]
}
