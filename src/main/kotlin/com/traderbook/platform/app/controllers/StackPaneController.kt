package com.traderbook.platform.app.controllers

import com.traderbook.platform.app.models.emuns.StackPane
import javafx.scene.Node
import tornadofx.*

class StackPaneController: Controller() {
    private val stackPanes = mutableMapOf<StackPane, Node>().observable()

    fun addStackPane(name: StackPane, pane: Node) {
        stackPanes.put(name, pane)
    }

    fun selectPane(name: StackPane) {
        stackPanes.values.forEach {
            it.visibleProperty().value = false
        }

        stackPanes[name]!!.toFront()
        stackPanes[name]!!.visibleProperty().value = true
    }
}