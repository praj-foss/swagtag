package praj.app.swagtag

import com.mpatric.mp3agic.Mp3File
import javafx.scene.image.Image
import java.io.ByteArrayInputStream
import java.io.File

/**
 * Created by Priyadarshi Raj on 27/12/17
 */
object Mp3Handler {
    private lateinit var mp3File: Mp3File
    var tagData = arrayListOf<String>()
    var albumArt: Image? = null

    fun update(target: File) {
        tagData.clear()
        mp3File = Mp3File(target)

        when {
            mp3File.hasId3v2Tag() -> {
                val tag = mp3File.id3v2Tag
                tagData.apply {
                    add(tag.title)
                    add(tag.artist)
                    add(tag.album)
                    add(tag.track)
                    add(tag.year)
                    add(tag.genreDescription)
                    add(tag.comment)
                    add(tag.bpm.toString())
                    add(tag.composer)
                    add(tag.encoder)
                    add(tag.publisher)
                    add(tag.copyright)
                }
                albumArt = Image(ByteArrayInputStream(tag.albumImage))
            }
            mp3File.hasId3v1Tag() -> {
                val tag = mp3File.id3v1Tag
                tagData.apply {
                    add(tag.title)
                    add(tag.artist)
                    add(tag.album)
                    add(tag.track)
                    add(tag.year)
                    add(tag.genreDescription)
                    add(tag.comment)
                }
                albumArt = null
            }
        }
    }
}