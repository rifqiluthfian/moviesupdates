package org.d3if0084.moviesupdates.ui.paket

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if0084.moviesupdates.R
import org.d3if0084.moviesupdates.databinding.FragmentMovieBinding
import org.d3if0084.moviesupdates.network.ApiStatus2

class PaketFragment : Fragment() {
    private val viewModel: PaketViewModel by lazy {
        ViewModelProvider(this)[PaketViewModel::class.java]
    }

    private lateinit var binding: FragmentMovieBinding
    private lateinit var myAdapter: PaketAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        myAdapter = PaketAdapter()
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

    private fun updateProgress(status: ApiStatus2) {
        when (status) {
            ApiStatus2.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus2.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
            }
            ApiStatus2.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }


}