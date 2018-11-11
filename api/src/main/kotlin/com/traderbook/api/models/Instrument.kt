package com.traderbook.api.models

import com.traderbook.api.enums.Instruments

class Instrument(var id: String, var name: Instruments, var ask: Double, var bid: Double, var oldAsk: Double, var oldBid: Double) {
    override fun toString(): String {
        return "Instrument(name=$name, ask=$ask, bid=$bid, oldAsk=$oldAsk, oldBid=$oldBid)"
    }
}