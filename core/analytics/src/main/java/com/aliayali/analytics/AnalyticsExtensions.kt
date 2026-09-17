package com.aliayali.analytics

fun AnalyticsHelper.logScreenView(screenName: String) {
    logEvent(
        AnalyticsEvent(
            type = AnalyticsEvent.Types.SCREEN_VIEW,
            extras = listOf(
                AnalyticsEvent.Param(
                    key = AnalyticsEvent.ParamKeys.SCREEN_NAME,
                    value = screenName,
                ),
            ),
        ),
    )
}