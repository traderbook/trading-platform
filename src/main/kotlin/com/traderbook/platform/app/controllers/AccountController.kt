package com.traderbook.platform.app.controllers

import com.traderbook.platform.app.models.views.Account
import tornadofx.*

class AccountController: Controller() {
    val accountList = arrayListOf<Account>().observable()
}