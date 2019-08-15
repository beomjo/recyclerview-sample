package kr.bsjo.recyclerviewex.paging

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kr.bsjo.recyclerviewex.api.ApiService
import kr.bsjo.recyclerviewex.model.ModelRepo
import kr.bsjo.recyclerviewex.paging.base.BasePageKeyDataSource

class RepoDataSource(private val user: String) : BasePageKeyDataSource<ModelRepo>() {

    override fun loadInitialData(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ModelRepo>) {
        ApiService.github()
            .userRepo(user, 0, params.requestedLoadSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { items -> submitInitialData(items, params, callback) },
                { error -> submitInitialError(error) }
            )
            .addTo(compositeDisposable)
    }

    override fun loadAditionalData(params: LoadParams<Int>, callback: LoadCallback<Int, ModelRepo>) {
        ApiService.github()
            .userRepo(user, params.key, params.requestedLoadSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { items -> submitData(items, params, callback) },
                { error -> submitError(error) }
            )
            .addTo(compositeDisposable)
    }
}