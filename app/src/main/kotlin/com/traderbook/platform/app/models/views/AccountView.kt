package com.traderbook.platform.app.models.views

import com.traderbook.api.AccountType
import com.traderbook.platform.app.models.emuns.Broker
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty

import tornadofx.*

class AccountView(id: Int, broker: Broker, accountType: AccountType, username: String, password: String, accountId: String) {
    val idProperty = SimpleIntegerProperty(this, "id", id)
    var id by idProperty

    val brokerProperty = SimpleObjectProperty<Broker>(this, "broker", broker)
    var broker by brokerProperty

    val accountTypeProperty = SimpleObjectProperty<AccountType>(this, "accountType", accountType)
    var accountType by accountTypeProperty

    val usernameProperty = SimpleStringProperty(this, "username", username)
    var username by usernameProperty

    val passwordProperty = SimpleStringProperty(this, "password", password)
    var password by passwordProperty

    val accountIdProperty = SimpleStringProperty(this, "accountId", accountId)
    var accountId by accountIdProperty

    override fun toString(): String {
        return "AccountView(idProperty=${idProperty.value}, brokerProperty=${brokerProperty.value}, accountTypeProperty=${accountTypeProperty.value}, usernameProperty=${usernameProperty.value}, passwordProperty=${passwordProperty.value}, accountIdProperty=${accountIdProperty.value})"
    }
}
