package org.d3if0084.moviesupdates.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if0084.moviesupdates.R
import org.d3if0084.moviesupdates.databinding.ListItemBinding
import org.d3if0084.moviesupdates.model.Movie
import org.d3if0084.moviesupdates.network.MovieApi

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private val data = mutableListOf<Movie>()

    fun updateData(newData: List<Movie>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
    override fun getItemCount(): Int {
        return data.size
    }
    class ViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) = with(binding) {
            namaTextView.text = movie.nama
            latinTextView.text = movie.namaLatin
            Glide.with(imageView.context)
                .load(MovieApi.getMovieUrl(movie.imageId))
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imageView)
            root.setOnClickListener {
                val message = root.context.getString(R.string.message, movie.nama)
                Toast.makeText(root.context, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}