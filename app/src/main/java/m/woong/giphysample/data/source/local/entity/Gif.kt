package m.woong.giphysample.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import m.woong.giphysample.data.source.remote.model.RemoteTrendingGiphyResponse

@Entity(tableName = "gif")
data class Gif(
    @PrimaryKey @field:SerializedName("id") val id: String,
    @field:SerializedName("height") val height: String,
    @field:SerializedName("size") val size: String,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("width") val width: String,
    @field:SerializedName("is_favorite") var isFavorite: Boolean
)

fun RemoteTrendingGiphyResponse.mapToGif(): List<Gif> {
    return this.data.map {
        val previewGif = it.images.previewGif
        Gif(it.id, previewGif.height, previewGif.size, previewGif.url, previewGif.width, false)
    }
}

