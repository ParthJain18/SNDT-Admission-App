package com.example.exercise2

import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exercise2.ui.theme.Exercise2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Exercise2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Greeting("An")
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main(){
    val image = painterResource(id = R.drawable.ic_task_completed)
    Column(verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = image, contentDescription = null)
        Text(
            text = "All Tasks Completed",
            modifier = Modifier.padding(top = 24.dp, bottom= 8.dp),
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Nice Work!",
            fontSize = 16.sp
        )
    }

}

@Preview(showBackground = true, name = "Hello")
@Composable
fun DefaultPreview() {
    Exercise2Theme {
//        Greeting("And")
        Main()

    }
}