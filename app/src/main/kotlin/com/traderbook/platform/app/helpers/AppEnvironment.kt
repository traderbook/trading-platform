package com.traderbook.platform.app.helpers

import java.io.FileInputStream
import java.net.URL
import java.util.*

object AppEnvironment {
    private val appProps = Properties()
    private var appProperties: URL = Thread.currentThread().contextClassLoader.getResource("application.properties")

    /**
     * Initialise le récupération des propriétés dans le fichier de configuration
     */
    init {
        appProps.load(FileInputStream(appProperties.path))
    }

    /**
     * Permet la récupération d'une propriété
     */
    fun getProperty(key: String): String {
        return appProps.getProperty(key)
    }
}