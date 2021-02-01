package m.woong.giphysample.ui.trending

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import m.woong.giphysample.R
import m.woong.giphysample.data.source.remote.model.RemoteTrendingGiphyResponse.*
import m.woong.giphysample.databinding.TrendingViewItemBinding

class TrendingGifAdapter: PagingDataAdapter<Data, TrendingGifAdapter.TrendingViewHolder>(DIFF_CALLBACK) {


    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val binding = DataBindingUtil.inflate<TrendingViewItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.trending_view_item,
            parent,
            false
        )
        return TrendingViewHolder(binding)
    }

    class TrendingViewHolder(private val binding: TrendingViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Data) {
            binding.apply {
                this.item = item
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean =
                oldItem == newItem
        }
    }
}