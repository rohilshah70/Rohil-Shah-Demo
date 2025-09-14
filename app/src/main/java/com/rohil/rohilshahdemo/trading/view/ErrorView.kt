package com.rohil.rohilshahdemo.trading.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rohil.rohilshahdemo.R

@Composable
fun ErrorView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){

        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ){
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
            ) {
                Image(
                    modifier = Modifier,
                    painter = painterResource(R.drawable.ic_error_no_network),
                    contentDescription = null
                )
            }

            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.error_description)
            )
        }
    }
}


@Preview
@Composable
private fun PreviewErrorView(){
    ErrorView()
}