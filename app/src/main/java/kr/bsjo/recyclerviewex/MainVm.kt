package kr.bsjo.recyclerviewex

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kr.bsjo.recyclerviewex.api.ApiService
import kr.bsjo.recyclerviewex.databinding.BaseRecyclerViewAdapter
import kr.bsjo.recyclerviewex.databinding.ItemRepoBinding
import kr.bsjo.recyclerviewex.model.ModelRepo

class MainVm {
    val TAG = this::class.java.canonicalName
    val disposables = CompositeDisposable()
    val adapter = BaseRecyclerViewAdapter<ModelRepo, ItemRepoBinding>(R.layout.item_repo, BR.vm)

    init {
        loadRepo()
    }

    private fun loadRepo() {
        ApiService.github().userRepo("jakewharton")
            .map { it.sortedByDescending { it.stargazers_count } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { repos -> adapter.insertAll(repos) },
                { Log.e(TAG, it.message) }
            )
            .addTo(disposables)
    }
}