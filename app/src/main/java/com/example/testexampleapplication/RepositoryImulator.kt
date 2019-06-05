package com.example.testexampleapplication

interface RepositoryImulator {
    fun isConnected(): Boolean
    suspend fun loadData(): LoadedData
}