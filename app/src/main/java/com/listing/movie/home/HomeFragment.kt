package com.listing.movie.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.id.domain.model.MovieDataModel
import com.listing.movie.R
import com.listing.movie.utils.adapter.MovieAdapter
import com.listing.movie.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        with(binding){

            rvMovie.layoutManager = LinearLayoutManager(requireContext())

            btnProfile.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
            }

            viewModel.currentUser.observe(viewLifecycleOwner) {
                tvGreeting.text = "Welcome, ${it.username}"
            }

            viewModel.movieList.observe(viewLifecycleOwner) {
                rvMovie.adapter = MovieAdapter(it.results, object : MovieAdapter.MovieAdapterListener{
                    override fun onClick(movie: MovieDataModel) {
                        val b = Bundle()
                        b.putSerializable("movie", movie)
                        findNavController().navigate(R.id.action_homeFragment_to_detailFragment,b)                    }
                })
            }

            viewModel.isError.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), viewModel.errorMessage.value, Toast.LENGTH_LONG).show()

            }
        }
        return binding.root
    }
}