package com.traderbook.api.models

import com.traderbook.api.enums.Instruments

class Instrument(var name: Instruments, var ask: Double, var bid: Double, var oldAsk: Double, var oldBid: Double) {
    override fun toString(): String {
        return "Instrument(name=$name, oldAsk=$oldAsk, ask=$ask, oldBid=$oldBid, bid=$bid)"
    }
}