package com.blumock.common

import android.content.res.Resources
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

fun FragmentManager.toggle(destination: String, @IdRes containerId: Int) {
    val current = this.findFragmentById(containerId)
        ?: throw Resources.NotFoundException("host not contains fragments")
    val new = this.findFragmentByTag(destination)
        ?: throw Resources.NotFoundException("fragment not found for $destination")
    if (current != new) {
        this.commit {
            attach(new)
            detach(current)
        }
    }
}