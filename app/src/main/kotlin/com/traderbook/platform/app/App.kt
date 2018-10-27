package com.traderbook.platform.app


import com.traderbook.platform.app.models.tables.Accounts
import com.traderbook.platform.view.MainView
import javafx.stage.Stage
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.App
import java.io.File
import java.sql.Connection

open class App : App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        if(!File("${System.getProperty("user.home")}/.traderbook/data").exists()) {
            File("${System.getProperty("user.home")}/.traderbook/data").mkdirs()
        }

        Database.connect("jdbc:sqlite:${System.getProperty("user.home")}/.traderbook/data/data.db", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

        transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(Accounts)
        }

        super.start(stage)
    }
}