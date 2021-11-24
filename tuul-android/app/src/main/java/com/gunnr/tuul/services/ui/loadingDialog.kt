package com.gunnr.tuul.services.ui

import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.widget.ImageView
import com.gunnr.tuul.R


class LoadingBar
    (var activity: Activity) {
    var dialog: Dialog? = null

    fun showDialog() {

        dialog = Dialog(activity)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCancelable(false)
        dialog!!.setContentView(R.layout.loading_layout)

        val gifImageView = dialog!!.findViewById(R.id.custom_loading_imageView) as ImageView


        val imageViewTarget = GlideDrawableImageViewTarget(gifImageView)

        Glide.with(activity)
            .load(R.drawable.loading)
            .placeholder(R.drawable.loading)
            .centerCrop()
            .crossFade()
            .into(imageViewTarget)

        dialog!!.show()
    }

    fun hideDialog() {
        dialog!!.dismiss()
    }

}