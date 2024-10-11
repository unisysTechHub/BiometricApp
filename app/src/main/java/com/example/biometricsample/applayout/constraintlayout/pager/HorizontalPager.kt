package com.example.biometricsample.applayout.constraintlayout.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UTHorizontalPager(){

    val pagerState = rememberPagerState {
        10
    }
    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(state = pagerState,      contentPadding = PaddingValues(start = 64.dp),
        ) {
            UTImageASync(url = "")
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color = color)
                        .size(16.dp)
                )
            }
        }

    }

}

@Composable
fun UTImageASync(url: String) {
    Box(modifier = Modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://picsum.photos/200")
                .crossfade(true)
                .build(),
            contentDescription = "Translated description of what the image contains",
            modifier = Modifier.clip(CircleShape)

        )
    }
}


@Preview
@Composable
fun HorizontalPager_Preview() {
    UTHorizontalPager()

}