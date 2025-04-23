package com.example.tipcalculatorxml

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.tipcalculatorxml.databinding.ActivityMainBinding
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listOf("15%", "18%", "20%")
        val adapter = ArrayAdapter(this, R.layout.list_items, items)
        binding.dropdownField.setAdapter(adapter)
        binding.dropdownField.setText(items[0], false)
        binding.tipAmount.text = getString(R.string.tip_amount, 0.0)

        fun updateTip(isRoundUp: Boolean) {
            val billAmount = binding.billAmountInput.text.toString().toDoubleOrNull() ?: 0.0
            val tipPercent = binding.dropdownField.text.toString().replace("%", "").toDoubleOrNull() ?: 0.0
            var tipAmount = billAmount * tipPercent / 100
            if(isRoundUp) { tipAmount = ceil(tipAmount) }
            binding.tipAmount.text = getString(R.string.tip_amount, tipAmount)
        }

        binding.billAmountInput.addTextChangedListener {
            updateTip(binding.switchRoundUp.isChecked)
        }
        binding.dropdownField.addTextChangedListener {
            updateTip(binding.switchRoundUp.isChecked)
        }
        binding.switchRoundUp.setOnCheckedChangeListener { _, isChecked ->
            updateTip(isChecked)
        }

        updateTip(false)
    }
}