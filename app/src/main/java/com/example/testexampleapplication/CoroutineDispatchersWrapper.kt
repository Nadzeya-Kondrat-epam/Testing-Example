package com.example.testexampleapplication

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchersWrapper {

    val main: CoroutineDispatcher

    val default: CoroutineDispatcher

    val io: CoroutineDispatcher
}