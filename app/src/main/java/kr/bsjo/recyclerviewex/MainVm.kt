package kr.bsjo.recyclerviewex

import kr.bsjo.recyclerviewex.model.ModelRepo
import kr.bsjo.recyclerviewex.paging.RepoDataSourceFactory
import kr.bsjo.recyclerviewex.paging.base.BasePaginationViewModel

class MainVm : BasePaginationViewModel<RepoDataSourceFactory, ModelRepo>() {
    init {
        dataSourceFactory = RepoDataSourceFactory(getListener(), "")
    }

    override fun getPageSize(): Int = 3

    fun newSearch(user: String) {
        dataSourceFactory.user = user
        clearData()
    }
}