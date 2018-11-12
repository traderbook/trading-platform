package com.traderbook.platform.app.events

import com.traderbook.api.models.InstrumentCollection
import tornadofx.*

class InstrumentUpdatedEvent(val instrumentCollection: InstrumentCollection) : FXEvent()