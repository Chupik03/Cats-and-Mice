package ru.egordresvyanskii.gameforcats

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pictureMouses = arrayOf(
            R.drawable.mouse_54,
            R.drawable.mouse_66,
            R.drawable.mouse_78,
            R.drawable.mouse_90,
            R.drawable.mouse_102)
        val bt_start = findViewById<Button>(R.id.btStart)
        val bt_score = findViewById<Button>(R.id.btScore)
        var id_image = R.drawable.mouse_78
        val imageMouse = findViewById<ImageView>(R.id.imageMouse)

        var durationMouse = 3000L
        var mouseCount = 0

        val sb_mouseSize = findViewById<SeekBar>(R.id.sbSize)
        val sb_mouseDuration = findViewById<SeekBar>(R.id.sbDuration)
        val sb_mouseCount = findViewById<SeekBar>(R.id.sbCount)

        sb_mouseSize.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                // Handle when the progress changes
                override fun onProgressChanged(seek: SeekBar,
                                               progress: Int, fromUser: Boolean) {
                    id_image = pictureMouses[progress]
                    imageMouse.setImageResource(id_image)
                }
                // Handle when the user starts tracking touch
                override fun onStartTrackingTouch(seek: SeekBar) {}
                // Handle when the user stops tracking touch
                override fun onStopTrackingTouch(seek: SeekBar) {}
            })

        sb_mouseDuration.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                // Handle when the progress changes
                override fun onProgressChanged(seek: SeekBar,
                                               progress: Int, fromUser: Boolean) {
                    durationMouse = 5000L - 40*progress
                }
                // Handle when the user starts tracking touch
                override fun onStartTrackingTouch(seek: SeekBar) {}
                // Handle when the user stops tracking touch
                override fun onStopTrackingTouch(seek: SeekBar) {}
            })

        sb_mouseCount.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                // Handle when the progress changes
                override fun onProgressChanged(seek: SeekBar,
                                               progress: Int, fromUser: Boolean) {
                    mouseCount = progress
                }
                // Handle when the user starts tracking touch
                override fun onStartTrackingTouch(seek: SeekBar) {}
                // Handle when the user stops tracking touch
                override fun onStopTrackingTouch(seek: SeekBar) {}
            })


        bt_start.setOnClickListener{
            val intentGame = Intent(this, GameWindow::class.java)
            intentGame.putExtra("position", id_image)
            intentGame.putExtra("duration", durationMouse)
            intentGame.putExtra("count", mouseCount)
            startActivity(intentGame)
        }

        bt_score.setOnClickListener{
            val intentScore = Intent(this, MenuScore::class.java)
            startActivity(intentScore)
        }


    }

}