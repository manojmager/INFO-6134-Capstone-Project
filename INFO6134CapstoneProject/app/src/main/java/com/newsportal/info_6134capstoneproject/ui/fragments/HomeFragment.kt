import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.adapters.TabPageAdapter
import com.newsportal.info_6134capstoneproject.data.ApiInterface
import com.newsportal.info_6134capstoneproject.pref.NewsCategoryPrefs


class HomeFragment : Fragment() {

    private lateinit var newsCategoryPrefs: NewsCategoryPrefs
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVars(view)
        if (savedInstanceState == null) {
            // Only setup tabs if it's the first creation of the fragment
            setupTabs()
        }
    }
    private fun initVars(view: View) {
        newsCategoryPrefs = NewsCategoryPrefs(requireContext())

        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)
    }

    private fun setupTabs() {
        val categoryList = newsCategoryPrefs.getCategoryList()
        Log.d(ContentValues.TAG, "setupTabs: $categoryList")

        if (!categoryList.isNullOrEmpty()) {
            val adapter = TabPageAdapter(childFragmentManager, categoryList)
            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(viewPager)
        } else {
            // Handle case when category list is empty or null
            Log.e("HomeFragment", "Category list is empty or null")
        }
    }
}