package kr.bsjo.recyclerviewex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_repo.view.*
import kr.bsjo.recyclerviewex.model.ModelRepo

class RepoViewHolder(
    @LayoutRes layout: Int,
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(layout, parent, false)
) {
    fun onBind(item: ModelRepo) {
        itemView.repoName.text = item.name
        itemView.repoContent.text = item.description
        itemView.stars.text = item.stargazers_count.toString()
    }

}