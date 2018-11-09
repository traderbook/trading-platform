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
                var content = it.usernameProperty.value

                if (it.isAuthenticatedProperty.value == true) {
                    content += " (CONNECTED)"
                }

                text = content
            }

            onUserSelect {
                accountController.selectItem(selectionModel.selectedIndex)
            }

            val ctxMenu = contextmenu {
                item("ADD") {
                    action {
                        accountController.addAccount()
                    }
                }

                item("DELETE") {
                    action {
                        selectedItem?.let {
                            accountController.deleteAccount()
                        }
                    }
                }


                item("CONNECT") {
                    action {
                        selectedItem?.let {
                            accountController.connection(
                                    selectedItem!!.id,
                                    selectedItem!!.broker,
                                    selectedItem!!.accountType,
                                    selectedItem!!.username,
                                    selectedItem!!.password
                            )
                        }
                    }
                }

                item("LOGOUT") {
                    action {
                        selectedItem?.let {
                            accountController.logout()
                        }
                    }
                }
            }

            onUserSelect(1) {
                ctxMenu.items.get(2).disableProperty().bind(it.isAuthenticatedProperty)
                ctxMenu.items.get(3).disableProperty().bind(!it.isAuthenticatedProperty)
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
