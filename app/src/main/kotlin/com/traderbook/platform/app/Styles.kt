package com.traderbook.platform.app

import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object { }

    init {
        root {
            fontSize = 15.pt
            prefWidth = 1680.0.pt
            prefHeight = 1050.0.pt
        }
    }
}