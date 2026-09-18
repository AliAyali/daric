package com.aliayali.daric.security.root

sealed interface SecurityStatus {
    data object Secure : SecurityStatus
    data object RootDetected : SecurityStatus
}