
package com.roberto.miodiariofit
import android.graphics.*
import android.os.*
import android.support.wearable.watchface.CanvasWatchFaceService
import android.view.SurfaceHolder
import org.json.JSONObject
import java.net.URL
import kotlin.concurrent.thread

class MioDiarioWatchFaceService : CanvasWatchFaceService() {
    override fun onCreateEngine(): Engine = Engine()
    inner class Engine : CanvasWatchFaceService.Engine() {
        private var glico = "--"
        private var kcalRes = "--"
        private val handler = Handler(Looper.getMainLooper())
        private val updateRunnable = object : Runnable {
            override fun run() {
                fetchData()
                handler.postDelayed(this, 60000)
            }
        }
        override fun onCreate(holder: SurfaceHolder) {
            super.onCreate(holder)
            setWatchFaceStyle(android.support.wearable.watchface.WatchFaceStyle.Builder(this@MioDiarioWatchFaceService).setAcceptsTapEvents(true).build())
            handler.post(updateRunnable)
        }
        override fun onDestroy() { handler.removeCallbacks(updateRunnable); super.onDestroy() }
        private fun fetchData() {
            thread {
                try {
                    val urlStr = "https://script.google.com/macros/s/AKfycbyRr2vU2Jv5UQp8J7k9x_example_v35725/exec?action=watch"
                    val json = URL(urlStr).readText()
                    val obj = JSONObject(json)
                    glico = obj.optString("glicemia","--")
                    kcalRes = obj.optString("kcalResidue","--")
                    invalidate()
                } catch (e: Exception) {}
            }
        }
        override fun onDraw(canvas: Canvas, bounds: Rect) {
            canvas.drawColor(Color.BLACK)
            val paint = Paint().apply { color = Color.WHITE; textSize = 28f; isAntiAlias = true; textAlign = Paint.Align.CENTER }
            val paint2 = Paint().apply { color = Color.GREEN; textSize = 42f; isAntiAlias = true; textAlign = Paint.Align.CENTER; isFakeBoldText = true }
            val paintKcal = Paint().apply { color = Color.CYAN; textSize = 22f; isAntiAlias = true; textAlign = Paint.Align.CENTER }
            canvas.drawText("MioDiarioFitBot", bounds.centerX().toFloat(), bounds.centerY()-60, paint)
            canvas.drawText(glico, bounds.centerX().toFloat(), bounds.centerY()+10, paint2)
            canvas.drawText("Kcal: $kcalRes", bounds.centerX().toFloat(), bounds.centerY()+50, paintKcal)
            canvas.drawText("v3.57.25", bounds.centerX().toFloat(), bounds.centerY()+80, paint)
        }
        override fun onTapCommand(tapType: Int, x: Int, y: Int, eventTime: Long) {
            if (tapType == TAP_TYPE_TAP) { fetchData() }
        }
    }
}
