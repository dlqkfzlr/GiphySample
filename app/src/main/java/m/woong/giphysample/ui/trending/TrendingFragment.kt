package m.woong.giphysample.ui.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import m.woong.giphysample.R
import m.woong.giphysample.databinding.TrendingFragmentBinding

@AndroidEntryPoint
class TrendingFragment : Fragment() {

    companion object {
        fun newInstance() = TrendingFragment()
    }

    private val viewModel: TrendingViewModel by viewModels()
    private lateinit var binding: TrendingFragmentBinding
    private lateinit var adapter: TrendingGifAdapter
    private var trendingJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.trending_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        initTrending()
    }

    private fun initAdapter() {
        adapter = TrendingGifAdapter()
        binding.rvTrending.adapter = adapter
    }

    private fun initTrending(){
        trendingJob?.cancel()
        trendingJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getTrendingGif().collectLatest {
                adapter.submitData(it)
            }
        }
    }

}