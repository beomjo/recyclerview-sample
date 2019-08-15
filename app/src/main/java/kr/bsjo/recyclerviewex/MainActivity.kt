package kr.bsjo.recyclerviewex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import kr.bsjo.recyclerviewex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val viewModel = MainVm()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .setVariable(BR.vm, viewModel)
    }

    override fun onDestroy() {
        viewModel.disposables.clear()
        super.onDestroy()
    }
}
