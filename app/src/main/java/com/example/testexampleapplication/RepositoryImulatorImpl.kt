package com.example.testexampleapplication

import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random

class RepositoryImulatorImpl(val coroutineDispatcher: CoroutineDispatchersWrapper): RepositoryImulator {

    override fun isConnected(): Boolean {
        return true
    }

    override suspend fun loadData(): LoadedData {
        return withContext(coroutineDispatcher.default) {
            delay(1000) // simulates some work
            val randomNumber = Random.nextInt(0, 100)

            if (Random.nextInt(0, 100) % 3 != 0)  {
                return@withContext LoadedData(randomNumber)
            } else {
                throw Exception("Failed to load data")
            }
        }
    }
}