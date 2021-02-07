package m.woong.giphysample.utils

import m.woong.giphysample.data.source.local.entity.Gif
import m.woong.giphysample.data.source.remote.model.RemoteTrendingGiphyResponse

fun RemoteTrendingGiphyResponse.mapToGif(): List<Gif> {
    return this.data.map {  // fixed width : 200 pixel
        val fixedWidthGif = it.images.fixedWidth
        Gif(it.id, fixedWidthGif.url, fixedWidthGif.width.toInt(), fixedWidthGif.height.toInt(), false)
    }
}
