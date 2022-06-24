package org.d3if0084.moviesupdates.ui.paket

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if0084.moviesupdates.R
import org.d3if0084.moviesupdates.databinding.ListItemBinding
import org.d3if0084.moviesupdates.model.Paket
import org.d3if0084.moviesupdates.network.PaketApi

class PaketAdapter : RecyclerView.Adapter<PaketAdapter.ViewHolder>() {

    private val data = mutableListOf<Paket>()

    fun updateData(newData: List<Paket>) {
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
        fun bind(paket: Paket) = with(binding) {
            namaTextView.text = paket.nama
            tahunTextView.text = paket.harga
            Glide.with(imageView.context)
                .load(PaketApi.getPaketUrl(paket.imageId))
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imageView)
            root.setOnClickListener {
                val pesan = root.context.getString(R.string.pesan, paket.nama)
                Toast.makeText(root.context, pesan, Toast.LENGTH_LONG).show()
            }
        }
    }
}