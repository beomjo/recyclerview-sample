package kr.bsjo.recyclerviewex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.bsjo.recyclerviewex.model.ModelRepo
import kr.bsjo.recyclerviewex.paging.base.BaseDiffAdapter
import kr.bsjo.recyclerviewex.paging.base.VIEW_TYPE_NORMAL

class RepoAdapter : BaseDiffAdapter<ModelRepo, RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_NORMAL)
            RepoViewHolder(R.layout.item_repo, parent)
        else LoadingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_NORMAL) {
            (holder as RepoViewHolder).onBind(getItem(position))
        }
    }

}