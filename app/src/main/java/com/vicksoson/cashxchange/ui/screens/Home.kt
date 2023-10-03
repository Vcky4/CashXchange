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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.vicksoson.cashxchange.ui.theme.Purple40
import com.vicksoson.cashxchange.ui.theme.Purple80

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

    val currencies = listOf("NGN", "USD", "CAD", "EUR")

    LaunchedEffect(key1 = fromAmount, key2 = toAmount) {
        if (currentFocus == "from") {
            toAmount = (fromAmount.ifEmpty { "0" }.toDouble() * 20).toString()
        } else {
            fromAmount = (toAmount.ifEmpty { "0" }.toDouble() / 20).toString()
        }
    }


    Column(
            modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Cash Xchange",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(modifier = Modifier
                .clickable {
                    trigger = "from"
                    isExpanded = !isExpanded
                }
                .border(1.dp, if (currentFocus == "from") Purple40 else Purple80, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .padding(start = 4.dp, end = 14.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
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
                    keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                    ),

                    )
            Text(text = from,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier

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
                .border(1.dp, if (currentFocus == "to") Purple40 else Purple80, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .padding(start = 4.dp, end = 14.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
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
                    keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                    )
            )
            Text(text = to,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier

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
                    Text(text = "Select $trigger Currency",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                    )

                    currencies.filter {
                        if (trigger == "from") {
                            it != to
                        } else {
                            it != from
                        }
                    }.forEach { currency ->
                        Text(text = currency,
                                fontSize = 16.sp,
                                modifier = Modifier
                                        .padding(bottom = 16.dp)
                                        .clickable {
                                            if (trigger == "from") {
                                                from = currency
                                            } else {
                                                to = currency
                                            }
                                            isExpanded = false
                                        }
                        )
                    }
                }
            }
        }

        Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 120.dp),

                ) {
            Text(text = "Continue", style = TextStyle(color = Color.Black))
        }

        Spacer(modifier = Modifier.height(16.dp))
        currencies.forEach { currency ->
            //show exchange rate for each currency compared to usd
            Text(text = "1$currency is 20NGN",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 16.dp))

        }

    }

}