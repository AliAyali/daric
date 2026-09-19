package com.aliayali.daric.security.root

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecurityManager @Inject constructor(
    private val rootDetector: RootDetector,
) {

    fun check(): SecurityStatus {
        return if (rootDetector.isRooted()) {
            SecurityStatus.RootDetected
        } else {
            SecurityStatus.Secure
        }
    }
}