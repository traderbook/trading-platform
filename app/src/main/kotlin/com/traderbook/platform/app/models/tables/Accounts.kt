package com.traderbook.platform.app.models.tables

import org.jetbrains.exposed.dao.IntIdTable

object Accounts : IntIdTable() {
    val broker = varchar("broker", 50)
    val accountType = varchar("account_type", 4)
    val username = varchar("username", 50)
    val password = varchar("password", 50)
    val accountId = varchar("account_id", 50)
}