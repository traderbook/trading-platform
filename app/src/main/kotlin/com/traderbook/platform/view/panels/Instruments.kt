package com.traderbook.platform.view.panels

import com.traderbook.platform.app.controllers.InstrumentController
import com.traderbook.platform.app.models.views.InstrumentView
import javafx.event.EventHandler
import javafx.scene.control.SelectionMode
import javafx.scene.control.TextField
import tornadofx.*

class Instruments : View("My View") {
    private val instrumentController: InstrumentController by inject()

    private var researchField: TextField by singleAssign()

    override val root = vbox {
        form {
            fieldset {
                field {
                    textfield {
                        promptText = "RESEARCH..."
                        researchField = this

                        onKeyReleased = EventHandler {
                            instrumentController.searchInstrument(this.text)
                        }
                    }

                    button {
                        text = "X"

                        action {
                            researchField.text = null
                        }
                    }
                }
            }
        }

        tableview(instrumentController.instrumentFiltered) {
            column("NAME", InstrumentView::name)
            column("ASK", InstrumentView::ask) {
                cellFormat {
                    text = "%.5f".format(it)
                }
            }
            column("BID", InstrumentView::bid) {
                cellFormat {
                    text = "%.5f".format(it)
                }
            }

            smartResize()
            selectionModel.selectionMode = SelectionMode.SINGLE

            contextmenu {
                item("OPEN GRAPH") {
                    action {
                        println("Il faut ouvrir un graphique")
                    }
                }

                item("PLACE ORDER") {
                    action {
                        println("Il faut ouvrir une modal de prise de position")
                    }
                }
            }

            placeholder = label("EMPTY INSTRUMENT LIST")
        }
    }
}
