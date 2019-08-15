package kr.bsjo.recyclerviewex.paging.base

interface OnDataSourceLoading {
    fun onFirstFetch()
    fun onFirstFetchEndWithData()
    fun onFirstFetchEndWithoutData()
    fun onDataLoading()
    fun onDataLoadingEnd()
    fun onInitialError()
    fun onError()
}