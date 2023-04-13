package fr.mjoudar.oqee.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.mjoudar.oqee.databinding.LayoutRecyclerViewCrewItemBinding
import fr.mjoudar.oqee.domain.model.CastCrew
import fr.mjoudar.oqee.domain.model.CrewMember

class CrewAdapter : ListAdapter<CrewMember, CrewAdapter.ViewHolder>(CrewAdapter.callback) {
    companion object {
        val callback = object : DiffUtil.ItemCallback<CrewMember>() {
            override fun areItemsTheSame(oldItem: CrewMember, newItem: CrewMember) = oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: CrewMember, newItem: CrewMember) = oldItem == newItem
        }
    }

    inner class ViewHolder(val binding: LayoutRecyclerViewCrewItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutRecyclerViewCrewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.crewMember = getItem(position)
    }


}