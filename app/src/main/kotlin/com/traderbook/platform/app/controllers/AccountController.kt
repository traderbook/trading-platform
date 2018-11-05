package com.traderbook.platform.app.controllers

import com.traderbook.api.AccountType
import com.traderbook.platform.app.events.AccountListRefreshEvent
import com.traderbook.platform.app.events.OpenConnectionFormEvent
import com.traderbook.platform.app.models.emuns.StackPane
import com.traderbook.platform.app.models.views.AccountView
import com.traderbook.platform.app.services.AccountService
import com.traderbook.platform.app.services.ConnectorService
import tornadofx.*

class AccountController : Controller() {
    private val stackPaneController: StackPaneController by inject()
    private val accountService = AccountService()

    val accountList = arrayListOf<AccountView>().observable()
    val connectorService = ConnectorService()

    var accountIndex: Int? = null

    /**
     * Permet de :
     * - charger les connectors installés sur la plate-forme de trading
     * - charger la liste des comptes de trading enregistrés dans la base de données
     */
    init {
        connectorService.load()

        runLater {
            accountService.read().forEach {
                accountList.add(AccountView(
                        it.id.value,
                        it.broker,
                        AccountType.valueOf(it.accountType),
                        it.username,
                        it.password,
                        it.accountId,
                        it.isAuthenticated
                ))
            }
        }
    }

    /**
     * Initialise toutes les propriété isAuthenticated des models AccountView à false
     */
    private fun resetAccountView() {
        accountList.forEach {
            it.isAuthenticatedProperty.value = false
        }
    }

    /**
     * Permet de rafraichir la liste des comptes de trading après une modification via le formulaire de connexion
     */
    private fun refreshAccountList() {
        fire(AccountListRefreshEvent())
        stackPaneController.selectPane(StackPane.DASHBOARD)
    }

    /**
     * Permet de retourner l'id du compte selectionné
     */
    fun getAccountId(): Int {
        return accountList[accountIndex!!].id
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
    fun connection(id: Int?, broker: String, accountType: AccountType, username: String, password: String) {
        if (id != null) {
            val account = accountService.update(
                    id,
                    broker,
                    "$accountType",
                    username,
                    password
            )

            if (account != null) {
                resetAccountView()

                accountList[accountIndex!!].broker = account.broker
                accountList[accountIndex!!].accountType = AccountType.valueOf(account.accountType)
                accountList[accountIndex!!].username = account.username
                accountList[accountIndex!!].password = account.password
                accountList[accountIndex!!].isAuthenticated = account.isAuthenticated

                refreshAccountList()
            }
        } else {
            val account = accountService.create(
                    broker,
                    "$accountType",
                    username,
                    password,
                    ""
            )

            if (account != null) {
                resetAccountView()

                accountList.add(AccountView(
                        account.id.value,
                        account.broker,
                        AccountType.valueOf(account.accountType),
                        account.username,
                        account.password,
                        account.accountId,
                        account.isAuthenticated
                ))

                refreshAccountList()
            }
        }
    }

    /**
     * Permet d'afficher le formulaire de connexion
     */
    fun addAccount() {
        stackPaneController.selectPane(StackPane.CONNECTION_ACCOUNT)
    }

    /**
     * Permet de supprimer un compte de trading
     */
    fun deleteAccount(account: AccountView) {
        accountService.delete(account.id)

        accountList.remove(account)

        fire(OpenConnectionFormEvent())
    }

    /**
     * Permet de déconnecter un compte de trading
     */
    fun logout(account: AccountView) {
        println("Tu n'a pas encore déconnecter un compte")
        println(account.toString())
    }
}