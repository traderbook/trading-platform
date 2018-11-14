package com.traderbook.platform.view.panels

import com.traderbook.platform.app.events.OpenGraphicEvent
import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import tornadofx.*


class Graphics : View("My View") {
    override val root = vbox {
        tabpane {
            tabClosingPolicy = TabPane.TabClosingPolicy.ALL_TABS

            subscribe<OpenGraphicEvent> {
                val instruments = it.instruments

                val tab = Tab("")

                val hbox = HBox()

                hbox.add(Label(instruments.toString()))

                val detachBtn = Label(" D ")

                detachBtn.onMouseClicked = EventHandler {
                    find<GraphicModal>(mapOf(GraphicModal::instruments to instruments)) {
                        openModal()
                    }

                    tab.close()
                }

                hbox.add(detachBtn)

                val vbox = VBox()

                vbox.add(Label("coucou ${it.instruments.toString()}"))

                tab.region {
                    useMaxWidth = true
                    useMaxHeight = true
                }

                tab.graphic = hbox
                tab.content = vbox

                this@tabpane.tabs.add(tab)
            }
        }
    }
}
