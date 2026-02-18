package org.example.project

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repository: DataRepository
) {

    private val scope = CoroutineScope(Dispatchers.Default)

    private val _selectedCategory = MutableStateFlow("Semua")
    val selectedCategory: StateFlow<String> = _selectedCategory

    private val _readCount = MutableStateFlow(0)
    val readCount: StateFlow<Int> = _readCount

    val newsFlow: Flow<News> =
        combine(
            repository.getNewsFlow(),
            _selectedCategory
        ) { news, category ->

            if (category == "Semua" || news.category == category)
                news
            else null

        }
            .filterNotNull()
            .map { news ->
                news.copy(title = news.title.trim())
            }
            .onEach {
                _readCount.value++
            }
            .catch { e ->
                emit(News(0, "Terjadi kesalahan: ${e.message}", "Error"))
            }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }
}
