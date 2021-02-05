package m.woong.giphysample.ui.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import m.woong.giphysample.R
import m.woong.giphysample.databinding.FavoritesFragmentBinding
import m.woong.giphysample.ui.MainViewModel

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
        val TAG = FavoritesFragment::class.java.simpleName
    }

    private val viewModel: MainViewModel by activityViewModels()
    /*private var _binding: FavoritesFragmentBinding? = null
    private val binding get() = _binding!!*/
    private lateinit var binding: FavoritesFragmentBinding
    private lateinit var fAdapter: FavoriteGifAdapter
    private var favoriteJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")
//        _binding = FavoritesFragmentBinding.inflate(inflater, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.favorites_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated")
        initAdapter()
        initFavorite()
    }

    private fun initAdapter() {
        fAdapter = FavoriteGifAdapter()
        binding.rvFavorites.adapter = fAdapter
        /*binding.rvFavorites.apply {
            setGridLayoutManager(2)
            this.adapter = fAdapter
        }*/
    }

    private fun initFavorite(){
        favoriteJob?.cancel()
        favoriteJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFavoriteGif().collectLatest {
                fAdapter.submitData(it)
                Log.d(TAG, "fAdapter.itemCount:${fAdapter.itemCount}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
//        _binding = null
    }

}