package praj.app.swagtag

import javafx.application.Application
import javafx.stage.Stage

/**
 * Created by Priyadarshi Raj on 27/12/17
 */
class MainApp : Application() {
    override fun start(primaryStage: Stage) {
        UIHandler.initialize(primaryStage)
        UIHandler.show()
    }
}