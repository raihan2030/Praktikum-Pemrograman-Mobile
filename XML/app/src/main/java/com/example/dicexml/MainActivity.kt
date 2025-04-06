package com.example.dicexml

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val mainLayout: ConstraintLayout = findViewById(R.id.main)
        val dice1: ImageView = findViewById(R.id.dice1)
        val dice2: ImageView = findViewById(R.id.dice2)
        val rollBtn: Button = findViewById(R.id.rollBtn)

        rollBtn.setOnClickListener {
            val result1 = (1..6).random()
            val result2 = (1..6).random()

            val imageResource1 = getDiceImageResource(result1)
            val imageResource2 = getDiceImageResource(result2)

            dice1.setImageResource(imageResource1)
            dice2.setImageResource(imageResource2)

            val snackbarText = getSnackbarText(result1, result2)
            Snackbar.make(mainLayout, snackbarText, Snackbar.LENGTH_SHORT).show()
        }
    }
}

fun getDiceImageResource (result: Int): Int {
    return when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_0
    }
}

fun getSnackbarText (result1: Int, result2: Int): String {
    return if(result1 == result2) "Selamat, Anda mendapatkan angka double!"
    else "Anda belum beruntung!"
}