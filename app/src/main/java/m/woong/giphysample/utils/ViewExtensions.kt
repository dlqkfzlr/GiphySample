package m.woong.giphysample.utils

import android.util.Log
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import m.woong.giphysample.R
import m.woong.giphysample.data.source.local.entity.Gif

/* ImageView */
@BindingAdapter("imageUrl")
fun ImageView.setUrl(gif: Gif) {
    /*val x = gif.width.toFloat()
    val y = gif.height.toFloat()
    val ratio: Float = (x / y)
    Log.d(
        "ImageView", "x:$x, y:$y, ratio:$ratio \n " +
                "iv.width:${this.width}, iv.height:${this.height}"
    )*/

    Glide.with(this)
        .load(gif.url)
        .fitCenter()
        .error(R.drawable.ic_baseline_error_24)
        .into(this)
}

/* ToggleButton */
@BindingAdapter("favorite")
fun ToggleButton.setFavorite(isFavorite: Boolean) {
    this.isChecked = isFavorite
}

/* RecyclerView*/
@BindingAdapter("gridLayoutManager")
fun RecyclerView.setGridLayoutManager(columns: Int) {
    this.layoutManager = StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL)
}