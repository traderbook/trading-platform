package com.traderbook.platform.view

import com.traderbook.platform.app.controllers.StackPaneController
import com.traderbook.platform.app.models.emuns.StackPane
import com.traderbook.platform.view.panels.*
import javafx.scene.layout.BorderPane
import tornadofx.*

class MainView : View("Main view") {
    private val stackPaneController: StackPaneController by inject()

    override val root = stackpane {
        borderpane {
            center {
                add(AccountForm::class)
            }

            visibleProperty().value = false
        }

        borderpane {
            top {
                add(MainMenu::class)
            }
            center {
                add(Graphics::class)
            }
            left {
                add(Accounts::class)
            }
            bottom {
                add(StatusBar::class)
            }

            visibleProperty().value = false
        }
    }

    init {
        stackPaneController.addStackPane(StackPane.CONNECTION_ACCOUNT, root.getChildList()!![0])
        stackPaneController.addStackPane(StackPane.DASHBOARD, root.getChildList()!![1])

        stackPaneController.selectPane(StackPane.DASHBOARD)
    }
}
