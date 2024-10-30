package com.yurikolesnikov.stark

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.io.FileOutputStream

class StarkWebViewClient(
    private val context: Context,
    private val onPageFinished: () -> Unit
) : WebViewClient() {
    private val logoFileName = "google_logo.png"

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        onPageFinished()
    }

    override fun onLoadResource(view: WebView?, url: String?) {
        super.onLoadResource(view, url)
        Log.d("Yura", "Resource loaded: $url")
    }

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        request?.let { resourceRequest ->
            Log.d("Yura", "Resource request: ${resourceRequest.url}")
            if (resourceRequest.url.toString().contains(LOGO_NAME)) {
                val logoFile = File(context.filesDir, logoFileName)

                if (logoFile.exists()) {
                    Log.d("Yura", "Getting log from cache")
                    return WebResourceResponse(
                        "image/png",
                        "UTF-8",
                        logoFile.inputStream()
                    )
                } else {
                    Log.d("Yura", "Saving logo to cache")
                    saveImageToInternalStorage(
                        context,
                        resourceRequest.url.toString(),
                    )
                    return super.shouldInterceptRequest(view, request)
                }
            }
        }
        return super.shouldInterceptRequest(view, request)
    }

    private fun saveImageToInternalStorage(context: Context, imageUrl: String) {
        Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    try {
                        Log.d("Yura", "Resource is ready: $resource")
                        val file = File(context.filesDir, logoFileName)
                        val outputStream = FileOutputStream(file)
                        resource.compress(
                            Bitmap.CompressFormat.PNG,
                            100,
                            outputStream
                        )
                        outputStream.flush()
                        outputStream.close()
                    } catch (error: Exception) {
                        Log.e("Yura", "Error saving image to file.")
                    }
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    Log.e("Yura", "Error loading image.")
                }
            })
    }
}