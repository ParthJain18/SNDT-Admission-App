package com.example.jetpackcomposeexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeexercise.ui.theme.JetpackComposeExerciseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeExerciseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main(){
    val image = painterResource(R.drawable.bg_compose_background)
    Column{
        Image(
            painter = image,
            contentDescription = null,
            Modifier.fillMaxWidth()
            )
        Text(
            text = stringResource(R.string.string1),
            fontSize = 24.sp,
            modifier = Modifier.padding(all = 16.dp)

        )
        Text(
            text = stringResource(R.string.string2),
            modifier = Modifier.padding(all = 16.dp),
            textAlign = TextAlign.Justify
        )
        Text(
            text = stringResource(R.string.string3),
            modifier = Modifier.padding(all = 16.dp),
            textAlign = TextAlign.Justify


        )

    }
}

@Preview(showBackground = true)
@Composable

fun DefaultPreview() {
    JetpackComposeExerciseTheme {
        Main()
    }
}