package com.traderbook.platform.app.controllers

import com.traderbook.platform.app.models.emuns.StackPane
import javafx.scene.Node
import tornadofx.*

class StackPaneController : Controller() {
    private val stackPanes = mutableMapOf<StackPane, Node>().observable()

    /**
     * Permet de rendre invisible toute les stackpane
     */
    private fun hideAllPanes() {
        stackPanes.values.forEach {
            it.visibleProperty().value = false
        }
    }

    /**
     * Permet d'afficher une stackpane
     */
    private fun showSpecificPane(name: StackPane) {
        stackPanes[name]!!.toFront()
        stackPanes[name]!!.visibleProperty().value = true
    }

    /**
     * Permet d'ajouter une stackpane dans une liste
     */
    fun addStackPane(name: StackPane, pane: Node) {
        stackPanes.put(name, pane)
    }

    /**
     * Permet de mettre en front et rendre visible une stackpane
     */
    fun selectPane(name: StackPane) {
        hideAllPanes()
        showSpecificPane(name)
    }

    /**
     * Permet d'annuler une connexion
     */
    fun cancelConnection() {
        hideAllPanes()
        showSpecificPane(StackPane.DASHBOARD)
    }
}