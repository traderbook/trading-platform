package com.traderbook.platform.view.panels

import com.traderbook.platform.app.controllers.StackPaneController
import com.traderbook.platform.app.models.emuns.StackPane
import tornadofx.*

class AccountForm : View("My View") {
    private val stackPaneController: StackPaneController by inject()

    override val root = form {
        fieldset {
            field {
                textfield {  }
            }

            field {
                textfield {  }
            }
        }

        button("CONNECTION") {
            action {
                stackPaneController.selectPane(StackPane.DASHBOARD)
            }
        }
    }
}
