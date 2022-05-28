package com.accenture.beecycle.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isInvisible
import com.accenture.beecycle.common.BaseAdapter
import com.accenture.beecycle.databinding.ItemTeamBinding
import com.accenture.beecycle.domain.models.Team
import com.bumptech.glide.Glide

class TeamAdapter : BaseAdapter<ItemTeamBinding, Team>() {

    override fun presentBinding(parent: ViewGroup): ItemTeamBinding =
        ItemTeamBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

    override fun onBindViewHolder(holder: ViewHolder<ItemTeamBinding>, position: Int) {
        val team = data?.get(position)
        with(holder.binding) {
            teamName.text = team?.name
            teamMembers.text = "${team?.members?.size} in this team"
            setAvatars(this, team?.members)
        }
    }

    private fun setAvatars(binding: ItemTeamBinding, members: List<String>?) {
        when (members?.size) {
            0 -> hideViews(binding.teamMember1, binding.teamMember2, binding.teamMember3, binding.teamMember4)
            1 -> {
                hideViews(binding.teamMember2, binding.teamMember3, binding.teamMember4)

                loadImage(binding.teamMember1, members[0])
            }
            2 -> {
                hideViews(binding.teamMember3, binding.teamMember4)

                loadImage(binding.teamMember1, members[0])
                loadImage(binding.teamMember2, members[1])
            }
            3 -> {
                hideViews(binding.teamMember4)

                loadImage(binding.teamMember1, members[0])
                loadImage(binding.teamMember2, members[1])
                loadImage(binding.teamMember3, members[2])
            }
            else -> {
                loadImage(binding.teamMember1, members?.get(0))
                loadImage(binding.teamMember2, members?.get(1))
                loadImage(binding.teamMember3, members?.get(2))
                loadImage(binding.teamMember4, members?.get(3))
            }
        }
    }

    private fun hideViews(vararg views: View) {
        views.forEach { it.isInvisible = true }
    }

    private fun loadImage(view: ImageView, url: String?) {
        Glide.with(view)
            .load(url)
            .circleCrop()
            .into(view)
    }
}