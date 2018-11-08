package com.traderbook.api.interfaces

import com.traderbook.api.enums.Messages

interface IConnectorObserver {
    fun update(message: Messages, data: Any?)
}