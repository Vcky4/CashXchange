package com.vicksoson.cashxchange.utils


object Endpoints {

    private const val API_KEY = "6ace37179a5c89ae2a70c601a18f136d50437216"
    private const val BASE_URL = "https://api.getgeoapi.com/v2/"

    const val GET_CURRENCIES = "currency/list?api_key=${API_KEY}"

    val CONVERT = { from: String, to: String, amount: String ->
        "${BASE_URL}currency/convert?api_key=${API_KEY}&from=${from}&to=${to}&amount=${amount}"
    }


}