package com.traderbook.platform.view.panels

import com.traderbook.platform.app.controllers.AccountController
import com.traderbook.platform.app.events.AccountListRefreshEvent
import javafx.scene.control.SelectionMode
import tornadofx.*

class Accounts : View("Accounts read") {
    private val accountController: AccountController by inject()

    override val root = vbox {
        listview(accountController.accountList) {
            cellFormat {
                text = it.usernameProperty.value
            }

            onUserSelect {
                accountController.selectItem(selectionModel.selectedIndex)
            }

            contextmenu {
                item("ADD").action {
                    accountController.addAccount()
                }

                item("DELETE").action {
                    selectedItem?.let {
                        accountController.deleteAccount(it)
                    }
                }

                item("LOGOUT").action {
                    selectedItem?.let {
                        accountController.logout(it)
                    }
                }
            }

            placeholder = label("EMPTY LIST CLICK ADD ACCOUNT")

            selectionModel.selectionMode = SelectionMode.SINGLE

            subscribe<AccountListRefreshEvent> {
                refresh()
            }
        }

        button("ADD ACCOUNT") {
            action {
                accountController.addAccount()
            }
        }
    }
}
