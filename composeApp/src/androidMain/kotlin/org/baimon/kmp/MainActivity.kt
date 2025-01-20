package org.baimon.kmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import org.baimon.kmp.data.database.getTaskDatabaseBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val database = remember {
                getTaskDatabaseBuilder(this)
            }
            App(database)
        }
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
//    App()
//}