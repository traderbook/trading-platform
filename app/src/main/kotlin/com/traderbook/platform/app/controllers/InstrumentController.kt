package com.traderbook.platform.app.controllers

import com.traderbook.platform.app.events.InstrumentUpdatedEvent
import com.traderbook.platform.app.models.views.InstrumentView
import tornadofx.*

class InstrumentController: Controller() {
    private val instrumentList = arrayListOf<InstrumentView>().observable()
    val instrumentFiltered = arrayListOf<InstrumentView>().observable()

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

            instrumentFiltered.clear()
            instrumentFiltered.addAll(instrumentList)
        }
    }

    fun searchInstrument(text: String) {
        val list = instrumentList.filter { it.nameProperty.value.toString().contains(text) }

        if(list.count() > 0) {
            instrumentFiltered.clear()
            instrumentFiltered.addAll(list)
        } else {
            instrumentFiltered.clear()
            instrumentFiltered.addAll(instrumentList)
        }
    }
}