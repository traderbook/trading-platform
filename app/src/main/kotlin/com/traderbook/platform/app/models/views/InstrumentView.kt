package com.traderbook.platform.app.models.views

import com.traderbook.api.enums.Instruments
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

class InstrumentView(name: Instruments, ask: Double, bid: Double) {
    val nameProperty = SimpleObjectProperty<Instruments>(this, "name", name)
    var name by nameProperty

    val askProperty = SimpleDoubleProperty(this, "ask", ask)
    var ask by askProperty

    val bidProperty = SimpleDoubleProperty(this, "bid", bid)
    var bid by bidProperty
}