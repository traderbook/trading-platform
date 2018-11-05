package com.traderbook.platform.app.services

import com.traderbook.platform.app.helpers.AppEnvironment
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.Manifest

class ConnectorService {
    private val connectorsPath = "${System.getProperty("user.home")}/${AppEnvironment.getProperty("applicationDir")}/connectors"
    private val connectors = mutableMapOf<String, Class<*>>()

    fun load() {
        var urlLoader: URLClassLoader? = null

        val listFiles = File(connectorsPath).listFiles()

        if(listFiles == null) {
            println("Empty list files")
        } else {
            listFiles.forEach {
                urlLoader = URLClassLoader(arrayOf(
                        URL("jar:file://${it.path}!/")
                ))
            }

            val resources = urlLoader!!.loadClass("com.traderbook.connector.Connector").classLoader.getResources("META-INF/MANIFEST.MF")

            for(resource in resources) {
                val manifest = Manifest(resource.openStream())

                if(manifest.mainAttributes.getValue("Title") != null) {

                    connectors.put(
                            manifest.mainAttributes.getValue("Title"),
                            urlLoader!!.loadClass("com.traderbook.connector.Connector") as Class<*>
                    )
                }
            }
        }
    }

    fun getConnectors(): MutableCollection<String> {
        return connectors.keys
    }

    fun getConnector(name: String): Class<*>? {
        return connectors[name]
    }
}