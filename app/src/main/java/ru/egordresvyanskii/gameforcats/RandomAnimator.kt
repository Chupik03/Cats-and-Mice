package ru.egordresvyanskii.gameforcats

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Path
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import kotlin.random.Random

class RandomAnimator(context: Context, image: ImageView, duration: Long) {
    private val context = context
    private val imageMouse = image
    private val durationMouse = duration
    private var animator = ObjectAnimator()


    public fun animateToRandomPosition(startX: Float = imageMouse.x, startY: Float = imageMouse.y) {

        val screenWidth = context.resources.displayMetrics.widthPixels.toFloat()
        val screenHeight = context.resources.displayMetrics.heightPixels.toFloat()
        // Генерируем случайные координаты
        val randomX = Random.nextFloat() * (screenWidth - imageMouse.width)
        val randomY = Random.nextFloat() * (screenHeight - imageMouse.height)

        // Создаем путь для анимации
        val path = Path().apply {
            moveTo(startX, startY)
            lineTo(randomX, randomY)
        }

        // Создаем аниматор по пути
        animator = ObjectAnimator.ofFloat(imageMouse, "x", "y", path).apply {
            duration = durationMouse
            interpolator = LinearInterpolator()

            // В конце каждой анимации запускаем новую с новыми координатами
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    imageMouse.clearAnimation()
                    animateToRandomPosition()
                }
            })
        }
        animator.start()
    }

    public fun endAnimate(){
        animator.removeAllListeners()
    }

}