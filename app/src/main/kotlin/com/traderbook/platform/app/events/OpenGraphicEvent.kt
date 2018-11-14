package com.traderbook.platform.app.events

import com.traderbook.api.enums.Instruments
import tornadofx.*

class OpenGraphicEvent(val instruments: Instruments?) : FXEvent()