package kr.bsjo.recyclerviewex.paging.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Base ViewModel class with observables and helper methods needed to use in the Pagination Library
 */
abstract class BasePaginationViewModel<T : DataSource.Factory<Int, K>, K> : ViewModel() {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    protected lateinit var dataSourceFactory: T
    private var pagedObservable: Observable<PagedList<K>>? = null

    val clearDataEvents: MutableLiveData<Event<Unit>> get() = _clearDataEvents
    private val _clearDataEvents = MutableLiveData<Event<Unit>>()

    val emptyVisibilityEvents: MutableLiveData<Event<Boolean>> get() = _emptyVisibilityEvents
    private val _emptyVisibilityEvents = MutableLiveData<Event<Boolean>>()

    val recyclerViewLoadingEvents: MutableLiveData<Event<Boolean>> get() = _recyclerViewLoadingEvents
    private val _recyclerViewLoadingEvents = MutableLiveData<Event<Boolean>>()

    val errorToastEvent: MutableLiveData<Event<Unit>> get() = _errorToastEvent
    private val _errorToastEvent = MutableLiveData<Event<Unit>>()

    abstract fun getPageSize(): Int

    fun clearData() {
        this.clearDataEvents.postValue(Event(Unit))
    }

    fun clearDataSource() {
        dataSourceFactory.create()
        createPagedObservable()
    }

    fun getItems(): Observable<PagedList<K>>? {
        if (pagedObservable == null) {
            createPagedObservable()
        }
        return pagedObservable
    }

    private fun createPagedObservable() {
        pagedObservable = RxPagedListBuilder(
            dataSourceFactory,
            PagedList.Config.Builder()
                .setPageSize(getPageSize())
                .setInitialLoadSizeHint(getPageSize() * 4) // default setInitialLoadSizeHint = pageSize*3
                .setEnablePlaceholders(false)
                .build()
        )
            .buildObservable()
    }

    protected fun getListener(): OnDataSourceLoading {
        return object : OnDataSourceLoading {
            override fun onFirstFetch() {
                showRecyclerLoading()
            }

            override fun onFirstFetchEndWithData() {
                hideRecyclerLoading()
                hideEmptyVisibility()
            }

            override fun onFirstFetchEndWithoutData() {
                hideRecyclerLoading()
                showEmptyVisibility()
            }

            override fun onDataLoading() {
                showRecyclerLoading()
            }

            override fun onDataLoadingEnd() {
                hideRecyclerLoading()
            }

            override fun onInitialError() {
                hideRecyclerLoading()
                showEmptyVisibility()
                showErrorToast()
            }

            override fun onError() {
                hideRecyclerLoading()
                showEmptyVisibility()
                showErrorToast()
            }
        }
    }

    fun showEmptyVisibility() {
        emptyVisibilityEvents.postValue(Event(true))
    }

    fun hideEmptyVisibility() {
        emptyVisibilityEvents.postValue(Event(false))
    }

    fun showRecyclerLoading() {
        recyclerViewLoadingEvents.postValue(Event(true))
    }

    fun hideRecyclerLoading() {
        recyclerViewLoadingEvents.postValue(Event(false))
    }

    fun showErrorToast() {
        errorToastEvent.postValue(Event(Unit))
    }

    fun addDisposable(d: Disposable) = compositeDisposable.add(d)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}