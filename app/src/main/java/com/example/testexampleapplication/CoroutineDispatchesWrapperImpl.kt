package com.example.testexampleapplication

import kotlinx.coroutines.Dispatchers

class CoroutineDispatchesWrapperImpl(): CoroutineDispatchersWrapper {

    override val main = Dispatchers.Main

    override val default = Dispatchers.Default

    override val io = Dispatchers.IO
}