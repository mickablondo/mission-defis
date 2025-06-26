package dev.mickablondo.missiondefis.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.mickablondo.missiondefis.databinding.ItemChallengeBinding
import dev.mickablondo.missiondefis.model.Challenge

class ChallengeAdapter(
    private val onCheckChanged: (Challenge, Boolean) -> Unit
) : ListAdapter<Challenge, ChallengeAdapter.ChallengeViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val binding = ItemChallengeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChallengeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge = getItem(position)
        holder.bind(challenge)
    }

    inner class ChallengeViewHolder(private val binding: ItemChallengeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(challenge: Challenge) {
            binding.checkbox.text = "${challenge.icon} ${challenge.title}"
            binding.checkbox.isChecked = challenge.completed

            binding.checkbox.setOnCheckedChangeListener(null)
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                onCheckChanged(challenge, isChecked)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Challenge>() {
        override fun areItemsTheSame(oldItem: Challenge, newItem: Challenge) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Challenge, newItem: Challenge) = oldItem == newItem
    }
}
