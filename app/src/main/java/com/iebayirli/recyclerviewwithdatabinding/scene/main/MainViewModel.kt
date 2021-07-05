package com.iebayirli.recyclerviewwithdatabinding.scene.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iebayirli.recyclerviewwithdatabinding.data.movie.ConstMovieData
import com.iebayirli.recyclerviewwithdatabinding.data.movie.Movie
import com.iebayirli.recyclerviewwithdatabinding.internal.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel(), MovieListener {

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> = _movieList

    private val _showProgressBar = MutableLiveData(false)
    val showProgressBar: LiveData<Boolean> = _showProgressBar

    val showToast = MutableLiveData<Event<Movie>>()

    init {
        submitMovieList()
    }

    private fun submitMovieList() {
        viewModelScope.launch {
            fetchMovies()
                .onStart {
                    _showProgressBar.postValue(true)
                }.catch { err ->
                    _showProgressBar.postValue(false)
                }
                .collect { list ->
                    _showProgressBar.postValue(false)
                    _movieList.value = list
                }
        }
    }

    fun addRandomMovie() {
        val rand = Random.nextInt(0, ConstMovieData.movieList.size - 1)
        ConstMovieData.movieList.add(
            ConstMovieData.movieList[rand]
        )
        submitMovieList()
    }

    private fun fetchMovies() = flow<List<Movie>> {
        delay(700)
        emit(ConstMovieData.movieList)
    }.flowOn(Dispatchers.IO)

    override fun onMovieClicked(movie: Movie) {
        showToast.value = Event(movie)
    }

    override fun onFavouriteClicked(movie: Movie) {
        val ind = ConstMovieData.movieList.indexOf(movie)
        ConstMovieData.movieList[ind].favourite = !ConstMovieData.movieList[ind].favourite
        submitMovieList()
    }
}