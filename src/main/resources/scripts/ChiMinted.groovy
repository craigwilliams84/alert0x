package scripts

import eth.craig.alert0x.model.alert.Email
import eth.craig.alert0x.spec.EventEmitted

def when() {
    EventEmitted
            .builder()
            .withSpec("Transfer(indexed address from, indexed address to, uint256 tokens)")
            .fromContract("0x0000000000004946c0e9f43f4dee607b0ef1fa1c")
            .havingArgumentValue("0x0000000000000000000000000000000000000000", 0, true)
            .build()
}

def sendAlerts() {
    [Email
            .builder()
            .from("event@alert0x.com")
            .to("craigwilliams84@gmail.com")
            .subject("Chi Minted!")
            .bodyTemplate("chi_minted.template")
            .build()]
}

def isDisabled() {
    true
}
