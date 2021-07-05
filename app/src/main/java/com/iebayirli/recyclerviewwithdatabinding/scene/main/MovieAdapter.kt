package com.iebayirli.recyclerviewwithdatabinding.scene.main

import com.iebayirli.recyclerviewwithdatabinding.R
import com.iebayirli.recyclerviewwithdatabinding.base.BaseAdapter
import com.iebayirli.recyclerviewwithdatabinding.data.movie.Movie
import com.iebayirli.recyclerviewwithdatabinding.databinding.ItemMovieBinding

class MovieAdapter(
    private val list: List<Movie>,
    private val movieListener: MovieListener
) : BaseAdapter<ItemMovieBinding, Movie>(list) {

    override val layoutId: Int = R.layout.item_movie

    override fun bind(binding: ItemMovieBinding, item: Movie) {
        binding.apply {
            movie = item
            listener = movieListener
            executePendingBindings()
        }
    }
}

interface MovieListener {
    fun onMovieClicked(movie: Movie)
    fun onFavouriteClicked(movie: Movie)
}