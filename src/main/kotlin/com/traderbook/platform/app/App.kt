package com.traderbook.platform.app


import com.traderbook.platform.view.MainView
import tornadofx.*
import tornadofx.App

class App: App(MainView::class, Styles::class) {
    init {
        reloadStylesheetsOnFocus()
    }
}