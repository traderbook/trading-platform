package com.traderbook.platform.app.controllers

import com.traderbook.api.enums.Instruments
import com.traderbook.platform.app.models.views.InstrumentView
import tornadofx.*

class InstrumentController: Controller() {
    private val instrumentList = arrayListOf<InstrumentView>().observable()
    val instrumentFiltered = arrayListOf<InstrumentView>().observable()

    init {
        instrumentList.add(InstrumentView(
                Instruments.EURUSD,
                1.1234,
                1.1234
        ))

        instrumentList.add(InstrumentView(
                Instruments.GBPUSD,
                1.1234,
                1.1234
        ))

        instrumentFiltered.addAll(instrumentList)
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