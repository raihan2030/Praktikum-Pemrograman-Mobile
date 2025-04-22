package com.example.tipcalculatorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculatorcompose.ui.theme.TipCalculatorComposeTheme
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorComposeTheme {
                Surface (
                    Modifier.fillMaxSize()
                ) {
                    TipTimeLayout()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipTimeLayout() {
    val options = listOf(15, 18, 20)
    var amountInput by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableIntStateOf(options[0]) }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = selectedOption.toDouble()
    var roundUp by remember { mutableStateOf(false) }
    val totalAmount = calculateTip(amount, tipPercent, roundUp)

    Column (
        modifier = Modifier
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 15.dp)
                .align(alignment = Alignment.Start)
        )

        EditNumberField(
            label = R.string.bill_amount,
            leadingIcon = R.drawable.money,
            value = amountInput,
            onValueChanged = { amountInput = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .padding(bottom = 15.dp)
                .fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier.padding(bottom = 15.dp)
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = "$selectedOption%",
                onValueChange = { },
                label = { Text(stringResource(R.string.tip)) },
                leadingIcon = { Icon(painter = painterResource(R.drawable.percent), contentDescription = null, Modifier.size(20.dp)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(text = "$selectionOption%") },
                        onClick = {
                            selectedOption = selectionOption
                            expanded = false
                        }
                    )
                }
            }
        }

        RoundTheTipRow(
            roundUp = roundUp,
            onRoundUpChanged = { roundUp = it },
            modifier = Modifier.padding(bottom = 30.dp)
        )

        Text(
            text = stringResource(R.string.tip_amount, totalAmount),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EditNumberField(
    value: String,
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    onValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChanged,
        label = {Text(stringResource(label))},
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null, Modifier.size(20.dp)) },

        keyboardOptions = keyboardOptions
    )
}

@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.round_up_tip),
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged
        )
    }
}

private fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean): String {
    val tip = (tipPercent / 100) * amount
    var totalAmount = amount + tip
    if(roundUp){
        totalAmount = kotlin.math.ceil(totalAmount)
    }

    return NumberFormat.getCurrencyInstance(Locale.US).format(totalAmount)
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorPreview() {
    TipCalculatorComposeTheme {
        TipTimeLayout()
    }
}