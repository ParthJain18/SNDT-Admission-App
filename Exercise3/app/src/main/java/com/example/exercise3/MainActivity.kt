package com.example.exercise3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exercise3.ui.theme.Exercise3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Exercise3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main() {
    Column {
        Row(modifier = Modifier.weight(1f, fill = true)) {
            Column(modifier = Modifier.weight(1f, fill = true).padding(all = 16.dp).fillMaxHeight().background(
                Color.Blue) ,verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = "Text composable",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Displays text and follows Material Design guidelines."
                )
            }
            Column(modifier = Modifier.weight(1f, fill = true).padding(all = 16.dp).fillMaxHeight(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Image composable",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Creates a composable that lays out and draws a given Painter class object.\n"
                )
            }
        }
        Row(modifier = Modifier.weight(1f, fill = true)) {
            Column(modifier = Modifier.weight(1f, fill = true).padding(all = 16.dp).fillMaxHeight()) {
                Text(
                    text = "Text composable",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Displays text and follows Material Design guidelines."
                )
            }
            Column(modifier = Modifier.weight(1f, fill = true).padding(all = 16.dp).fillMaxHeight()) {
                Text(
                    text = "Image composable",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Creates a composable that lays out and draws a given Painter class object.\n"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Exercise3Theme {
        Main()
    }
}