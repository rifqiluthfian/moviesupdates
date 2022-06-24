package org.d3if0084.moviesupdates.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if0084.moviesupdates.R
import org.d3if0084.moviesupdates.databinding.FragmentMovieBinding
import org.d3if0084.moviesupdates.network.ApiStatus

class MovieFragment : Fragment() {
    private val viewModel: MovieViewModel by lazy {
        ViewModelProvider(this)[MovieViewModel::class.java]
    }

    private lateinit var binding: FragmentMovieBinding
    private lateinit var myAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        myAdapter = MovieAdapter()
        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner, {
            myAdapter.updateData(it)
        })
        viewModel.getStatus().observe(viewLifecycleOwner, {
            updateProgress(it)
        })
        viewModel.scheduleUpdater(requireActivity().application)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when(item.itemId) {
            R.id.menu_about -> {
                findNavController().navigate(
                    R.id.action_movieFragment_to_aboutFragment
                )
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }


}