package com.traderbook.platform.app.services

import com.traderbook.platform.app.models.Account
import org.jetbrains.exposed.sql.transactions.transaction

class AccountService {
    /**
     * Permet de récupérer les comptes de trading
     */
    fun read(): List<Account> {
        var accounts: List<Account>? = null

        transaction {
            accounts = Account.all().toList()
        }

        return accounts!!
    }

    /**
     * Permet d'ajouter un compte de trading
     */
    fun create(broker: String, accountType: String, username: String, password: String, accountId: String): Account? {
        var account: Account? = null

        transaction {
            Account.all().forEach {
                it.isAuthenticated = false
            }

            account = Account.new {
                this.broker = broker
                this.accountType = accountType
                this.username = username
                this.password = password
                this.accountId = accountId
                this.isAuthenticated = true
            }
        }

        return account
    }

    /**
     * Permet la mise à jour d'un compte de trading
     */
    fun update(id: Int, broker: String, accountType: String, username: String, password: String): Account? {
        var account: Account? = null

        transaction {
            Account.all().forEach {
                it.isAuthenticated = false
            }

            account = Account.findById(id)

            if (account != null) {
                account!!.broker = broker
                account!!.accountType = accountType
                account!!.username = username
                account!!.password = password
                account!!.isAuthenticated = true
            }
        }

        return account
    }

    /**
     * Permet la suppression d'un compte de trading
     */
    fun delete(id: Int) {
        transaction {
            Account.findById(id)!!.delete()
        }
    }
}