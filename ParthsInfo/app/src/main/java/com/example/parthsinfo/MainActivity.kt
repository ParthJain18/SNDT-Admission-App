package com.example.parthsinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parthsinfo.ui.theme.ParthsInfoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParthsInfoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    info()
                }
            }
        }
    }
}

@Composable
fun info(modifier : Modifier = Modifier){
    logoName(name = "Parth", descrip = "Newbie Developer")
}

@Composable
fun logoName(modifier: Modifier = Modifier,name : String,  descrip : String){
    val logo = painterResource(id = R.drawable.android_logo)
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
        Image(painter = logo, contentDescription = null, contentScale = ContentScale.Crop,
        modifier = Modifier.height(500.dp).width(400.dp))

        Text(
            text = "Android",
            fontSize = 20.sp,
            modifier = Modifier.weight(0.3f, fill = true)

        )
        Text(
            text = name,
            fontSize = 50.sp,
            modifier = Modifier.weight(0.1f, fill = true)

        )
        Text(
            text = descrip,
            fontSize = 20.sp
        )

    }
}
@Composable
fun data(){

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ParthsInfoTheme {
        info()
    }
}