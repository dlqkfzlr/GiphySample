package m.woong.giphysample.ui.trending

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import m.woong.giphysample.R
import m.woong.giphysample.data.source.local.entity.Gif
import m.woong.giphysample.databinding.TrendingFragmentBinding
import m.woong.giphysample.ui.MainActivity
import m.woong.giphysample.ui.MainViewModel
import m.woong.giphysample.ui.favorites.FavoritesFragment
import m.woong.giphysample.utils.setGridLayoutManager
import java.lang.ClassCastException

@OptIn(ExperimentalPagingApi::class)
@AndroidEntryPoint
class TrendingFragment : Fragment(), FavoriteToggleListener {

    companion object {
        fun newInstance() = TrendingFragment()
        val TAG = TrendingFragment::class.java.simpleName
    }

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: TrendingFragmentBinding
    private lateinit var adapter: TrendingGifAdapter
    private var trendingJob: Job? = null
    private var _listener: FavoriteToggleListener? = null
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _listener = null
    }

    private fun initAdapter() {
        adapter = TrendingGifAdapter(listener)
        binding.rvTrending.adapter = adapter
    }


    private fun initTrending(){
        trendingJob?.cancel()
        trendingJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getTrendingGif().collectLatest {
                adapter.submitData(it)
                Log.d(TAG, "adapter.itemCount:${adapter.itemCount}")
            }
        }
    }

    override fun onToggleFavorite(gif: Gif) {
        gif.isFavorite = !gif.isFavorite
        Log.d(TAG, "gif:${gif}")
        viewModel.saveFavoriteGif(gif)
    }
}
