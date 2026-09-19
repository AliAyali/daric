package com.aliayali.daric.security.root

import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class AndroidRootDetector @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : RootDetector {

    override fun isRooted(): Boolean {
        return hasSuBinary() ||
                hasMagisk() ||
                hasTestKeys()
    }

    private fun hasSuBinary(): Boolean {
        val paths = listOf(
            "/system/bin/su",
            "/system/xbin/su",
            "/sbin/su",
            "/vendor/bin/su",
            "/su/bin/su",
        )

        return paths.any { path ->
            File(path).exists()
        }
    }

    private fun hasMagisk(): Boolean {
        val paths = listOf(
            "/sbin/.magisk",
            "/data/adb/magisk",
            "/data/adb/modules",
        )

        return paths.any { path ->
            File(path).exists()
        }
    }

    private fun hasTestKeys(): Boolean {
        return Build.TAGS?.contains("test-keys") == true
    }
}