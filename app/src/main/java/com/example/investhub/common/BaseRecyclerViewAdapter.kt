package com.example.investhub.common

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<S, T : RecyclerView.ViewHolder> : RecyclerView.Adapter<T>() {

    /**
     * list of all items
     */
    private var _items = mutableListOf<S>()

    var items: List<S> = _items
        private set

    var isLoadingAdded = false

    /**
     * create view holder of recycler view item
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        return createItemViewHolder(parent, viewType)
    }

    /**
     * bind recycler view item with data
     */
    override fun onBindViewHolder(holder: T, position: Int) {
        bindItemViewHolder(holder, position)
    }

    /**
     * get count for visible count
     */
    override fun getItemCount() = _items.size

    /**
     * abstract method to create custom view holder
     */
    protected abstract fun createItemViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): T

    /**
     * abstract method to bind custom data
     */
    protected abstract fun bindItemViewHolder(holder: T, position: Int)

    /**
     * add all items to list
     */
    fun addAll(items: List<S>, clearPreviousItems: Boolean = false) {
        if (clearPreviousItems) {
            this._items.clear()
        }
        this._items.addAll(items)
        notifyDataSetChanged()
    }

    /**
     * add item (at position - optional)
     */
    fun addItem(item: S, position: Int = _items.size, clearPreviousItems: Boolean = false) {
        var adapterPosition = position
        if (clearPreviousItems) {
            this._items.clear()
            adapterPosition = 0 // set position to 0 after items arrayList gets clear
        }
        this._items.add(adapterPosition, item)
        notifyItemInserted(adapterPosition)
    }

    /**
     * remove item from position
     */
    fun removeItem(position: Int) {
        if (position != -1) {
            _items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    /**
     * get item at position
     */
    fun getItemAt(position: Int): S {
        return _items[position]
    }

    /**
     * update item at position
     */
    fun updateItemAt(position: Int, item: S) {
        if (position != -1) {
            _items[position] = item
            notifyItemChanged(position)
        }
    }

    /**
     * To clear the adapter
     */
    fun clearAdapter() {
        _items.clear()
        notifyDataSetChanged()
    }

    fun showLoadMore(loadMoreItem: S) {
        isLoadingAdded = true
        addItem(loadMoreItem)
    }

    fun hideLoadMore() {
        if (isLoadingAdded) {
            isLoadingAdded = false
            removeItem(itemCount - 1)
        }
    }
}