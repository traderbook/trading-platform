package com.traderbook.platform.app.controllers

import com.traderbook.platform.app.events.AccountListRefreshEvent
import com.traderbook.platform.app.events.OpenConnectionFormEvent
import com.traderbook.platform.app.models.Account
import com.traderbook.platform.app.models.emuns.AccountType
import com.traderbook.platform.app.models.emuns.Broker
import com.traderbook.platform.app.models.emuns.StackPane
import com.traderbook.platform.app.models.views.AccountView
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.*

class AccountController : Controller() {
    private val stackPaneController: StackPaneController by inject()

    val accountList = arrayListOf<AccountView>().observable()
    var accountIndex: Int? = null

    init {
        runLater {
            transaction {
                Account.all().forEach {
                    accountList.add(AccountView(it.id.value, Broker.valueOf(it.broker), AccountType.valueOf(it.accountType), it.username, it.password, it.accountId))
                }
            }
        }
    }

    /**
     * Permet de sélectionner un compte de trading et d'afficher le formulaire de connexion
     */
    fun selectItem(index: Int) {
        accountIndex = index

        stackPaneController.selectPane(StackPane.CONNECTION_ACCOUNT)

        fire(OpenConnectionFormEvent())
    }

    /**
     * Permet de lancer le processus de connexion d'un compte de trading
     */
    fun connection(id: Int?, broker: Broker, accountType: AccountType, username: String, password: String) {
        if (id != null) {
            transaction {
                val account = Account.findById(id)

                if (account != null) {
                    account.broker = "$broker"
                    account.accountType = "$accountType"
                    account.username = username
                    account.password = password

                    accountList[accountIndex!!].broker = broker
                    accountList[accountIndex!!].accountType = accountType
                    accountList[accountIndex!!].username = username
                    accountList[accountIndex!!].password = password
                }
            }
        } else {
            var accountId = "ZERTYUI"

            transaction {
                var account = Account.new {
                    this.broker = broker.toString()
                    this.accountType = accountType.toString()
                    this.username = username
                    this.password = password
                    this.accountId = accountId
                }

                accountList.add(AccountView(account.id.value, broker, accountType, username, password, accountId))
            }
        }

        fire(AccountListRefreshEvent())
    }

    fun addAccount() {
        stackPaneController.selectPane(StackPane.CONNECTION_ACCOUNT)
    }
}