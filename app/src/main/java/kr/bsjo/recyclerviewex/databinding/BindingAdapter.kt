package kr.bsjo.recyclerviewex.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class BindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("loadUrl")
        fun imageLoad(imageView: ImageView, imageUrl: String?) {
                Glide.with(imageView.context)
                    .load(imageUrl)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .apply(RequestOptions().centerCrop())
                    .into(imageView)
        }
    }
}
