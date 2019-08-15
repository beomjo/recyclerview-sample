package kr.bsjo.recyclerviewex.databinding

import androidx.databinding.ViewDataBinding
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup

open class BaseRecyclerViewAdapter<ITEM : Any, B : ViewDataBinding>(
        @LayoutRes val layout: Int,
        val bindingVariableId: Int? = null)
    : RecyclerView.Adapter<BaseViewHolder<B>>() {
    val items = mutableListOf<ITEM>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<B> {
        return object : BaseViewHolder<B>(
                layout = layout,
                parent = parent,
                bindingVariableId = bindingVariableId
        ) {}
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<B>, position: Int) {
        holder.onBindViewHolder(items[position])
    }

    open fun replaceAll(items: List<ITEM>) {
        this.items.clear()
        this.items.addAll(items)

        notifyDataSetChanged()
    }

    open fun insertAll(items: List<ITEM>) {
        val sizeOld = itemCount

        this.items.addAll(items)

        val sizeNew = itemCount

        notifyItemRangeInserted(sizeOld, sizeNew)
    }

    open fun add(item: ITEM) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    open fun clear() {
        val size = items.size
        this.items.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun changeToCopyItems(){
        items.clear()
    }
}