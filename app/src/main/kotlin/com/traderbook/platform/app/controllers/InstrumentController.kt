package com.traderbook.platform.app.controllers

import com.traderbook.platform.app.events.InstrumentUpdatedEvent
import com.traderbook.platform.app.models.views.InstrumentView
import tornadofx.*

class InstrumentController: Controller() {
    val instrumentList = arrayListOf<InstrumentView>().observable()

    init {
        subscribe<InstrumentUpdatedEvent> {
            val instruments = arrayListOf<InstrumentView>()

            it.instrumentCollection.instruments.forEach {
                instruments.add(InstrumentView(
                            it.value.id,
                            it.value.name,
                            it.value.ask,
                            it.value.bid,
                            it.value.oldAsk,
                            it.value.oldBid
                    ))
            }

            instrumentList.clear()
            instrumentList.addAll(instruments)
        }
    }
}