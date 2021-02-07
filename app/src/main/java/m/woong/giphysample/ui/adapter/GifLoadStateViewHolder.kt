package m.woong.giphysample.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import m.woong.giphysample.R
import m.woong.giphysample.databinding.GifLoadStateFooterViewItemBinding

class GifLoadStateViewHolder(
    private val binding: GifLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.ivRetry.also {
            it.setOnClickListener { retry.invoke() }
        }
    }

    fun bind(loadState: LoadState) {
        Log.d("LOAD_GIF", "GifLoadStateAdapter.bind호출 LoadState:$loadState")
        binding.pbLoading.isVisible = loadState is LoadState.Loading
        binding.ivRetry.isVisible = loadState !is LoadState.Loading
    }

    companion object{
        fun create(parent: ViewGroup, retry: () -> Unit): GifLoadStateViewHolder{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.gif_load_state_footer_view_item, parent, false)
            val binding = GifLoadStateFooterViewItemBinding.bind(view)
            return GifLoadStateViewHolder(binding, retry)
        }
    }
}