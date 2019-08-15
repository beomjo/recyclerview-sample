package kr.bsjo.recyclerviewex.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.bsjo.recyclerviewex.R
import kr.bsjo.recyclerviewex.model.ModelRepo

class RepoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val items = mutableListOf<ModelRepo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepoViewHolder(R.layout.item_repo, parent)
    }

    override fun getItemCount(): Int = items.count()


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RepoViewHolder).onBind(items[position])
    }

    fun add(item: ModelRepo) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun addAll(items: List<ModelRepo>) {
        val sizeOld = itemCount
        this.items.addAll(items)
        val sizeNew = itemCount

        notifyItemRangeInserted(sizeOld, sizeNew)
    }

    fun clear() {
        val size = items.size
        this.items.clear()
        notifyItemRangeRemoved(0, size)
    }

}