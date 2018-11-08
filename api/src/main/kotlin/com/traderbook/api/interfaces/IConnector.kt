package com.traderbook.api.interfaces

import com.traderbook.api.AccountType

interface IConnector {
    fun connection(accountType: AccountType, username: String, password: String)

    fun start()

    fun stop()
}