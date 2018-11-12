package com.traderbook.platform.app.models.views

import com.traderbook.api.enums.Instruments
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class InstrumentView(id: String, name: Instruments, ask: Double, bid: Double, oldAsk: Double, oldBid: Double) {
    val idProperty = SimpleStringProperty(this, "id", id)
    var id by idProperty

    val nameProperty = SimpleObjectProperty<Instruments>(this, "name", name)
    var name by nameProperty

    val askProperty = SimpleDoubleProperty(this, "ask", ask)
    var ask by askProperty

    val bidProperty = SimpleDoubleProperty(this, "bid", bid)
    var bid by bidProperty

    val oldAskProperty = SimpleDoubleProperty(this, "oldAsk", oldAsk)
    var oldAsk by oldAskProperty

    val oldBidProperty = SimpleDoubleProperty(this, "oldBid", oldBid)
    var oldBid by oldBidProperty
}