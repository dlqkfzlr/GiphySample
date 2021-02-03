package m.woong.giphysample.data.source.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "gifs")
data class Gifs(
    @PrimaryKey @field:SerializedName("id") val id: Long,
    @field:SerializedName("import_datetime") val importDatetime: String,
    @Embedded @field:SerializedName("preview_gif") val previewGif: PreviewGif,
    @field:SerializedName("is_favorite") val isFavorite: Boolean
)

data class PreviewGif(
    val height: String,
    val size: String,
    val url: String,
    val width: String
)
