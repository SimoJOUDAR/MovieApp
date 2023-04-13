package fr.mjoudar.oqee.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.mjoudar.oqee.databinding.LayoutRecyclerViewItemBinding
import fr.mjoudar.oqee.domain.model.Movie

class MoviesAdapter (
    private val onItemClickListener: View.OnClickListener,
    private val onContextClickListener: View.OnContextClickListener
        ) : ListAdapter<Movie, MoviesAdapter.ViewHolder>(callback) {

    companion object {
        val callback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
        }
    }

    inner class ViewHolder(val binding: LayoutRecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.binding.movie = movie
        holder.binding
        with(holder.itemView) {
            tag = movie
            setOnClickListener(onItemClickListener)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                setOnContextClickListener(onContextClickListener)
            }
        }
    }
}