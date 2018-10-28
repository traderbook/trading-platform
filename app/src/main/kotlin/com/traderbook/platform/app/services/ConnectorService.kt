package com.traderbook.platform.app.services

import com.traderbook.api.interfaces.IConnector
import com.traderbook.platform.app.helpers.AppEnvironment
import java.io.File
import java.net.URL
import java.net.URLClassLoader

class ConnectorService {
    private val connectorsPath = "${System.getProperty("user.home")}/${AppEnvironment.getProperty("applicationDir")}/connectors"
    private val connectors = mutableMapOf<String, IConnector>()

    fun load() {
        var urlLoader: URLClassLoader? = null

        File(connectorsPath).listFiles().forEach {
            urlLoader = URLClassLoader(arrayOf(
                    URL("jar:file://${it.path}!/")
            ))
        }

        val connector = urlLoader!!.loadClass("com.traderbook.connector.Connector").newInstance() as IConnector

        connectors.put(
                connector.getName(),
                connector
        )
    }

    fun getConnectors(): MutableMap<String, IConnector> {
        return connectors
    }

    fun getConnector(name: String): IConnector? {
        return connectors[name]
    }
}