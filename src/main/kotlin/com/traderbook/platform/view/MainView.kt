package com.traderbook.platform.view

import com.traderbook.platform.view.panels.Graphics
import com.traderbook.platform.view.panels.InstrumentAccount
import com.traderbook.platform.view.panels.MainMenu
import com.traderbook.platform.view.panels.StatusBar
import tornadofx.*

class MainView : View("Main view") {
    override val root = borderpane {
        top {
            add(MainMenu::class)
        }
        center {
            add(Graphics::class)
        }
        left {
            add(InstrumentAccount::class)
        }
        bottom {
            add(StatusBar::class)
        }
    }
}
