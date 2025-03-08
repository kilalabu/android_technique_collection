package com.example.android_technique_collection

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * テスト環境では `Dispatchers.Main` が使用できないため、
 * テスト実行中は `Main` ディスパッチャを [testDispatcher] に置き換えるための JUnit [TestRule]
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule  constructor(
    // UnconfinedTestDispatcher を使用することで、テスト内のコルーチンを即時実行し、
    // delay などの時間制御をスキップできる
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {

    override fun starting(description: Description) = Dispatchers.setMain(testDispatcher)

    override fun finished(description: Description) = Dispatchers.resetMain()
}