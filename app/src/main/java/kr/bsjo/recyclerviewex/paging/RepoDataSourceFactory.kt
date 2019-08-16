package kr.bsjo.recyclerviewex.paging

import android.util.Log
import androidx.paging.DataSource
import kr.bsjo.recyclerviewex.model.ModelRepo
import kr.bsjo.recyclerviewex.paging.base.OnDataSourceLoading

class RepoDataSourceFactory(
    var loading: OnDataSourceLoading,
    var user: String
) : DataSource.Factory<Int, ModelRepo>() {

    lateinit var source: RepoDataSource

    override fun create(): DataSource<Int, ModelRepo> {
        Log.d("${this::class.java.canonicalName} ", "RepoDataSourceFactory.create call ")

        /**
         * The DataSource should invalidate itself if the snapshot is no longer valid.
         * If a DataSource becomes invalid, the only way to query more data is to create a new DataSource from the Factory.
         * */

        source = RepoDataSource(user)
        source.onDataSourceLoading = loading
        return source
    }
}