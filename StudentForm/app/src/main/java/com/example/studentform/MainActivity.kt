package com.example.studentform

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentform.ui.theme.StudentFormTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.selects.select


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentFormTheme {
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

fun saveDataToFirebase(data1: String, data2: String, data3: String, data4: String) {
    println("Clicked")

    val db = Firebase.firestore

    val student = hashMapOf(
        "fName" to data1,
        "lName" to data2,
        "City" to data3,
        "Department" to data4
    )
    db.collection("Students")
        .add(student)
        .addOnSuccessListener { documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }



}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form() {

    val fName = remember { mutableStateOf(TextFieldValue()) }
    val lName = remember { mutableStateOf(TextFieldValue()) }
    val foi = remember { mutableStateOf(TextFieldValue()) }

    var text1 = " "
    var text2 = " "
    var text3 = " "
    var text4 = " "


    val courses = listOf("Juhu Campus", "Churchgate Campus", "Pune Campus")
    val ListJuhu = listOf("Department of Extension & Communication","Department of Educational Technology","Department of Special Education", "Department of Human Development", "Department of Food Science & Nutrition", "Department of Family Resource Management", "Department of Textile Science and Apparel Design", "Department of Computer Science", "Department of Education Management", "C U Shah College of Pharmacy", "SHPT College of Science - Department of Analytical Chemistry")

    var selectedOption1 by remember { mutableStateOf("") }
    var selectedOption2 by remember { mutableStateOf("") }

    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    val icon = if (expanded1) {
        Icons.Filled.KeyboardArrowUp}else{
            Icons.Filled.KeyboardArrowDown
    }






    Column (modifier = Modifier.padding(all = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Text(
            text = "Student Details",
            Modifier.padding(all = 10.dp),
            fontSize = 30.sp
        )
        TextField(
            value = fName.value,
            onValueChange = { fName.value = it },
            Modifier.padding(all = 10.dp),
            label = {Text("First Name")}
            )
        TextField(
            value = lName.value,
            onValueChange = { lName.value = it },
            Modifier.padding(all = 10.dp),
            label = {Text("Last Name")}
        )

        Box(
            modifier = Modifier.padding(10.dp),
            contentAlignment = Alignment.Center,
            propagateMinConstraints = true
        ){
                ExposedDropdownMenuBox(
                    expanded = expanded1,
                    onExpandedChange = { expanded1 = it }
                ) {
                    TextField(
                         value = selectedOption1,
                         onValueChange = { selectedOption1 = it },
                         readOnly = true,
                         trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded1) },
                         colors = ExposedDropdownMenuDefaults.textFieldColors(),
                         modifier = Modifier.menuAnchor(),
                         placeholder = { Text("Choose your nearest Branch")}
                    )

                    ExposedDropdownMenu(
                        expanded = expanded1,
                        onDismissRequest = { expanded1 = false }) {
                        courses.forEach { course ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedOption1 = course
                                    expanded1 = false
                                },
                                text = { Text(text = course) }
                            )
                        }
                    }

            }
        }

        Box(
            modifier = Modifier.padding(10.dp),
            contentAlignment = Alignment.Center,
            propagateMinConstraints = true

        ){
            ExposedDropdownMenuBox(
                expanded = expanded2,
                onExpandedChange = { expanded2 = it }
            ) {
                TextField(
                    value = selectedOption2,
                    onValueChange = { selectedOption2 = it },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded2) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.menuAnchor(),
                    placeholder = { Text("Choose Department")},
                    enabled = selectedOption1.isNotEmpty()

                )

                ExposedDropdownMenu(
                    expanded = expanded2,
                    onDismissRequest = { expanded2 = false }) {
                    ListJuhu.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                selectedOption2 = item
                                expanded2 = false
                            },
                            text = { Text(text = item) }
                        )
                    }
                }

            }
        }




        Button(
            onClick = {
                text1 = fName.value.text
                text2 = lName.value.text
                text3 = selectedOption1
                text4 = selectedOption2



                saveDataToFirebase(text1, text2, text3, text4)
            }
        ) {
            Text(text = "Click")
        }


    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StudentFormTheme {
        Form()
    }
}
