package com.traderbook.api.interfaces

import com.traderbook.api.AccountType

interface IConnector {
    fun getName(): String

    fun connection(accountType: AccountType, username: String, password: String)

    fun start()

    fun stop()
}