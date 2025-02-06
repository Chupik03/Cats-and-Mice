package ru.egordresvyanskii.gameforcats

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import `in`.eyehunt.sqliteandroidexample.db.DbStatistica
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random

class GameWindow: Activity() {

    private var durationMouse: Long = 2500
    private var id_image = 0
    private var clickCounterOnMouse = 0
    private var clickCounterOnDisplay = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_window)

        val idImageMouse = arrayOf<ImageView>(
            findViewById(R.id.imageMouse1),
            findViewById(R.id.imageMouse2),
            findViewById(R.id.imageMouse3),
            findViewById(R.id.imageMouse4),
            findViewById(R.id.imageMouse5))

        var animate = arrayOf<RandomAnimator>()

        id_image = intent.getIntExtra("position", 0)
        durationMouse = intent.getLongExtra("duration", 2500)
        val mouseCount =  intent.getIntExtra("count", 0)

        fillImageMouse(mouseCount, idImageMouse)

        for(i in 0..mouseCount){
            animate += RandomAnimator(this, idImageMouse[i], durationMouse)
            animate[i].animateToRandomPosition()
        }
        for(i in 0..mouseCount){
            idImageMouse[i].setOnClickListener{
                clickListenerOnMouse(idImageMouse[i], animate[i])
            }
        }
        findViewById<ConstraintLayout>(R.id.gameWindowLayout).setOnClickListener{
            clickCounterOnDisplay += 1
        }

        findViewById<Button>(R.id.btBack).setOnClickListener{
            for(i in 0..mouseCount){
                animate[i].endAnimate()
            }

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val currentDate = sdf.format(Date())

            val stat = Statistica()
            stat.date = currentDate
            stat.CountClickOnMouse = clickCounterOnMouse
            stat.CountClick = clickCounterOnMouse + clickCounterOnDisplay
            if(stat.CountClick == 0){
                stat.PercentageClicks = .0
            }
            else{
                stat.PercentageClicks = (clickCounterOnMouse.toDouble()/(clickCounterOnMouse + clickCounterOnDisplay).toDouble())*100
            }


            val dbStatistica = DbStatistica(this)

            dbStatistica.addResultat(stat)

            dbStatistica.close()

            finish()
        }


    }
    private fun clickListenerOnMouse(image: ImageView, animate: RandomAnimator){
        clickCounterOnMouse += 1
        findViewById<TextView>(R.id.tvCounter).text = "$clickCounterOnMouse"
        val screenWidth = resources.displayMetrics.widthPixels.toFloat()
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()
        // Генерируем случайные координаты
        val randomX = Random.nextFloat() * (screenWidth - image.width)
        val randomY = Random.nextFloat() * (screenHeight - image.height)
        animate.endAnimate()
        animate.animateToRandomPosition(randomX, randomY)
    }

    private fun fillImageMouse(count: Int, idImageMouse: Array<ImageView>){
        for(i in 0..count){
            idImageMouse[i].setImageResource(id_image)
        }
    }
}