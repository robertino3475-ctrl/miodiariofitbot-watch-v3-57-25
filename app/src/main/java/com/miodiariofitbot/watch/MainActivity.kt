package com.miodiariofitbot.watch
import android.os.Bundle; import androidx.appcompat.app.AppCompatActivity; import android.widget.TextView; import android.view.Gravity
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tv = TextView(this); tv.text = "MioDiarioFitBot\nv3.57.25 FIXED\nXiaomi Watch 2 Pro"; tv.textSize = 20f; tv.gravity = Gravity.CENTER; setContentView(tv)
    }
}
