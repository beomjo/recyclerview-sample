package kr.bsjo.recyclerviewex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kr.bsjo.recyclerviewex.adapter.RepoAdapter
import kr.bsjo.recyclerviewex.api.ApiService

class MainActivity : AppCompatActivity() {

    val disposable = CompositeDisposable()
    val TAG = this::class.java.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdapter()
    }

    private fun initAdapter() {
        val repoAdapter = RepoAdapter()
        recyclerview.adapter = repoAdapter
        loadItem(repoAdapter)
    }

    private fun loadItem(repoAdapter: RepoAdapter) {
        ApiService.github()
            .userRepo("jakewharton")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { repos -> repoAdapter.addAll(repos) },
                { e -> Log.e(TAG, e.message) }
            )
            .addTo(disposable)
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }
}

