package com.blumock.feeding.recycler

import android.content.Context
import com.blumock.common.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DownloadDialog(private val context: Context) {

    suspend operator fun invoke() = suspendCoroutine {
        MaterialAlertDialogBuilder(context).apply {
            setTitle(context.getString(R.string.download_notification))
            setMessage(context.getString(R.string.download_dialog_message))
            setPositiveButton(context.getString(R.string.yes)) { a, _ ->
                it.resume(true)
                a.cancel()
            }
            setNegativeButton(context.getString(R.string.no)) { a, _ ->
                a.cancel()
            }
            show()
        }
        it.resume(false)
    }
}