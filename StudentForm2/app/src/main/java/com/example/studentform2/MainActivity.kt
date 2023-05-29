package com.example.studentform2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentform2.ui.theme.StudentForm2Theme
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentForm2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Form()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form() {

    val inputText = remember { mutableStateOf(TextFieldValue()) }
    val text1 = remember { mutableStateOf(TextFieldValue()) }


    Column (modifier = Modifier.padding(all = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Text(
            text = "Howdy!",
            Modifier.padding(all = 10.dp)
        )
        TextField(
            value = inputText.value,
            onValueChange = { inputText.value = it },
            Modifier.padding(all = 10.dp)
        )

        Button(
            onClick = {
                text1.value = inputText.value
                println("Hello")

                saveDataToFirebase(text1.value.text)
            }
        ) {
            Text(text = "Click")
        }

        Text(
            text = text1.value.text,
            Modifier.padding(all = 10.dp)
        )

    }
}

fun saveDataToFirebase(data : String){
    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.getReference("StudentForm2")

    val myObj = SaveDataToFirebase(data)

    databaseReference.setValue(myObj)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StudentForm2Theme {
        Form()
    }
}