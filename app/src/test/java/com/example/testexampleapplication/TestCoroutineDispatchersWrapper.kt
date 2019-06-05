package com.example.testexampleapplication

import kotlinx.coroutines.Dispatchers

class TestCoroutineDispatchersWrapper : CoroutineDispatchersWrapper {

    override val main = Dispatchers.Unconfined

    override val default = Dispatchers.Unconfined

    override val io = Dispatchers.Unconfined
}