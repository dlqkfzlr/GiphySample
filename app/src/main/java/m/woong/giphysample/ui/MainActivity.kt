package m.woong.giphysample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import m.woong.giphysample.R
import m.woong.giphysample.databinding.MainActivityBinding
import m.woong.giphysample.ui.favorites.FavoritesFragment
import m.woong.giphysample.ui.trending.TrendingFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding
    lateinit var tFragment: TrendingFragment
    lateinit var fFragment: FavoritesFragment
    lateinit var tabSelectedListener: TabLayout.OnTabSelectedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tFragment = TrendingFragment.newInstance()
        fFragment = FavoritesFragment.newInstance()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, tFragment)
                .commitNow()
        }
        binding = DataBindingUtil.setContentView(
            this, R.layout.main_activity
        )
        setTabLayout()
    }

    private fun setTabLayout(){
        tabSelectedListener = object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, if (tab?.position == 0) tFragment else fFragment)
                    .commitNow()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        }
        binding.tabLayout.addOnTabSelectedListener(tabSelectedListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.tabLayout.removeOnTabSelectedListener(tabSelectedListener)
    }

    companion object{
        val TAG = MainActivity::class.java.simpleName
    }
}