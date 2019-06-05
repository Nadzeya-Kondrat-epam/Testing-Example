package com.example.testexampleapplication

import androidx.lifecycle.Observer
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class DataViewModelTest : Spek({

    emulateInstantTaskExecutorRule()

    val repositoryMock: RepositoryImulator = mockk()

    val testViewModel = DataViewModel(
        TestCoroutineDispatchersWrapper(),
        repositoryMock
    )

    val loadingObserver: Observer<Boolean> = mockk(relaxed = true)
    val dataObserver: Observer<LoadedData> = mockk(relaxed = true)
    val errorObserver: Observer<String> = mockk(relaxed = true)

    val response = LoadedData(5)
    val errorMessage = "My test error case"

    given("data loading: success scenario") {
        beforeEachTest {
            clearMocks(repositoryMock)
            testViewModel.loading.observeForever(loadingObserver)
            testViewModel.loadedData.observeForever(dataObserver)
            testViewModel.erroroData.observeForever(errorObserver)
        }

        afterEachTest {
            testViewModel.loading.removeObserver(loadingObserver)
            testViewModel.loadedData.removeObserver(dataObserver)
            testViewModel.erroroData.removeObserver(errorObserver)
        }

        describe("data loading: success scenario") {
            beforeEachTest {
                coEvery { repositoryMock.loadData() } returns response
                testViewModel.loadData()
            }

            it("should stop loading progress") {
                verify { loadingObserver.onChanged(false) }
            }

            it("loaded data should be posted") {
                verify { dataObserver.onChanged(response) }
            }
        }

        describe("data loading: error scenario") {
            beforeEachTest {
                coEvery { repositoryMock.loadData() } throws Exception(errorMessage)
                testViewModel.loadData()
            }

            it("should stop loading progress") {
                verify { loadingObserver.onChanged(false) }
            }

            it("error response should be posted") {
                verify { errorObserver.onChanged(errorMessage) }
            }
        }
    }
})