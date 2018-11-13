package com.traderbook.platform.view.panels

import com.traderbook.platform.app.controllers.InstrumentController
import com.traderbook.platform.app.events.OpenGraphicEvent
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
                            instrumentController.searchInstrument("")
                        }
                    }
                }
            }
        }

        tableview(instrumentController.instrumentFiltered) {
            var instrumentSelected = selectedItem

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

            onUserSelect(1) {
                instrumentSelected = selectedItem
            }

            contextmenu {
                item("OPEN GRAPH") {
                    action {
                        fire(OpenGraphicEvent(instrumentSelected!!.name))
                    }
                }

                item("PLACE ORDER") {
                    action {
                        println("Il faut ouvrir une modal de prise de position")
                    }
                }
            }

            items.onChange {
                if(items.count() == 0) {
                    placeholder = label("INSTRUMENTS NOT FOUND")
                }
            }

            placeholder = label("WAITING DATA")
        }
    }
}
