package com.roberto.miodiariofit
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.SurfaceHolder
import androidx.wear.watchface.CanvasType
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.WatchFace
import androidx.wear.watchface.WatchFaceService
import androidx.wear.watchface.WatchFaceType
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository
import java.time.ZonedDateTime

class MioDiarioWatchFaceService : WatchFaceService() {
    override suspend fun createWatchFace(
        surfaceHolder: SurfaceHolder, watchState: WatchState,
        complicationSlotsManager: ComplicationSlotsManager,
        currentUserStyleRepository: CurrentUserStyleRepository
    ): WatchFace {
        val renderer = object : Renderer.CanvasRenderer2<MioDiarioWatchFaceService>(
            surfaceHolder, currentUserStyleRepository, watchState, CanvasType.HARDWARE, 16
        ) {
            private val paint = Paint().apply { color = Color.WHITE; textSize = 42f; isAntiAlias = true }
            private val small = Paint().apply { color = Color.GREEN; textSize = 28f; isAntiAlias = true }
            override fun render(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime) {
                canvas.drawColor(Color.BLACK)
                canvas.drawText("MioDiario v3.57.25", 20f, 90f, small)
                canvas.drawText(zonedDateTime.toLocalTime().toString().substring(0,8), 20f, 150f, paint)
            }
            override fun renderHighlightLayer(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime) {}
        }
        return WatchFace(WatchFaceType.DIGITAL, renderer)
    }
}
