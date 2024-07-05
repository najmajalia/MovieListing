package com.listing.movie.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.id.di.BuildConfig
import com.id.domain.model.MovieDataModel
import com.listing.movie.databinding.ItemMovieBinding

class MovieAdapter(
    private val movies: List<MovieDataModel>, private var listener: MovieAdapterListener
) : RecyclerView.Adapter<MovieAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(private val bind: ItemMovieBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bindItem(movie: MovieDataModel) {
            with(bind) {
                tvTitle.text = movie.title
                tvOverview.text = movie.overview
            }
            Glide.with(itemView).load(BuildConfig.BASE_IMAGE_URL + movie.posterPath).into(bind.ivMovie)
            itemView.setOnClickListener {
                listener.onClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapter =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(adapter)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    interface MovieAdapterListener {
        fun onClick(movie: MovieDataModel)
    }
}