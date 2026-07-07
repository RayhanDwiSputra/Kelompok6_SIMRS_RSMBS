package com.rsmbs.simrs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.rsmbs.simrs.ui.navigation.SimrsNavGraph
import com.rsmbs.simrs.ui.theme.SimrsMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimrsMobileTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SimrsNavGraph()
                }
            }
        }
    }
}
