package com.vicksoson.cashxchange.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.vicksoson.cashxchange.ui.theme.Purple40
import com.vicksoson.cashxchange.ui.theme.Purple80
import com.vicksoson.cashxchange.utils.Endpoints
import com.vicksoson.cashxchange.utils.NetworkCalls
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {
    var fromAmount by remember { mutableStateOf("") }
    var toAmount by remember { mutableStateOf("") }
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var trigger by remember {
        mutableStateOf("")
    }

    var from by remember {
        mutableStateOf("NGN")
    }

    var to by remember {
        mutableStateOf("USD")
    }

    var currentFocus by remember {
        mutableStateOf("")
    }

    var processing by remember {
        mutableStateOf(false)
    }

    var rates by remember {
        mutableStateOf("")
    }

    val currencies = listOf(
        Currency("Nigerian Naira", "NGN"),
        Currency("United States Dollar", "USD"),
        Currency("Euro", "EUR"),
        Currency("British Pound ", "GBP"),
        Currency("Japanese Yen", "JPY"),
        Currency("Canadian Dollar", "CAD"),
        Currency("Australian Dollar", "AUD"),
        Currency("Swiss Franc", "CHF"),
        Currency("Chinese Yuan", "CNY"),
        Currency("Hong Kong dollar", "HKD"),
        Currency("New Zealand Dollar", "NZD"),
    )

//    LaunchedEffect(
//        key1 = arrayOf(fromAmount, from),
//        key2 = arrayOf(toAmount, to),
//        key3 = currentFocus
//    ) {
//        val referenceCurrency = currencies.find {
//            it.name == "NGN"
//        } ?: Currency("NGN", 1.0)
//        val fromRate = currencies.find { it.name == from }!!.rate
//        val toRate = currencies.find { it.name == to }!!.rate
//        if (currentFocus == "from") {
//            toAmount = (referenceCurrency.rate.div(fromRate).times(toRate))
//                .times(fromAmount.ifEmpty { "0" }.toDouble()).toString()
//        } else {
//            fromAmount = referenceCurrency.rate.div(toRate).times(fromRate)
//                .times(toAmount.ifEmpty { "0" }.toDouble()).toString()
//        }
//    }


    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Cash Xchange",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(modifier = Modifier
            .clickable {
                trigger = "from"
                isExpanded = !isExpanded
            }
            .border(
                1.dp,
                if (currentFocus == "from") Purple40 else Purple80,
                RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .padding(start = 4.dp, end = 14.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = fromAmount,
                onValueChange = {
                    fromAmount = it
                },
                placeholder = { Text(text = "Amount", color = Color.Gray) },
                modifier = Modifier
                    .onFocusEvent {
                        if (it.isFocused) {
                            currentFocus = "from"
                        }
                    }
                    .weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),

                )
            Text(
                text = from, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier

                    .padding(start = 16.dp, end = 8.dp)
            )
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier
            .clickable {
                trigger = "to"
                isExpanded = !isExpanded
            }
            .border(
                1.dp,
                if (currentFocus == "to") Purple40 else Purple80,
                RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .padding(start = 4.dp, end = 14.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = toAmount,
                onValueChange = {
                    toAmount = it
                },
                placeholder = { Text(text = "Amount", color = Color.Gray) },
                modifier = Modifier
                    .onFocusEvent {
                        if (it.isFocused) {
                            currentFocus = "to"
                        }
                    }
                    .weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                readOnly = true,
            )
            Text(
                text = to, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier

                    .padding(start = 16.dp, end = 8.dp)
            )
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
        }

        if (isExpanded) {
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = { isExpanded = false },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Purple40, RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Select $trigger Currency",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    currencies.filter {
                        if (trigger == "from") {
                            it.name != to
                        } else {
                            it.name != from
                        }
                    }.forEach { currency ->
                        Text(text = currency.name,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .clickable {
                                    if (trigger == "from") {
                                        from = currency.symbol
                                    } else {
                                        to = currency.symbol
                                    }
                                    isExpanded = false
                                })
                    }
                }
            }
        }

        Text(
            text = rates,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 60.dp),
        )
        Button(
            onClick = {
                processing = true
                refreshRates(
                    from = from, to = to, amount = fromAmount
                ).addOnSuccessListener {
                    processing = false
                    val result = it.rate
                    //find the currency that matches the symbol
                    when (to) {
                        "USD" -> {
                            toAmount = result.usd.amount.toString()
                            rates = "1${from} is ${result.usd.rate}${to}"
                        }

                        "EUR" -> {
                            toAmount = result.eur.amount.toString()
                            rates = "1${from} is ${result.eur.rate}${to}"
                        }

                        "GBP" -> {
                            toAmount = result.gbp.amount.toString()
                            rates = "1${from} is ${result.gbp.rate}${to}"
                        }

                        "JPY" -> {
                            toAmount = result.jpy.amount.toString()
                            rates = "1${from} is ${result.jpy.rate}${to}"
                        }

                        "CAD" -> {
                            toAmount = result.cad.amount.toString()
                            rates = "1${from} is ${result.cad.rate}${to}"
                        }

                        "AUD" -> {
                            toAmount = result.aud.amount.toString()
                            rates = "1${from} is ${result.aud.rate}${to}"
                        }

                        "CHF" -> {
                            toAmount = result.chf.amount.toString()
                            rates = "1${from} is ${result.chf.rate}${to}"
                        }

                        "CNY" -> {
                            toAmount = result.cny.amount.toString()
                            rates = "1${from} is ${result.cny.rate}${to}"
                        }

                        "HKD" -> {
                            toAmount = result.hkd.amount.toString()
                            rates = "1${from} is ${result.hkd.rate}${to}"
                        }

                        "NZD" -> {
                            toAmount = result.nzd.amount.toString()
                            rates = "1${from} is ${result.nzd.rate}${to}"
                        }

                        else -> {
                            toAmount = result.ngn.amount.toString()
                            rates = "1${from} is ${result.ngn.rate}${to}"
                        }
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 60.dp),
        ) {
            if (processing) {
                CircularProgressIndicator()
            } else {
                Text(text = "Convert", style = TextStyle(color = Color.White))
            }
        }

//        Spacer(modifier = Modifier.height(16.dp))
//        currencies.forEach { currency ->
//            //show exchange rate for each currency compared to usd
//            val referenceCurrency = currencies.find {
//                it.name == "NGN"
//            } ?: Currency("NGN", 1.0)
//            Text(
//                text = "1${currency.name} is ${currency.rate.times(referenceCurrency.rate)}NGN",
//                fontSize = 18.sp,
//                modifier = Modifier.padding(top = 16.dp)
//            )
//
//        }

    }
}

