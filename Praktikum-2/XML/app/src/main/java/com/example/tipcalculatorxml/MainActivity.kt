package com.example.tipcalculatorxml

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.tipcalculatorxml.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.billAmount.setStartIcon(
            context = this,
            drawableResId = R.drawable.money
        )

        binding.dropdownLayout.setStartIcon(
            context = this,
            drawableResId = R.drawable.percent
        )

        val items = listOf("Ayam", "Sapi", "Ikan", "Udang")
        val adapter = ArrayAdapter(this, R.layout.list_items, items)
        binding.dropdownField.setText(items[0])
        binding.dropdownField.setAdapter(adapter)
    }
}

fun TextInputLayout.setStartIcon(context: Context, drawableResId: Int) {
    val scale = context.resources.displayMetrics.density
    val sizeInPx = (20 * scale + 0.5f).toInt()

    val bitmap = BitmapFactory.decodeResource(context.resources, drawableResId)
    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, sizeInPx, sizeInPx, true)
    val resizedDrawable: Drawable = BitmapDrawable(context.resources, resizedBitmap)

    this.startIconDrawable = resizedDrawable
}