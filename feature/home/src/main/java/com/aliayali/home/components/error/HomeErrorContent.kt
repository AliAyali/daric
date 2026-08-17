package com.aliayali.home.components.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aliayali.designsystem.component.DaricButton
import com.aliayali.home.R
import com.aliayali.model.error.AppError

@Composable
fun HomeErrorContent(
    error: AppError,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val titleRes = when (error) {
        AppError.NoInternet ->
            R.string.feature_home_error_no_internet_title

        AppError.Timeout ->
            R.string.feature_home_error_timeout_title

        is AppError.Server ->
            R.string.feature_home_error_server_title

        AppError.Unknown ->
            R.string.feature_home_error_unknown_title
    }

    val descriptionRes = when (error) {
        AppError.NoInternet ->
            R.string.feature_home_error_no_internet_description

        AppError.Timeout ->
            R.string.feature_home_error_timeout_description

        is AppError.Server ->
            R.string.feature_home_error_server_description

        AppError.Unknown ->
            R.string.feature_home_error_unknown_description
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(titleRes),
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(
            modifier = Modifier.height(8.dp),
        )

        Text(
            text = stringResource(descriptionRes),
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(
            modifier = Modifier.height(20.dp),
        )

        DaricButton(
            onClick = onRetry,
        ) {
            Text(
                text = stringResource(
                    R.string.feature_home_error_retry,
                ),
            )
        }
    }
}