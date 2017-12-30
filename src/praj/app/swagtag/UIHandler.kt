package praj.app.swagtag

import javafx.geometry.HPos
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import javafx.stage.FileChooser
import javafx.stage.Stage
import java.io.File

/**
 * Created by Priyadarshi Raj on 27/12/17
 */
object UIHandler {
    lateinit var stage: Stage
    lateinit var scene: Scene
    lateinit var root: GridPane

    private var file: File? = null
    private var filenameLabel = Label("Please select music file")

    private val buttons = arrayListOf<Button>()
    private val textFields = arrayListOf<TextField>()
    private val labels = arrayListOf<Label>()
    private val imgView = ImageView()
    private val IMG_STREAM = ClassLoader.getSystemResourceAsStream("unavailable.png")
    private val UNAVAILABLE_IMG = Image(IMG_STREAM)

    private val fileChooser = FileChooser()
    private val fileChooserStage = Stage()

    fun initialize(stage: Stage) {
        this.stage = stage

        initStage()
        initControls()
        initFields()

        scene = Scene(root,800.0, 400.0)
        stage.scene = scene
    }

    private fun initStage() {
        stage.apply {
            title = "swagtag"
            minWidth = 800.0
            minHeight = 400.0
        }
    }

    private fun initControls() {
        // setting up the root container (GridPane)
        root = GridPane().apply {
            hgap = 10.0
            vgap = 10.0
            padding = Insets(10.0)
            alignment = Pos.CENTER
            //isGridLinesVisible = true
            columnConstraints.addAll(
                    ColumnConstraints().apply {     // col 1
                        hgrow = Priority.ALWAYS
                        halignment = HPos.CENTER

                    },
                    ColumnConstraints().apply {     // col 2
                        hgrow = Priority.ALWAYS
                    }
            )
        }

        // Setting up the file name label
        root.add(filenameLabel, 0, 0, 1, 1)

        // Setting up buttons
        for(i in 0..2) {
            buttons.add(Button())
            buttons[i].prefWidth = 70.0
            root.add(buttons[i], i+2, 0, 1, 1)
        }

        buttons.apply {
            get(0).text = "Browse"
            get(1).text = "Read"
            get(2).text = "Save"

            get(0).setOnAction { selectFile() }
            get(1).setOnAction { readFile() }
            get(2).isDisable = true
        }
    }

    private fun initFields() {
        // Setting up album art
        imgView.apply {
            image = UNAVAILABLE_IMG
            fitWidth = 300.0
            fitHeight = 300.0
            isSmooth = true
            isPreserveRatio = true
        }
        IMG_STREAM.close()

        // Setting up scrollable area
        val scrollPane = ScrollPane()
        val scrollGrid = GridPane()

        scrollPane.apply {
            content = scrollGrid
            isFitToWidth = true
        }

        scrollGrid.apply {
            hgap = 10.0
            vgap = 10.0
            padding = Insets(10.0)
            alignment = Pos.CENTER
            //isGridLinesVisible = true
            columnConstraints.addAll(
                    ColumnConstraints(),
                    ColumnConstraints().apply {
                        //setMinSize(160.0, Region.USE_COMPUTED_SIZE)
                        hgrow = Priority.ALWAYS
                    }
            )
        }

        // Adding value labels and textfields to root
        for(i in 0..11) {
            labels.add(Label("ABC"))
            textFields.add(TextField())

            scrollGrid.add(labels[i], 0, i, 1, 1)
            scrollGrid.add(textFields[i], 1, i, 1, 1)
        }

        (0..11).forEach { i ->
            labels[i].text = when(i) {
                0 -> "Title"
                1 -> "Artist"
                2 -> "Album"
                3 -> "Track"
                4 -> "Year"
                5 -> "Genre"
                6 -> "Comment"
                7 -> "BPM"
                8 -> "Composer"
                9 -> "Encoder"
                10 -> "Publisher"
                11 -> "Copyright"
                else -> null
            }
        }

        root.add(imgView, 0,1,1,1)
        root.add(scrollPane, 1, 1, 4, 1)
    }

    private fun selectFile() {
        file = fileChooser.run {
            title = "Open music file"
            extensionFilters.add(FileChooser.ExtensionFilter("Music files", "*.mp3"))
            showOpenDialog(fileChooserStage)
        }

        filenameLabel.text = file?.name ?: return
    }

    private fun readFile() {
        if(file == null) return

        textFields.map {
            it.text = "Not available"
            it.isDisable = true
        }

        Mp3Handler.update(file!!)
        for(i in 0 until Mp3Handler.tagData.size) {
            textFields[i].text = Mp3Handler.tagData[i]
            textFields[i].isDisable = false
        }
        imgView.image = Mp3Handler.albumArt ?: UNAVAILABLE_IMG
    }

    fun show() {
        stage.show()
    }
}