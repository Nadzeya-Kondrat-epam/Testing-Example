package com.example.testexampleapplication

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private var btnTestRequest: Button? = null
    private var progress: ProgressBar? = null

    private var dataViewModel: DataViewModel? =  null

    private val coroutineDispatchersWrapper = CoroutineDispatchesWrapperImpl()
    private val repositoryImulator: RepositoryImulator = RepositoryImulatorImpl(coroutineDispatchersWrapper)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnTestRequest = findViewById(R.id.requestBtn)
        progress = findViewById(R.id.progress_loading)

        dataViewModel = DataViewModel.Factory(
            coroutineDispatchersWrapper,
            repositoryImulator
        ).create(DataViewModel::class.java)

        btnTestRequest?.setOnClickListener { dataViewModel?.loadData() }

        initObservers()
    }

    private fun initObservers() {
        dataViewModel?.loading?.observe(this, Observer { isLoading -> progress?.isVisible = isLoading })
        dataViewModel?.loadedData?.observe(
            this,
            Observer { data ->
                Toast.makeText(this, "loadedData: ${data.value}", Toast.LENGTH_LONG).show() })
        dataViewModel?.erroroData?.observe(this,
            Observer { Toast.makeText(this, "loadedData: ${it}", Toast.LENGTH_LONG).show() })
    }
}
