package com.traderbook.platform.view.panels

import com.traderbook.api.enums.Instruments
import tornadofx.*

class GraphicModal : Fragment("My View") {
    val instruments: Instruments by param()


    init {
        println(instruments.toString())
    }

    override val root = borderpane {

    }
}
