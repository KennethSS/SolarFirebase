package com.jeongyookgak.jyg_order8.remote.ext

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel

@ExperimentalCoroutinesApi
fun <E> SendChannel<E>.safeOffer(value: E): Boolean {
    if (isClosedForSend) return false
    return try {
        offer(value)
    } catch (e: CancellationException) {
        false
    }
}