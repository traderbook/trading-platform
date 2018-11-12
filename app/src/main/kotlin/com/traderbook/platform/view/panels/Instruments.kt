package com.traderbook.platform.view.panels

import com.traderbook.platform.app.controllers.InstrumentController
import com.traderbook.platform.app.models.views.InstrumentView
import javafx.beans.property.ReadOnlyListProperty
import javafx.scene.control.SelectionMode
import tornadofx.*

class Instruments : View("My View") {
    private val instrumentController: InstrumentController by inject()

    override val root = vbox {
        tableview(instrumentController.instrumentList) {
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

            placeholder = label("EMPTY INSTRUMENT LIST")
        }
    }
}
