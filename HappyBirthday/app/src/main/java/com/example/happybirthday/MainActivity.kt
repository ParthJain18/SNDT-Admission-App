package com.example.happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happybirthday.ui.theme.HappyBirthdayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappyBirthdayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    BirthdayGreeting(greeting = "Happy Birthday", from = "Parth")
                    BirthdayGreetingWithImage(greeting = getString(R.string.greeting), from = getString(
                                            R.string.name))
                }
            }
        }
    }
}

@Composable
fun BirthdayGreeting(modifier: Modifier=Modifier, greeting : String, from : String) {
    Column (modifier = Modifier.fillMaxSize() ,verticalArrangement = Arrangement.Top , horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = greeting,
            fontSize = 50.sp,
            modifier = Modifier.padding(all = 10.dp)
        )
        Text(
            text = " - from $from",
            fontSize = 20.sp,
            modifier = Modifier.align(alignment = Alignment.End).padding(all = 5.dp)
        )
    }
}


@Composable
fun BirthdayGreetingWithImage(modifier: Modifier = Modifier, greeting : String, from : String) {
    val image = painterResource(R.drawable.androidparty)
Box {
    Image(
        painter = image,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
    BirthdayGreeting(greeting = "Happy Birthday", from = "Parth")
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HappyBirthdayTheme {
//        BirthdayGreeting(greeting = "Happy Birthday", from = "Parth")
        BirthdayGreetingWithImage(greeting = "Happy Birthday", from = "Parth")
    }
}