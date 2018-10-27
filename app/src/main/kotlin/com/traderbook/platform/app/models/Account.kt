package com.traderbook.platform.app.models

import com.traderbook.platform.app.models.tables.Accounts
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class Account(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Account>(Accounts)

    var broker by Accounts.broker
    var accountType by Accounts.accountType
    var username by Accounts.username
    var password by Accounts.password
    var accountId by Accounts.accountId
}