package com.example.vasektomato.viewmodel

import androidx.lifecycle.*
import com.example.vasektomato.db.model.Tomato
import com.example.vasektomato.db.repository.TomatoRepository
import kotlinx.coroutines.launch

class TomatoViewModel (private val repository: TomatoRepository) : ViewModel() {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allTomatoes: LiveData<List<Tomato>> = repository.allTomatoes.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(tomato: Tomato) = viewModelScope.launch {
        repository.insert(tomato)
    }

    fun findByName(name: String) : Tomato {
        return repository.findTomatoByName(name)
    }
}

class TomatoViewModelFactory(private val repository: TomatoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TomatoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TomatoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
