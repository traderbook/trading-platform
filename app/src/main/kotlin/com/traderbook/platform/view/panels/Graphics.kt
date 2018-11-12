package com.traderbook.platform.view.panels

import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.Tab
import javafx.scene.input.TransferMode
import tornadofx.*
import javafx.scene.input.ClipboardContent
import javafx.scene.input.Dragboard



class Graphics : View("My View") {
    override val root = vbox {
        tabpane {

            val tabs = this.tabs

            setOnDragDetected {
                this.startFullDrag()

                println(this.selectionModel.selectedIndex)


                val dragboard = this.startDragAndDrop(*TransferMode.ANY)
                val clipboardContent = ClipboardContent()
                clipboardContent.putString(this.selectionModel.selectedIndex.toString())
                dragboard.setContent(clipboardContent)

                it.consume()
            }

            setOnDragOver {
                if(it.getDragboard().hasString())
                {
                    it.acceptTransferModes(*TransferMode.COPY_OR_MOVE)
                }

                it.consume()
            }

            setOnDragDropped {
                it.dragboard.also(::println)
                var success = false

                val dragboard = it.getDragboard()

                if(dragboard.hasString())
                {
                    System.out.println("from " + dragboard.getString() + " to ")

                    success = true
                }

                it.setDropCompleted(success)

                it.consume()
            }

            setOnDragDone {
                if(it.getTransferMode() == TransferMode.MOVE)
                {
                    // tabPane.setText("");
                }
                it.consume()
            }

            tab("EURUSD") {
//                graphic = hbox {
//                    label { text = "EURUSD" }
//                    label {
//                        text = " D "
//
//                        onMouseClicked = EventHandler {
//                            println("Doit permettre de détacher le graphique")
//                        }
//                    }
//                }

                vbox {
                    label("coucou EURUSD")
                }

                useMaxWidth = true
                useMaxHeight = true

//                onDragDetected = EventHandler {
//                    println("Drag")
//                    val t = it.source as Node
//
//                    println(t.indexInParent)
//
////                val start = it.source as Node
////
////                start.startFullDrag()
//                }

//                setOnDragDetected {
//                    println("cool detached")
//                }
            }

            tab("GBPUSD") {
//                graphic = hbox {
//                    label { text = "GBPUSD" }
//                    label {
//                        text = " D "
//
//                        onMouseClicked = EventHandler {
//                            println("Doit permettre de détacher le graphique")
//                        }
//                    }
//                }

                vbox {
                    label("coucou GBPUSD")
                }

                useMaxWidth = true
                useMaxHeight = true
            }

        }
    }
}
