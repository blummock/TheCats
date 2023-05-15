package com.blumock.feeding.recycler

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DownloadDialog(private val context: Context) {

    suspend operator fun invoke() = suspendCoroutine {
        MaterialAlertDialogBuilder(context).apply {
            setTitle(context.getString(com.blumock.common.R.string.download_notification))
            setMessage(context.getString(com.blumock.common.R.string.download_dialog_message))
            setPositiveButton(context.getString(com.blumock.common.R.string.yes)) { a, _ ->
                it.resume(true)
                a.cancel()
            }
            setNegativeButton(context.getString(com.blumock.common.R.string.no)) { a, _ ->
                a.cancel()
            }
            show()
        }
        it.resume(false)
    }
}