package praj.app.swagtag

import javafx.application.Application

/**
 * Created by Priyadarshi Raj on 27/12/17
 */
object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        Application.launch(MainApp::class.java, *args)
    }
}