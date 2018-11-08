package com.traderbook.api.models

class BrokerAccount(
        val username: String,
        val accountId: String,
        val currency: String
) {
    override fun toString(): String {
        return "BrokerAccount(username='$username', accountId='$accountId', currency='$currency')"
    }
}