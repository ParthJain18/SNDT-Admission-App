package com.example.studentform

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studentform.ui.theme.StudentFormTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


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
                    MyApp()
                }
            }
        }
    }
}



var campuses = mutableListOf(ChurchCampus, JuhuCampus, PuneCampus)




fun saveDataToFirebase(data1: String, data2: String, data3: String, data4: String) {
    println("Clicked")

    val db = Firebase.firestore

    val student = hashMapOf(
        "name" to data1,
        "contactNum" to data2,
        "email" to data3,
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

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "screen1") {
        composable("screen1") {
            Screen1(onNavigate = { navController.navigate("screen2") })
        }
        composable("screen2") {
            Screen2(onBack = { navController.navigateUp() })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen1(onNavigate: () -> Unit) {

    val name = remember { mutableStateOf(TextFieldValue()) }
    val contactNum = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }

    var text1 = " "
    var text2 = " "
    var text3 = " "
    var text4 = " "

    var secondList : MutableList<String> = remember { mutableListOf()}


    val programmes = listOf("Ph.D.","Master Degree","Degree","P.G.Diploma","Diploma","Certificate")


    val phdProgrammes = phdChurchgate.union(phdJuhu).union(phdPune).distinct().sorted()
    val bachelorProgrammes = bachelorChurchgate.union(bachelorJuhu).union(bachelorPune).distinct().sorted()
    val masterProgrammes = masterChurchgate.union(masterJuhu).union(masterPune).distinct().sorted()
    val pgdProgrammes = pgdChurchgate.union(pgdJuhu).union(pgdPune).distinct().sorted()
    val certProgrammes = certChurchgate.union(certJuhu).union(certPune).distinct().sorted()
    val diplomaProgrammes = diplomaChurchgate.union(diplomaJuhu).union(diplomaPune).distinct().sorted()







    var selectedProgramme by remember { mutableStateOf("") }
    var selectedCourse by remember { mutableStateOf("") }

    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }





    Column(
        modifier = Modifier
            .padding(all = 10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ){
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription ="header",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(120.dp)
        )
        Text(
            text = "Your Contact and Course Details:",
            Modifier
                .padding(start = 15.dp, end = 10.dp, top = 10.dp)
                .fillMaxWidth(),
            fontSize = 17.sp,
            textAlign = TextAlign.Left
        )
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            Modifier
                .padding(all = 10.dp)
                .fillMaxWidth(),
            maxLines = 1,
            label = {Text("Name")}
            )
        TextField(
            value = contactNum.value,
            onValueChange = { contactNum.value = it },
            Modifier
                .padding(all = 10.dp)
                .fillMaxWidth(),
            label = {Text("Contact Number")}
        )
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            Modifier
                .padding(all = 10.dp)
                .fillMaxWidth(),
            label = {Text("Email")}
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
                         value = selectedProgramme,
                         onValueChange = { selectedProgramme = it },
                         readOnly = true,
                         trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded1) },
                         colors = ExposedDropdownMenuDefaults.textFieldColors(),
                         modifier = Modifier
                             .menuAnchor()
                             .fillMaxWidth(),
                         singleLine = true,
                         placeholder = { Text("Choose Programme")}
                    )

                    ExposedDropdownMenu(
                        expanded = expanded1,
                        onDismissRequest = { expanded1 = false }
                    ) {
                        programmes.forEachIndexed { index, degree ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedProgramme = degree
                                    expanded1 = false
                                    secondList.clear()
                                    selectedCourse = ""
                                    when (index) {
                                        0 -> secondList.addAll(phdProgrammes)
                                        1 -> secondList.addAll(masterProgrammes)
                                        2 -> secondList.addAll(bachelorProgrammes)
                                        3 -> secondList.addAll(pgdProgrammes)
                                        4 -> secondList.addAll(diplomaProgrammes)
                                        5 -> secondList.addAll(certProgrammes)
                                    }
                                },
                                text = { Text(text = degree) }
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
                    value = selectedCourse,
                    onValueChange = { selectedCourse = it },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded2) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    placeholder = { Text("Choose Course")},
                    maxLines = 1,
                    enabled = secondList.isNotEmpty()

                )

                ExposedDropdownMenu(
                    expanded = expanded2,
                    onDismissRequest = { expanded2 = false }) {
                    secondList.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                selectedCourse = item
                                expanded2 = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                            text = { Text(text = item) }
                        )
                    }
                }

            }
        }


        Button(
            onClick = {

                text1 = name.value.text
                text2 = contactNum.value.text
                text3 = selectedProgramme
                text4 = selectedCourse

                var coursesJuhu : List<String> = listOf("")
                var coursesChurch : List<String> = listOf("")
                var coursesPune : List<String> = listOf("")

//"Ph.D.","Master Degree","Degree","P.G.Diploma","Diploma","Certificate"
                campuses.clear()
                when (selectedProgramme){
                    "Ph.D." -> {
                        coursesJuhu = JuhuCampus.phdList
                        coursesChurch = ChurchCampus.phdList
                        coursesPune = PuneCampus.phdList
                    }
                    "Master Degree" -> {
                        coursesJuhu = JuhuCampus.masterList
                        coursesChurch = ChurchCampus.masterList
                        coursesPune = PuneCampus.masterList
                    }
                    "Degree" -> {
                        coursesJuhu = JuhuCampus.degreeList
                        coursesChurch = ChurchCampus.degreeList
                        coursesPune = PuneCampus.degreeList
                    }
                    "P.G.Diploma" -> {
                        coursesJuhu = JuhuCampus.pgdList
                        coursesChurch = ChurchCampus.pgdList
                        coursesPune = PuneCampus.pgdList
                    }
                    "Diploma" -> {
                        coursesJuhu = JuhuCampus.diplomaList
                        coursesChurch = ChurchCampus.diplomaList
                        coursesPune = PuneCampus.diplomaList
                    }
                    "Certificate" -> {
                        coursesJuhu = JuhuCampus.certList
                        coursesChurch = ChurchCampus.certList
                        coursesPune = PuneCampus.certList
                    }

                }

                if (selectedCourse in coursesJuhu) {
                    campuses.add(JuhuCampus)
                }
                if (selectedCourse in coursesChurch) {
                    campuses.add(ChurchCampus)
                }
                if (selectedCourse in coursesPune) {
                    campuses.add(PuneCampus)
                }


                saveDataToFirebase(text1, text2, text3, text4)
                onNavigate()
            },
            modifier = Modifier.padding(top = 30.dp),
            colors = ButtonDefaults.buttonColors(Color(0, 0, 153))
        ) {
            Text(text = "CONTINUE",
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
        }
        Text(
            text = "Your responses will be recorded for further contact purpose.",
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 5.dp)
                .width(275.dp),
            lineHeight = 13.sp,
            color = Color.Gray

        )


    }
}


@Composable
fun Screen2(onBack: () -> Unit ) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    BackHandler {
        onBack()
    }
    Column(
        modifier = Modifier
            .padding(all = 10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    )
    {
        campuses.forEach { campus ->
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(campus.url))
                        launcher.launch(intent)
                    }
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.TopCenter,
                propagateMinConstraints = true,

                ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(
                        top = 10.dp,
                        bottom = 10.dp,
                        start = 5.dp,
                        end = 5.dp
                    )
                ) {
                    Column(modifier = Modifier.weight(1f).padding(start = 10.dp)) {

                        Text(
                            text = campus.name,
                            fontSize = 25.sp,
                            modifier = Modifier
                                .padding(start = 0.dp),
                            textAlign = TextAlign.Left,
                        )
                        Text(
                            text = campus.contactNum,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .padding(start = 5.dp, top = 10.dp),
                            textAlign = TextAlign.Left,
                        )
                        Text(
                            text = campus.address,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(start = 3.dp, top = 5.dp),
                            textAlign = TextAlign.Left,
                            softWrap = true

                        )
                    }
                    Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = "",
                            tint = Color.Gray,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }



}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StudentFormTheme {
        Screen2 {}
    }
}
