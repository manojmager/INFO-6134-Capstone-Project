import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.newsportal.info_6134capstoneproject.api.ApiClient
import com.newsportal.info_6134capstoneproject.api.ApiInterface
import com.newsportal.info_6134capstoneproject.response.SourceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TabContentViewModel(application: Application) : AndroidViewModel(application) {
    private val apiInterface: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)

    val sources: MutableLiveData<SourceResponse> by lazy {
        MutableLiveData<SourceResponse>()
    }

    fun fetchSources(topic: String?, language: String?, countries: String?, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    apiInterface.getSources(topic, language, countries, apiKey).execute()
                }
                if (response.isSuccessful) {
                    sources.postValue(response.body())
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }


    fun fetchLatestHeadlines(source: String?, sortBy: String?, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    if (source != null) {
                        apiInterface.getArticles(source, sortBy, apiKey).execute()
                    } else {
                        null // Return null if source is null
                    }
                }
                if (response != null && response.isSuccessful) {
                    // Handle successful response

                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }


}
