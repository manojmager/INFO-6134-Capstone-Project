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

    private lateinit var apiInterface: ApiInterface
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVars(view)
        setupTabs()

//        apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
//        val call = apiInterface.getArticles(
//            topic = "business",
//            sources = listOf("nytimes.com", "theguardian.com"),
//            apiKey = "z_LylxinAVC3Q1-Qu43kgeCKlT-jWaf9uKPKLKwy91o"
//        )
//
//        call.enqueue(object : Callback<ArticlesResponse> {
//            override fun onResponse(call: Call<ArticlesResponse>, response: Response<ArticlesResponse>) {
//                if (response.isSuccessful) {
//                    val articlesResponse = response.body()
//                    // Log the response body
//                    Log.d(TAG, "Response body: ${response.body()}")
//                    articlesResponse?.let {
//                        // Process articlesResponse.articles list
//                        Log.d(TAG, "getResult: $it")
//                    }
//                } else {
//                    // Handle error response
//                    Log.e(TAG, "Failed to fetch articles: ${response.errorBody()?.string()}")
//                }
//            }
//
//
//            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
//                // Handle network failures
//                Log.e(TAG, "Error fetching latest headlines: ${t.message}")
//            }
//        })
    }
    private fun initVars(view: View) {
        newsCategoryPrefs = NewsCategoryPrefs(requireContext())

        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)
    }

    private fun setupTabs() {
        val categoryList = newsCategoryPrefs.getCategoryList()

        if (!categoryList.isNullOrEmpty()) {
            val adapter = TabPageAdapter(childFragmentManager, categoryList)
            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(viewPager)
        } else {
            // Handle case when category list is empty or null
            Log.e("HomeFragment", "Category list is empty or null")
        }
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
