package com.traderbook.api.models

import com.traderbook.api.enums.Instruments

class InstrumentCollection(val instruments: MutableMap<Instruments, Instrument>)