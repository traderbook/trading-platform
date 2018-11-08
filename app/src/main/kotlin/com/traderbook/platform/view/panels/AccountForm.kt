package com.traderbook.platform.view.panels

import com.traderbook.api.AccountType
import com.traderbook.platform.app.controllers.AccountController
import com.traderbook.platform.app.controllers.StackPaneController
import com.traderbook.platform.app.events.AccountListRefreshEvent
import com.traderbook.platform.app.events.AlertEvent
import com.traderbook.platform.app.events.OpenConnectionFormEvent
import javafx.collections.FXCollections
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import tornadofx.*

class AccountForm : View("My View") {
    private val stackPaneController: StackPaneController by inject()
    private val accountController: AccountController by inject()

    private val brokers = FXCollections.observableArrayList(accountController.connectorService.getConnectors())
    private var accountType = FXCollections.observableArrayList(AccountType.values().toList())

    private var brokerField: ComboBox<String> by singleAssign()
    private var accountTypeField: ComboBox<AccountType> by singleAssign()
    private var usernameField: TextField by singleAssign()
    private var passwordField: TextField by singleAssign()

    override val root = vbox {
        label {
            hide()

            subscribe<AlertEvent> {
                text = it.message
                show()
            }

            subscribe<AccountListRefreshEvent> {
                text = ""
                hide()
            }
        }

        form {
            fieldset {
                field {
                    combobox(null, brokers) {
                        cellFormat {
                            text = it
                        }

                        brokerField = this

                        selectionModel.selectFirst()
                    }
                }

                field {
                    combobox(null, accountType) {
                        cellFormat {
                            text = it.name
                        }

                        accountTypeField = this

                        selectionModel.selectFirst()
                    }
                }

                field {
                    textfield {
                        promptText = "USERNAME"
                        usernameField = this
                    }
                }

                field {
                    textfield {
                        promptText = "PASSWORD"
                        passwordField = this
                    }
                }
            }

            hbox {
                button("CANCEL") {
                    action {
                        stackPaneController.cancelConnection()
                    }
                }

                button("CONNECTION") {
                    action {
                        if (accountController.accountIndex != null) {
                            accountController.connection(
                                    accountController.getAccountId(),
                                    brokerField.value,
                                    accountTypeField.value,
                                    usernameField.text,
                                    passwordField.text
                            )
                        } else {
                            accountController.connection(
                                    null,
                                    brokerField.value,
                                    accountTypeField.value,
                                    usernameField.text,
                                    passwordField.text
                            )
                        }

                        subscribe<AccountListRefreshEvent> {
                            usernameField.text = null
                            passwordField.text = null
                        }
                    }
                }
            }

        }
    }

    init {
        subscribe<OpenConnectionFormEvent> {
            if (accountController.accountIndex != null) {
                accountController.accountList.apply {
                    this[accountController.accountIndex!!].apply {
                        brokerField.value = brokerProperty.value
                        accountTypeField.value = accountTypeProperty.value
                        usernameField.text = usernameProperty.value
                        passwordField.text = passwordProperty.value
                    }
                }
            }
        }
    }
}
