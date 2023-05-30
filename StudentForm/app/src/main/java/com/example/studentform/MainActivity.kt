package com.example.studentform

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults.color
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form() {

    val name = remember { mutableStateOf(TextFieldValue()) }
    val contactNum = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }

    var text1 = " "
    var text2 = " "
    var text3 = " "
    var text4 = " "

    var secondList : MutableList<String> = remember { mutableListOf()}



    val campus = listOf("Juhu Campus", "Churchgate Campus", "Pune Campus")
    val ListJuhu = listOf("Department of Extension & Communication","Department of Educational Technology","Department of Special Education", "Department of Human Development", "Department of Food Science & Nutrition", "Department of Family Resource Management", "Department of Textile Science and Apparel Design", "Department of Computer Science", "Department of Education Management", "C U Shah College of Pharmacy", "SHPT College of Science - Department of Analytical Chemistry")
    val programmes = listOf("Ph.D.","Master Degree","Degree","P.G.Diploma","Diploma","Certificate")
    val phdChurchgate = listOf("Ph.D in Dept. of Marathi"
        ,"Ph.D in Dept. of English"
        ,"Ph.D in Dept. of Hindi"
        ,"Ph.D in Dept. of Sanskrit"
        ,"Ph.D in Dept. of Gujarati"
        ,"Ph.D in Dept. of History"
        ,"Ph.D in Dept. of Psychology"
        ,"Ph.D in Dept. of Sociology"
        ,"Ph.D in Dept. of Political Science"
        ,"Ph.D in Dept. of Economics"
        ,"Ph.D in Dept. of Social work"
        ,"Ph.D in Dept. of Lifelong Learning & Extension"
        ,"Ph.D in S.H.P.T. School of Library Science"
        ,"Ph.D in Dept. of Education"
        ,"Ph.D in L.T College of Nursing"
        ,"Ph.D in Dept. of Music"
        ,"Ph.D in Dept. of Commerce"
    )
    val phdJuhu = listOf("Ph.D in Dept. of Food Science & Nutrition"
        ,"Ph.D in Dept. of Family Resource Management"
        ,"Ph.D in Research Centre for Women's Studies"
        ,"Ph.D in S.H.P.T. School of Science - Analytical chemistry"
        ,"Ph.D in C.U.Shah College of Pharmacy"
        ,"Ph.D in Dept. of Educational Technology"
        ,"Ph.D in Dept. of Computer Science"
        ,"Ph.D in Usha Mittal Institute of Technology"
        ,"Ph.D in JankiDevi Bajaj Institute of Management Studies"
        ,"Ph.D in Dept. of Education Management"
        ,"Ph.D in Dept. of Human Development"
        ,"Ph.D in Dept of Special Education"
        ,"Ph.D in Dept of Extension & Communication"
        ,"Ph.D in SNDTWU Law School"
        ,"Ph.D in Textile Science & Apparel Design")
    val phdPune = listOf("Ph.D in Dept. of Marathi"
        ,"Ph.D in Dept. of Hindi"
        ,"Ph.D in Dept. of Psychology"
        ,"Ph.D in Dept. of Economics"
        ,"Ph.D in Dept. of Geography"
        ,"Ph.D in Dept. of Music"
        ,"Ph.D in Dept. of Commerce"
        ,"Ph.D in Dept. of Drawing and Painting"
    )
    val masterChurchgate = listOf("Master of Arts (M.A. - Marathi)"
        ,"Master of Arts (M.A. - Hindi)"
        ,"Master of Arts (M.A. - Gujarati)"
        ,"Master of Arts (M.A. - Sanskrit)"
        ,"Master of Arts (M.A. - English)"
        ,"Master of Arts (M.A. - Music)"
        ,"Master of Visual Arts (M.V.A. - Creative Painting)"
        ,"Master of Visual Arts (M.V.A. - Portraiture Painting)"
        ,"Master of Visual Arts (M.V.A. - Mural Painting)"
        ,"Master of Arts (M.A. - Economics)"
        ,"Master of Arts (M.A. - History)"
        ,"Master of Arts (M.A. - Sociology)"
        ,"Master of Arts (M.A. - Political Science)"
        ,"Master of Arts (M.A. - Psychology)"
        ,"Master of Arts (M.A. - Non Formal Education and Development)"
        ,"Master of Commerce (M Com)"
        ,"Master of Arts (M A - Education)"
        ,"Master of Education (M.Ed)"
        ,"Master of Library and Information Science (M L. I.S)"
        ,"Master of Social Work (MSW)"
        ,"Master of Science (M. Sc. - Nursing)"
    )

    val masterJuhu = listOf("Master of Arts (M.A - Women Studies)"
        ,"Master of Science (M Sc. - Food Science and Nutrition)"
        ,"Master of Science (M.Sc. - Clinical Nutrition & Dietetics)"
        ,"Master of Science (M.Sc. - Resource Management and Ergonomics)"
        ,"Master of Science (M Sc. - Extension & Communication)"
        ,"Master of Arts in Media and Communication"
        ,"Master of Science (M Sc. - Human Development)"
        ,"Master of Science ( M.Sc. - Early Childhood Education)"
        ,"Master of Science (M Sc. - Textile Science and Apparel Design)"
        ,"Master of Education (M. Ed. Special Education: Intellectual Disability)"
        ,"Master of Education (M. Ed. Special Education: Learning Disability)"
        ,"Master of Education (M. Ed. Special Education: Visual Impairment)"
        ,"Master of Arts (M.A. eLearning)"
        ,"Master of Arts (M.Sc. eLearning)"
        ,"Master of Education Management (MEM)"
        ,"Master of Business Administration (MBA - Human Resource Management)"
        ,"Master of Business Administration (MBA - Marketing Management)"
        ,"Master of Business Administration (MBA - Finance Management)"
        ,"Master of Management Studies (MMS: HR, Finance, Marketing operations)"
        ,"Master of Law (LLM): 2 years"
        ,"Master of Science (M. Sc. : Analytical Chemistry)"
        ,"Master of Pharmacy (M. Pharm. : Pharmaceutics)"
        ,"Master of Pharmacy (M. Pharm. Quality Assurance)"
        ,"Master of Pharmacy (M.Pharm: Phytopharmacy and Phytomedicine)"
        ,"Master of Technology (M. Tech.: Computer Science and Technology)"
        ,"Master of Technology (M. Tech.: Electronics and Communication)"
        ,"Master of Computer Applications (MCA)"
        ,"Master of Science (M. Sc.) in Computer Science"
        ,"Master of Science (M.Sc. - Resource Management and Interior Design)")
    val masterPune = listOf("Master of Arts (M.A. - Marathi)"
        , "Master of Arts (M.A. - Hindi)"
        , "Master of Arts (M.A. - Music)"
        , "Master of Visual Arts (M.V.A. - Creative Painting)"
        , "Master of Visual Arts (M.V.A. - Portraiture Painting)"
        , "Master of Visual Arts (M.V.A. - Mural Painting)"
        , "Master of Arts (M.A. - Geography)"
        , "Master of Arts (M.A. - Economics)"
        , "Master of Arts (M.A. - Psychology)"
        , "Master of Commerce (M. Com)"
        , "Master of Science (M Sc.: Communication Media for Children)"
        , "Master of Science (M Sc.: Nutrition and health communication)"
        , "Master of Arts in Media and Communication"
    )
    val bachelorChurchgate = listOf("Bachelor of Science (B.Sc. Nursing)")
    val bachelorJuhu = listOf("Bachelor of Education (B.Ed Special Education: Intellectual Disability)"
        ,"Bachelor of Education (B.Ed Special Education: Learning Disability)"
        ,"Bachelor of Education (B.Ed Special Education: Visual Impairment)"
        ,"Bachelor of Pharmacy"
        ,"Bachelor of Technology (B.Tech. - Computer Science & Technology)"
        ,"Bachelor of Technology (B.Tech. - Electronics Engineering)"
        ,"Bachelor of Technology (B.Tech. - Electronics & Telecommunication)"
        ,"Bachelor of Technology (B.Tech. - Information Technology)"
        ,"Bachelor of Business Administration and Bachelor of Law"
        ,"Bachelor of Law"
        ,"Bachelor of Vocation (Jewellery Design and Manufacture)"
        ,"Bachelor of Vocation (Optometry)"
        ,"Bachelor of Vocation (Interior Design)"
        ,"Bachelor of Vocation (Food Processing Technology)"
        ,"Bachelor of Vocation (Fashion Design)"
    )
    val bachelorPune : List<String> = listOf("")
    val pgdChurchgate = listOf("Post Graduate Diploma in Travel and Tourism")
    val pgdJuhu = listOf("Post Graduate Diploma in Computer Science and Applications"
        ,"Post Graduate Diploma in Dietetics"
        ,"Post Graduate Diploma in Early Childhood Education"
        ,"Post Graduate Diploma in Education Management"
        ,"Post Graduate Diploma in Human Resource Management"
        ,"Post Graduate Diploma in Management of Learning Disabilities"
        ,"Post Graduate Diploma in Nutrition Food Processing and Technology"
        ,"Post Graduate Diploma in Quality Assurance in Apparel Industry"
        ,"Post Graduate Diploma in Apparel Merchandizing and Management"
    )
    val pgdPune : List<String> = listOf("")
    val certJuhu = listOf("Certificate course in Shadow Teaching"
    )
    val certChurchgate : List<String> = listOf("")
    val certPune : List<String> = listOf("")
    val diplomaChurchgate : List<String> = listOf("")
    val diplomaPune : List<String> = listOf("")
    val diplomaJuhu : List<String> = listOf("")

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
    val icon = if (expanded1) {
        Icons.Filled.KeyboardArrowUp}else{
        Icons.Filled.KeyboardArrowDown
    }






    Column (modifier = Modifier.padding(all = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
            text = "Admission Details",
            Modifier.padding(all = 10.dp),
            fontSize = 30.sp
        )
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            Modifier.padding(all = 10.dp).fillMaxWidth(),
            maxLines = 1,
            label = {Text("Name")}
            )
        TextField(
            value = contactNum.value,
            onValueChange = { contactNum.value = it },
            Modifier.padding(all = 10.dp).fillMaxWidth(),
            label = {Text("Contact Number")}
        )
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            Modifier.padding(all = 10.dp).fillMaxWidth(),
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
                         modifier = Modifier.menuAnchor().fillMaxWidth(),
                        maxLines = 1,
                         placeholder = { Text("Choose Programme")}
                    )

                    ExposedDropdownMenu(
                        expanded = expanded1,
                        onDismissRequest = { expanded1 = false }) {
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
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
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



                saveDataToFirebase(text1, text2, text3, text4)
            },
            modifier = Modifier.padding(top = 30.dp)
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
