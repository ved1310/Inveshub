package com.example.investhub.extenstion

import android.net.Uri
import android.widget.ImageView
import androidx.navigation.fragment.R
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.util.Locale

fun ImageView?.loadCenterCropDrawable(url: String?) {
    this ?: return
    url ?: return

   Glide.with(context)
      .load(url)
        .centerCrop().into(this)
}

/*fun ImageView.loadResource(
    path: Int?, isCircle: Boolean = false, isCenterCrop: Boolean = false,
) {
    if (path == null)
        return

   val options:com.bumptech.glide.request.RequestOptions=com.bumptech.glide.request.RequestOptions().dontTransform()

    if (isCenterCrop) {
        options.centerCrop()
    }
    if (isCircle) {
        options.optionalCircleCrop()
    }

    val result = Glide.with(context)
        .load(path)
        .placeholder(R.color.primary)
        .error(R.color.white)
        .apply(options)
    result.into(this)
}*/


fun ImageView.loadImage(
    path: String?, isCircle: Boolean = false, isCenterCrop: Boolean = false,
) {
    if (!path.isRequiredField())
        return

    val options:com.bumptech.glide.request.RequestOptions=com.bumptech.glide.request.RequestOptions().dontTransform()

    if (isCenterCrop) {
        options.centerCrop()
    }
    if (isCircle) {
        options.optionalCircleCrop()
    }

    val url: String = if (path!!.isUrl()) {
        path
    } else {
        Uri.fromFile(File(path!!)).toString()
    }

    val result = Glide.with(context)
        .load(url)
     .placeholder(com.example.investhub.R.drawable.shope)
        .error(com.example.investhub.R.drawable.shope)
        .apply(options)
    result.into(this)
}
fun String?.isRequiredField(): Boolean {
    return this != null && isNotEmpty() && isNotBlank()
}

fun String.isUrl(): Boolean {
    return this.lowercase(Locale.getDefault()).contains("http") ||
            this.lowercase(Locale.getDefault()).contains("http")
}

fun ImageView.loadImageWithPlaceHolder(
    path: String, isCircle: Boolean = false, isCenterCrop: Boolean = false,placeHolder:Int
) {
    if (!path.isRequiredField())
        return

    val options: RequestOptions = RequestOptions()
        .centerCrop()
     .diskCacheStrategy(DiskCacheStrategy.ALL)
     .priority(Priority.HIGH)
        .dontTransform()

    if (isCenterCrop) {
        options.centerCrop()
    }
    if (isCircle) {
        options.optionalCircleCrop()
    }

    val url: String = if (path.isUrl()) {
        path
    } else {
        Uri.fromFile(File(path)).toString()
    }

    val result = Glide.with(context)
        .load(url)
        .placeholder(placeHolder)
        .error(placeHolder)
        .apply(options)
    result.into(this)
}
