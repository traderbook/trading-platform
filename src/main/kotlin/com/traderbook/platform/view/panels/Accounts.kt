package com.traderbook.platform.view.panels

import com.traderbook.platform.app.controllers.AccountController
import com.traderbook.platform.app.controllers.StackPaneController
import com.traderbook.platform.app.models.emuns.StackPane
import tornadofx.*

class Accounts : View("Accounts list") {
    private val stackPaneController: StackPaneController by inject()
    private val accountController: AccountController by inject()

    override val root = vbox {
        listview(accountController.accountList) { }

        button("ADD ACCOUNT") {
            action {
                stackPaneController.selectPane(StackPane.CONNECTION_ACCOUNT)
            }
        }
    }
}
