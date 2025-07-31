package dev.stukalo.presentation.core.ui.util.smartstatusbar

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.core.graphics.blue
import androidx.core.graphics.createBitmap
import androidx.core.graphics.get
import androidx.core.graphics.green
import androidx.core.graphics.red
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

/**
 * @param darkColorBound takes value from 0 to 255. It specifies how dark color should be to be determined as dark.
 *
 * 0 - only black color will be considered as dark
 *
 * 255 - all colors will be considered as dark
 */
@OptIn(FlowPreview::class)
@Composable
fun InstallSmartStatusBar(
    ignoreVertical: Int = 4,
    ignoreHorizontal: Int = 4,
    darkColorBound: Int = 128,
    refreshPolicy: RefreshPolicy,
) {
    require(darkColorBound in 0..255) { "Value must be between 0 and 255" }

    val localDensity = LocalDensity.current
    val context = LocalActivity.current as ComponentActivity

    val darkIcons = remember {
        mutableStateOf<Boolean?>(null)
    }

    LaunchedEffect(darkIcons.value) {
        if (darkIcons.value == null) {
            return@LaunchedEffect
        }

        if (darkIcons.value == true) {
            context.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                    scrim = Transparent.toArgb(),
                    darkScrim =  Transparent.toArgb()
                ),
                navigationBarStyle = SystemBarStyle.light(
                    Transparent.toArgb(),
                    Transparent.toArgb(),
                )
            )
        } else {
            context.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(
                    scrim = Transparent.toArgb(),
                ),
                navigationBarStyle = SystemBarStyle.light(
                    Transparent.toArgb(),
                    Transparent.toArgb(),
                )
            )
        }
    }

    val statusBarsHeight = WindowInsets.systemBars.getTop(localDensity)
    val fixedStatusBarHeight = remember {
        mutableIntStateOf(statusBarsHeight)
    }

    LaunchedEffect(statusBarsHeight) {
        fixedStatusBarHeight.intValue = statusBarsHeight
    }


    LaunchedEffect(Unit) {
        when(refreshPolicy) {
            is RefreshPolicy.OneTimeCheck -> {

                delay(refreshPolicy.waitBeforeCheck)

                getScreenshotAndProcess(
                    context = context,
                    density = localDensity.density,
                    statusBarHeight = fixedStatusBarHeight.intValue,
                    ignoreVerticalDp = ignoreVertical,
                    ignoreHorizontalDp = ignoreHorizontal,
                    darkColorBound = darkColorBound,
                    updateStatusBarIconColor = {
                        darkIcons.value = it
                    }
                )
            }

            is RefreshPolicy.RefreshOnInteraction -> {
                interactionFlow.debounce(refreshPolicy.debounce).collectLatest {

                    var rechecks = 0
                    val maxRechecks = refreshPolicy.recheck

                    do {
                        getScreenshotAndProcess(
                            context = context,
                            density = localDensity.density,
                            statusBarHeight = fixedStatusBarHeight.intValue,
                            ignoreVerticalDp = ignoreVertical,
                            ignoreHorizontalDp = ignoreHorizontal,
                            darkColorBound = darkColorBound,
                            updateStatusBarIconColor = {
                                darkIcons.value = it
                            }
                        )

                        delay(refreshPolicy.waitAfterCheck)

                    } while (rechecks++ < maxRechecks)
                }
            }

            is RefreshPolicy.RefreshContinuously -> {
                while (true) {
                    getScreenshotAndProcess(
                        context = context,
                        density = localDensity.density,
                        statusBarHeight = fixedStatusBarHeight.intValue,
                        ignoreVerticalDp = ignoreVertical,
                        ignoreHorizontalDp = ignoreHorizontal,
                        darkColorBound = darkColorBound,
                        updateStatusBarIconColor = {
                            darkIcons.value = it
                        }
                    )
                    delay(refreshPolicy.waitAfterCheck)
                }
            }
        }
    }
}

private suspend fun getScreenshotAndProcess(
    context: Context,
    density: Float,
    statusBarHeight: Int,
    ignoreVerticalDp: Int,
    ignoreHorizontalDp: Int,
    darkColorBound: Int,
    updateStatusBarIconColor: (Boolean) -> Unit
) {

    var attempts = 0
    val maxAttempts = 10
    var success = false

    while (!success && attempts < maxAttempts) {
        if (attempts != 0) delay(50)
        attempts++

        (context as? Activity)?.window?.decorView?.rootView?.let {
            getScreenshotFromView(
                v = it,
                density = density,
                statusBarHeight = statusBarHeight,
                ignoreVerticalDp = ignoreVerticalDp,
                ignoreHorizontalDp = ignoreHorizontalDp,
            )?.let { statusBarBgBitmap ->
                success = true
                updateStatusBarIconColor(
                    processBitmap(
                        bitmap = statusBarBgBitmap,
                        darkColorBound= darkColorBound,
                    )
                )
            }
        }
    }
}

private fun getScreenshotFromView(
    v: View,
    density: Float,
    statusBarHeight: Int,
    ignoreVerticalDp: Int,
    ignoreHorizontalDp: Int,
): Bitmap? {
    var screenshot: Bitmap?

    val ignoreVerticalPx = (ignoreVerticalDp * density)
    val ignoreHorizontalPx = (ignoreHorizontalDp * density)

    try {
        screenshot = createBitmap(
            v.measuredWidth - (ignoreVerticalPx * 2).toInt(),
            statusBarHeight - (ignoreHorizontalPx * 2).toInt()
        )
        val canvas = Canvas(screenshot)
        canvas.translate(-ignoreVerticalPx, -ignoreHorizontalPx)
        v.draw(canvas)
    } catch (e: Exception) {
        screenshot = null
        Log.e("MainActivity", "Failed to capture screenshot because: " + e.message)
    }
    return screenshot
}

/**
 * Determines whether light or dark tones dominate in the image.
 * @return true, if bitmap mostly light, otherwise false
 */
private suspend fun processBitmap(
    bitmap: Bitmap,
    darkColorBound: Int,
) : Boolean {

    val darkColorsCount = AtomicInteger()

    coroutineScope {
        for (y in 0 until bitmap.height) {
            launch(Dispatchers.Default) {
                for (x in 0 until bitmap.width) {
                    bitmap[x, y].apply {
                        if (isDarkColor(darkColorBound)) darkColorsCount.incrementAndGet()
                        else darkColorsCount.decrementAndGet()
                    }
                }
            }
        }
    }

    return darkColorsCount.get() < 0
}

fun Int.isDarkColor(darkColorBound: Int): Boolean =
    (0.299 * red + 0.587 * green + 0.114 * blue) <= darkColorBound