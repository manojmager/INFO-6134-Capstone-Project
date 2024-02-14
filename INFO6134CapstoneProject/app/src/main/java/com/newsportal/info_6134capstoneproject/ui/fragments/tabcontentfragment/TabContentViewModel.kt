import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsportal.info_6134capstoneproject.data.ApiClient
import com.newsportal.info_6134capstoneproject.data.ApiInterface
import com.newsportal.info_6134capstoneproject.data.OperationCallback
import com.newsportal.info_6134capstoneproject.model.Article
import com.newsportal.info_6134capstoneproject.repository.Repository
import com.newsportal.info_6134capstoneproject.response.SourceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TabContentViewModel(private val repository: Repository) : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: MutableLiveData<List<Article>> = _articles

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<String>()
    val onMessageError: LiveData<String> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    fun loadArticles() {
        _isViewLoading.value = true
        repository.fetchArticles(object : OperationCallback<Article> {
            override fun onError(error: String?) {
                _isViewLoading.value = false
                _onMessageError.value = error
            }

            override fun onSuccess(data: List<Article>?) {
                _isViewLoading.value = false
                if (data.isNullOrEmpty()) {
                    _isEmptyList.value = true
                } else {
                    _articles.value = data
                }
            }
        })
    }
}