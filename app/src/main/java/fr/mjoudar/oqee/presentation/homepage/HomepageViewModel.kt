package fr.mjoudar.oqee.presentation.homepage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.mjoudar.oqee.data.network.ApiClient
import fr.mjoudar.oqee.data.repositories.MoviesRepository
import fr.mjoudar.oqee.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomepageViewModel @Inject constructor(private val repository : MoviesRepository): ViewModel()  {

    private val _moviesStateFlow = MutableStateFlow<MoviesUiState>(MoviesUiState.Loading)
    val moviesStateFlow = _moviesStateFlow.asStateFlow().stateIn(
        scope = viewModelScope,
        initialValue = MoviesUiState.Loading,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
    )

    init {
        getMoviesState()
    }

    // Update the moviesUiState based on the received Response
    private fun getMoviesState() = viewModelScope.launch(Dispatchers.IO) {
        val defaultException = Exception("An unidentified error occurred. We couldn't load the data. Please, check your internet connection.")
        val response = repository.getData()
        if (response.isSuccessful) {
            _moviesStateFlow.emit(MoviesUiState.Success(response.body.map { it.toMovie() }))
        } else {
            val e = response.exception ?: defaultException
            _moviesStateFlow.emit(MoviesUiState.Error(e))
        }
    }
}

sealed class MoviesUiState {
    object Loading: MoviesUiState()
    data class Success(val movies : List<Movie>): MoviesUiState()
    data class Error(val error: Exception): MoviesUiState()
}