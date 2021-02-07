package m.woong.giphysample.ui.trending

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import m.woong.giphysample.R
import m.woong.giphysample.data.source.local.entity.Gif
import m.woong.giphysample.databinding.TrendingFragmentBinding
import m.woong.giphysample.ui.MainViewModel
import m.woong.giphysample.ui.adapter.GifLoadStateAdapter
import m.woong.giphysample.ui.adapter.TrendingGifToggleListener
import m.woong.giphysample.ui.adapter.TrendingGifAdapter

@OptIn(ExperimentalPagingApi::class)
@AndroidEntryPoint
class TrendingFragment : Fragment(), TrendingGifToggleListener {

    companion object {
        fun newInstance() = TrendingFragment()
        val TAG = TrendingFragment::class.java.simpleName
    }

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: TrendingFragmentBinding
    private lateinit var adapter: TrendingGifAdapter
    private var trendingJob: Job? = null
    private var _listener: TrendingGifToggleListener? = null
    private val listener get() = _listener!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.trending_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        _listener = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        initTrending()
        initSwipeToRefresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _listener = null
    }

    private fun initAdapter() {
        adapter = TrendingGifAdapter(listener)
        binding.rvTrending.adapter = adapter.withLoadStateFooter(
            footer = GifLoadStateAdapter(adapter::retry)
        )
    }

    private fun initSwipeToRefresh() {
//        binding.srTrending.setOnRefreshListener { adapter.refresh() }
    }

    private fun initTrending(){
        trendingJob?.cancel()
        trendingJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getTrendingGif().collectLatest {
                adapter.submitData(it)
            }
        }
        /*viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvTrending.scrollToPosition(0) }
        }*/
    }

    override fun onToggleFavorite(gif: Gif) {
        gif.isFavorite = !gif.isFavorite
        viewModel.saveFavoriteGif(gif)
    }
}
