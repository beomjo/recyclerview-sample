package kr.bsjo.recyclerviewex.paging.base

import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePageKeyDataSource<T> : PageKeyedDataSource<Int, T>() {
    lateinit var onDataSourceLoading: OnDataSourceLoading
    protected var compositeDisposable = CompositeDisposable()

    protected abstract fun loadInitialData(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>)
    protected abstract fun loadAditionalData(params: LoadParams<Int>, callback: LoadCallback<Int, T>)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        onDataSourceLoading.onFirstFetch()
        loadInitialData(params, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // since we are keeping data in memory, we will not need to load the data before it.
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        onDataSourceLoading.onDataLoading()
        loadAditionalData(params, callback)
    }

    protected fun submitInitialData(
        items: List<T>,
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        callback.onResult(items, 0, params.requestedLoadSize)
        if (items.isNotEmpty()) {
            onDataSourceLoading.onFirstFetchEndWithData()
        } else {
            onDataSourceLoading.onFirstFetchEndWithoutData()
        }
    }

    protected fun submitData(items: List<T>, params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        val adjacentPageKey = if (items.isEmpty()) null else params.key + items.size
        callback.onResult(items, adjacentPageKey)
        onDataSourceLoading.onDataLoadingEnd()
    }


    protected fun submitInitialError(error: Throwable) {
        onDataSourceLoading.onError()
        error.printStackTrace()
    }


    protected fun submitError(error: Throwable) {
        onDataSourceLoading.onError()
        error.printStackTrace()
    }

    override fun invalidate() {
        compositeDisposable.dispose()
        super.invalidate()
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}