package com.traderbook.platform.app.services

import com.traderbook.api.AccountType
import com.traderbook.api.enums.Messages
import com.traderbook.api.interfaces.IConnector
import com.traderbook.api.interfaces.IConnectorObserver
import com.traderbook.api.models.BrokerAccount
import com.traderbook.platform.app.helpers.AppEnvironment
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.Manifest

class ConnectorService(private val controller: IConnectorObserver): IConnector, IConnectorObserver {
    private val connectorsPath = "${System.getProperty("user.home")}/${AppEnvironment.getProperty("applicationDir")}/connectors"
    private val connectors = mutableMapOf<String, Class<*>>()
    private val accountService = AccountService()
    private var connector: IConnector? = null

    private var name: String? = null
    private var accountType: AccountType? = null
    private var username: String? = null
    private var password: String? = null

    fun load() {
        val listFiles = File(connectorsPath).listFiles()

        if(listFiles == null) {
            println("Empty list files")
        } else {
            listFiles.forEach {
                val urlLoader = URLClassLoader(arrayOf(
                        URL("jar:file://${it.path}!/")
                ))

                val resources = urlLoader.loadClass("com.traderbook.connector.Connector").classLoader.getResources("META-INF/MANIFEST.MF")

                for(resource in resources) {
                    val manifest = Manifest(resource.openStream())

                    if(manifest.mainAttributes.getValue("Title") != null) {
                        connectors.put(
                                manifest.mainAttributes.getValue("Title"),
                                urlLoader.loadClass("com.traderbook.connector.Connector") as Class<*>
                        )
                    }
                }
            }
        }
    }

    fun getConnectors(): MutableCollection<String> {
        return connectors.keys
    }

    fun initializeConnector(name: String) {
        this.name = name

        connector = connectors[name]!!
                .getConstructor(IConnectorObserver::class.java)
                .newInstance(this) as IConnector
    }

    fun getConnector(name: String): Class<*>? {
        return connectors[name]
    }

    override fun connection(accountType: AccountType, username: String, password: String) {
        this.accountType = accountType
        this.username = username
        this.password = password

        connector!!.connection(
                accountType,
                username,
                password
        )
    }

    override fun start() {
        connector!!.start()
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(message: Messages, data: Any?) {
        when(message) {
            Messages.SUCCESS_LOGIN -> {
                val brokerAccount = data as BrokerAccount

                val account = accountService.getAccountByAccountId(brokerAccount.accountId)

                if(account == null) {
                    controller.update(message, accountService.create(
                            this.name!!,
                            this.accountType!!.toString(),
                            this.username!!,
                            this.password!!,
                            brokerAccount.accountId
                    ))
                }
            }
            Messages.BAD_CREDENTIALS -> controller.update(message, null)
            else -> println("Error occured")
        }
    }
}