package com.gunnr.tuul.services.misc

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.commonsware.cwac.provider.StreamProvider

class PDFUtil {
    private val authority = "com.gunnr.tuul"
    private val provider = Uri.parse("content://$authority")
    private val assetPath = "assets/"

    private fun getUri(filename: String): Uri {
        val path = "$assetPath$filename.pdf"

        return provider
            .buildUpon()
            .appendPath(StreamProvider.getUriPrefix(authority))
            .appendPath(path)
            .build()
    }

    fun open(context: AppCompatActivity, filename: String){
        val i = Intent()
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        i.setDataAndType(getUri(filename), "application/pdf")
        context.startActivity(i)
    }
}