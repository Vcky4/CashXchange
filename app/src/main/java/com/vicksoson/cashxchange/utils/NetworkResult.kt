package com.azabox.b2c.utils

class NetworkResult<T, E> {
    private var onSuccess: ((T) -> Unit)? = null
    private var onFailure: ((E) -> Unit)? = null
    private var onComplete: ((Int) -> Unit)? = null


    fun addOnSuccessListener(listener: (T) -> Unit): NetworkResult<T, E> {
        onSuccess = listener
        return this
    }


    fun addOnFailureListener(listener: (E) -> Unit): NetworkResult<T, E> {
        onFailure = listener
        return this
    }

    fun addOnCompleteListener(listener: (Int) -> Unit): NetworkResult<T, E> {
        onComplete = listener
        return this
    }

    fun onSuccess(result: T) {
        onSuccess?.let {
            it(result)
        }
    }

    fun onFailure(error: E) {
        onFailure?.let {
            it(error)
        }
    }

    fun onComplete(status: Int) {
        onComplete?.let {
            it(status)
        }
    }
}