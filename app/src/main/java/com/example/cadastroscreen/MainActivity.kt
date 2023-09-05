package com.example.cadastroscreen

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cadastroscreen.ui.theme.CadastroScreenTheme
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CadastroScreenTheme {
                App()
            }
        }
    }
}

@Composable
fun App(){ // Função App com todos os componentes
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ){
            SimpleCenterAlignedTopAppBar() // Componente Top Bar
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ){
        }
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TelaInput()
        }
    }
}

@Preview
@Composable
fun AppPreview(){ // Preview da função app
    App()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleCenterAlignedTopAppBar() { // Componente Top Bar
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "AppFirestore - Cadastro",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

            }
        }
    )
}

@Preview (showBackground = true)
@Composable
fun SimpleCenterAlignedTopAppBarPreview(){ // Preview Top Bar
    SimpleCenterAlignedTopAppBar()
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaInput() { // Componente Input
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Adiciona um espaçamento externo
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Formulário simples
        val textNome = rememberSaveable { mutableStateOf("") }
        TextField(
            value = textNome.value,
            onValueChange = { textNome.value = it },
            label = { Text("Nome") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        val textTel = rememberSaveable { mutableStateOf("") }
        TextField(
            value = textTel.value,
            onValueChange = { textTel.value = it },
            label = { Text("Telefone") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        val options = listOf("Whatsapp", "Facebook", "Instagram", "Twitter")
        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember { mutableStateOf(options[0]) }
        // We want to react on tap/press on TextField to show menu
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                // The `menuAnchor` modifier must be passed to the text field for correctness.
                modifier = Modifier
                    .menuAnchor()
                    .padding(8.dp)
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedOptionText,
                onValueChange = {},
                label = { Text("Origem") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }

        val context = LocalContext.current
        val calendar = Calendar.getInstance()

        var selectedDateText by remember { mutableStateOf("") }

// Fetching current year, month and day
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

        val datePicker = DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            }, year, month, dayOfMonth
        )
        datePicker.datePicker.minDate = calendar.timeInMillis

        Column(

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = {
                    datePicker.show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = if (selectedDateText.isNotEmpty()) {
                    "Data Selecionada $selectedDateText"
                } else {
                    "Data de Contato"
                })
            }
        }
        val textObs = rememberSaveable { mutableStateOf("") }
        TextField(
            value = textObs.value,
            onValueChange = { textObs.value = it }, modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(10.dp)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp)),
            label = { Text("Observação") },
        )
        val tag = "ButtonWithLog"
        Button(
            onClick = {
                Log.d(tag, "Botão 'Cadastrar' clicado")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Cadastrar")
        }
        Button(
            onClick = {
                Log.d(tag, "Botão 'Cancelar' clicado")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Cancelar")
        }
    }
}

@Preview (showBackground = true)
@Composable
fun TelaInputPreview(){ // Inputs
    TelaInput()
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CadastroScreenTheme {
        Greeting("Android")
    }
}