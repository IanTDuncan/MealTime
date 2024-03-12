package com.example.mealtime1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mealtime1.ui.theme.MealTime1Theme
import com.example.mealtime1.RegistrationActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegisterPage: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonRegisterPage = findViewById(R.id.buttonRegisterPage)

        buttonRegisterPage.setOnClickListener {
            // Navigate to RegistrationActivity
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }


        buttonLogin.setOnClickListener {
            // This is the beginning of our login logic, simple checking.
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            // Example: Check if username and password are not empty
            if (username.isNotEmpty() && password.isNotEmpty()) {
                // The below stuff is a placeholder for what we actually do when a login is unsuccessful.
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainMenuActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

/*   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       setContent {
           MealTime1Theme {
               // A surface container using the 'background' color from the theme
               Surface(
                   modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background
               ) {
                   Greeting("Android")
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
   MealTime1Theme {
       Greeting("Android")
   }
}*/
