package com.traderbook.platform.view.panels

import tornadofx.*

class MainMenu : View("My View") {
    override val root = menubar {
        menu("Files") {
            item("Login","Shortcut+L")
            item("Logout","Shortcut+O")
        }
    }
}
