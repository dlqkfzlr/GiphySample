package m.woong.giphysample.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import m.woong.giphysample.R

/* ImageView */
@BindingAdapter("imageUrl")
fun ImageView.setUrl(url: String) {
    url.let {
        Glide.with(this)
            .load(url)
            .fitCenter()
            .error(R.drawable.ic_baseline_error_24)
            .into(this)
    }
}


/* RecyclerView*/
@BindingAdapter("gridLayoutManager")
fun RecyclerView.setGridLayoutManager(columns: Int){
    this.layoutManager = GridLayoutManager(this.context, columns)
}