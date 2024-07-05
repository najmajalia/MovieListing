package com.listing.movie.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.id.di.BuildConfig
import com.id.domain.model.MovieDataModel
import com.listing.movie.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var movie: MovieDataModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDetailBinding.inflate(inflater,container,false)
        movie = arguments?.getSerializable("movie") as MovieDataModel
        with(binding){
            tvTitle.text=movie.title
            tvVote.text=movie.stringVote
            Glide.with(requireContext()).load(BuildConfig.BASE_IMAGE_URL + movie.posterPath).into(binding.ivMovie)
            tvOverview.text=movie.overview
        }
        return binding.root
    }
}