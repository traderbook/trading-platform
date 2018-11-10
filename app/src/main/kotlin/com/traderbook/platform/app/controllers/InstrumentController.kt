package com.traderbook.platform.app.controllers

import com.traderbook.api.enums.Instruments
import com.traderbook.platform.app.models.views.InstrumentView
import tornadofx.*

class InstrumentController: Controller() {
    val instrumentList = arrayListOf<InstrumentView>().observable()

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
    }
}