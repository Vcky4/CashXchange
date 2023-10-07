package com.vicksoson.cashxchange.utils

import android.util.Log
import com.azabox.b2c.utils.NetworkResult
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.fuel.serialization.responseObject
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import timber.log.Timber

object NetworkCalls {
    inline fun <reified T : Any> post(
        endpoint: String,
        body: List<Pair<String, Any?>>
    ): NetworkResult<T, FuelError> {
        val networkResult = NetworkResult<T, FuelError>()
        endpoint.httpPost().body(JsonBody.generate(body))
            .responseObject<T>(json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }) { request, response, result ->
                result.fold(
                    {
                        Timber.i(it.toString())
                        networkResult.onSuccess(it)
                    }, { e ->
                        Timber.e(e)
                        Timber.d("body: ${JsonBody.generate(body)}")
                        networkResult.onFailure(e)
                    }
                )
            }
        return networkResult
    }

    inline fun <reified T : Any> get(
        endpoint: String,
//        body: List<Pair<String, Any?>>
    ): NetworkResult<T, FuelError> {
        val networkResult = NetworkResult<T, FuelError>()
        endpoint.httpGet()
            .responseObject<T>(json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }) { request, response, result ->
                result.fold(
                    {
                        Log.d("network", it.toString())
                        networkResult.onSuccess(it)
                    }, { e ->
                        Log.e("network", e.toString())
//                        Timber.d("body: ${JsonBody.generate(body)}")
                        networkResult.onFailure(e)
                    }
                )
            }
        return networkResult
    }

    inline fun <reified T : Any> put(
        endpoint: String,
        body: List<Pair<String, Any>>
    ): NetworkResult<T, FuelError> {
        val networkResult = NetworkResult<T, FuelError>()
        endpoint.httpPut().body(JsonBody.generate(body))
            .responseObject<T>(json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }) { request, response, result ->
                result.fold(
                    {
                        Timber.i(it.toString())
                        networkResult.onSuccess(it)
                    }, { e ->
                        Timber.e(e)
                        networkResult.onFailure(e)
                    }
                )
            }
        return networkResult
    }


    fun getStringList(endpoint: String): NetworkResult<List<String>, FuelError> {
        val networkResult = NetworkResult<List<String>, FuelError>()
        endpoint.httpGet()
            .responseObject(loader = ListSerializer(String.serializer()), json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }) { request, response, result ->

                result.fold(
                    {
                        Timber.d("interests: $it")
                        networkResult.onSuccess(it)
                    },
                    {
                        networkResult.onFailure(it)
                    })
            }
        return networkResult
    }

    @OptIn(InternalSerializationApi::class)
    inline fun <reified T : Any> getObjectList(endpoint: String): NetworkResult<List<T>, FuelError> {
        val networkResult = NetworkResult<List<T>, FuelError>()
        endpoint.httpGet()
            .responseObject(loader = ListSerializer(T::class.serializer()), json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }) { request, response, result ->

                result.fold({
                    Timber.d("success: $it")
                    networkResult.onSuccess(it)
                },
                    {
                        Timber.d("error: $it from request: $request")
                        networkResult.onFailure(it)
                    })
            }
        return networkResult
    }

    @OptIn(InternalSerializationApi::class)
    inline fun <reified T : Any> getObject(endpoint: String): NetworkResult<T, FuelError> {
        val networkResult = NetworkResult<T, FuelError>()
        endpoint.httpGet()
            .responseObject(T::class.serializer(), json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }) { request, response, result ->
                result.fold({
                    Timber.d("success: $it")
                    networkResult.onSuccess(it)
                },
                    {
                        Timber.d("error: $it from request: $request")
                        networkResult.onFailure(it)
                    })
            }
        return networkResult
    }
}