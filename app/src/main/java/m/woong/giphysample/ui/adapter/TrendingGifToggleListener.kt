package m.woong.giphysample.ui.adapter

import m.woong.giphysample.data.source.local.entity.Gif

interface TrendingGifToggleListener {
    fun onToggleFavorite(gif: Gif)
}