package kr.bsjo.recyclerviewex

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import kr.bsjo.recyclerviewex.model.ModelRepo

class MainActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var viewModel: MainVm
    private lateinit var adapter: RepoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initVm()
        initAdapter()
        initLayout()
        registerObserving()
    }

    private fun initVm() {
        viewModel = ViewModelProviders.of(this).get(MainVm::class.java)
    }

    private fun initLayout() {
        editText.setOnEditorActionListener(
            object : TextView.OnEditorActionListener {
                override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        viewModel.newSearch(editText.text.toString())
                        hideKeyboard()
                        return true
                    }
                    return false
                }
            }
        )
    }

    private fun initAdapter() {
        adapter = RepoAdapter()
        recyclerview.adapter = adapter

    }

    private fun registerObserving() {
        submitItems()

        viewModel.errorToastEvent.observe(this, Observer { Toast.makeText(this, "에러발생", Toast.LENGTH_LONG) })

        viewModel.clearDataEvents.observe(this,
            Observer {
                viewModel.clearDataSource()
                submitItems()
                adapter.notifyDataSetChanged()
            }
        )

        viewModel.emptyVisibilityEvents.observe(this,
            Observer { show ->
                if (show != null) {
                    this.empty_view_imageView.visibility = if (show.peek()) View.VISIBLE else View.GONE
                }
            }
        )

        viewModel.recyclerViewLoadingEvents.observe(this,
            Observer { show ->
                if (show != null) {
                    adapter.loading = show.peek()
                }
            })
    }

    private fun submitItems() {
        viewModel.getItems()
            ?.subscribe(
                { items -> adapter.submitList(items) },
                { /** Error handle*/ }
            )
            .let { viewModel.addDisposable(it!!) }
    }

    fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = this.currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
