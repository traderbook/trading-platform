package com.traderbook.platform.app.controllers

import com.traderbook.api.AccountType
import com.traderbook.platform.app.events.AccountListRefreshEvent
import com.traderbook.platform.app.events.OpenConnectionFormEvent
import com.traderbook.platform.app.models.emuns.Broker
import com.traderbook.platform.app.models.emuns.StackPane
import com.traderbook.platform.app.models.views.AccountView
import com.traderbook.platform.app.services.AccountService
import tornadofx.*

class AccountController : Controller() {
    private val stackPaneController: StackPaneController by inject()
    private val accountService = AccountService()

    val accountList = arrayListOf<AccountView>().observable()
    var accountIndex: Int? = null

    /**
     * Permet de charger la liste des comptes de trading enregistrés dans la base de données
     */
    init {
        runLater {
            accountService.read().forEach {
                accountList.add(AccountView(it.id.value, Broker.valueOf(it.broker), AccountType.valueOf(it.accountType), it.username, it.password, it.accountId))
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
            val account = accountService.update(
                    id,
                    "$broker",
                    "$accountType",
                    username,
                    password
            )

            if (account != null) {
                accountList[accountIndex!!].broker = broker
                accountList[accountIndex!!].accountType = accountType
                accountList[accountIndex!!].username = username
                accountList[accountIndex!!].password = password
            }
        } else {
            val accountId = "ZERTYUI"

            val account = accountService.create(
                    broker.toString(),
                    accountType.toString(),
                    username,
                    password,
                    accountId
            )

            accountList.add(AccountView(account!!.id.value, broker, accountType, username, password, accountId))
        }

        fire(AccountListRefreshEvent())
    }

    /**
     * Permet d'afficher le formulaire de connexion
     */
    fun addAccount() {
        stackPaneController.selectPane(StackPane.CONNECTION_ACCOUNT)
    }

    /**
     * Permet de supprimer un compte de trading et de le déconnecter avant
     */
    fun deleteAccount(account: AccountView) {
        accountService.delete(account.id)

        accountList.remove(account)
    }

    /**
     * Permet de déconnecter un compte de trading
     */
    fun logout(account: AccountView) {
        println("Tu n'a pas encore déconnecter un compte")
        println(account.toString())
    }
}