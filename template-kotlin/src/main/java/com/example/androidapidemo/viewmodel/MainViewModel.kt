package com.example.androidapidemo.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.androidapidemo.ApiDemoApplication
import com.example.androidapidemo.data.ApiRepository
import com.example.androidapidemo.model.ApiItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ApiRepository = (application as ApiDemoApplication).repository

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private var searchJob: Job? = null
    private var currentQuery = ""

    val apis: LiveData<List<ApiItem>> = repository.getAllApiItems()
        .catch { e ->
            _error.value = e.message
        }
        .asLiveData()

    init {
        loadApis()
    }

    private fun loadApis() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                repository.refreshApiItems(createSampleApiList())
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchApis(query: String) {
        currentQuery = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300) // Debounce
            if (currentQuery == query) {
                repository.searchApiItems(query)
                    .catch { e ->
                        _error.value = e.message
                    }
                    .collect { items ->
                        // The LiveData will be updated automatically through the Flow
                    }
            }
        }
    }

    private fun createSampleApiList(): List<ApiItem> {
        return listOf(
            ApiItem(
                1,
                "RecyclerView",
                "用于高效显示大量数据的可滚动列表",
                "UI Components",
                "RecyclerView的基本使用示例",
                21
            ),
            ApiItem(
                2,
                "ViewModel",
                "用于管理UI相关的数据，在配置更改时保持数据",
                "Architecture Components",
                "ViewModel的使用示例",
                19
            ),
            ApiItem(
                3,
                "WorkManager",
                "用于管理后台任务的API",
                "Background Tasks",
                "WorkManager的使用示例",
                23
            ),
            ApiItem(
                4,
                "Room",
                "提供SQLite数据库的抽象层",
                "Data Storage",
                "Room数据库的使用示例",
                20
            ),
            ApiItem(
                5,
                "Navigation",
                "处理应用内导航的框架",
                "Navigation",
                "Navigation的使用示例",
                21
            ),
            ApiItem(
                6,
                "CameraX",
                "用于简化相机应用开发的API",
                "Camera",
                "CameraX的使用示例",
                21
            ),
            ApiItem(
                7,
                "DataStore",
                "用于替代SharedPreferences的数据存储解决方案",
                "Data Storage",
                "DataStore的使用示例",
                23
            ),
            ApiItem(
                8,
                "Compose",
                "用于构建原生UI的现代工具包",
                "UI",
                "Compose的使用示例",
                21
            ),
            ApiItem(
                9,
                "LiveData",
                "具有生命周期感知能力的数据持有者类",
                "Architecture Components",
                "LiveData的使用示例",
                19
            ),
            ApiItem(
                10,
                "ViewBinding",
                "更安全高效地访问视图的工具",
                "UI",
                "ViewBinding的使用示例",
                20
            )
        )
    }
} 