@Serializable
data class DataResponse(
    val status: String,
    @SerialName("updated_date") val date: String,
    val amount: String,
    @SerialName("base_currency_name") val baseCurrency: String,
    @SerialName("rates")
    val rate: Rate = Rate(),
)

@Serializable
data class Rate(
    @SerialName("NGN") val ngn: Currency = Currency(),
    @SerialName("USD") val usd: Currency = Currency(),
    @SerialName("EUR") val eur: Currency = Currency(),
    @SerialName("GBP") val gbp: Currency = Currency(),
    @SerialName("JPY") val jpy: Currency = Currency(),
    @SerialName("CAD") val cad: Currency = Currency(),
    @SerialName("AUD") val aud: Currency = Currency(),
    @SerialName("CHF") val chf: Currency = Currency(),
    @SerialName("CNY") val cny: Currency = Currency(),
    @SerialName("HKD") val hkd: Currency = Currency(),
    @SerialName("NZD") val nzd: Currency = Currency()
)

@Serializable
data class Currency(
    @SerialName("currency_name") val name: String = "",
    val symbol: String = "",
    val rate: String = "",
    @SerialName("rate_for_amount") val amount: Double = 0.0
)

fun refreshRates(from: String, to: String, amount: String) =
    NetworkCalls.get<DataResponse>(
        endpoint = Endpoints.CONVERT(from, to, amount)
    )
