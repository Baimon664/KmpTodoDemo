package org.baimon.kmp

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import org.baimon.kmp.data.database.getTaskDatabaseBuilder

fun MainViewController() = ComposeUIViewController {
    val database = remember {
        getTaskDatabaseBuilder()
    }
    App(database)
}