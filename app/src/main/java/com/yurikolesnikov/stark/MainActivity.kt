package com.yurikolesnikov.stark

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yurikolesnikov.stark.ui.theme.StarkAppTheme
import java.io.File

// URL for loading.
const val URL = "https://www.google.com"
const val LOGO_NAME = "googlelogo_color_160x56dp.png" // Figured out experimentally.

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StarkAppTheme {
                MainContainer()
            }
        }
    }
}