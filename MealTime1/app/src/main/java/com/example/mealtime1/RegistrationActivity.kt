package com.example.mealtime1
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.mealtime1.R

class RegistrationActivity : ComponentActivity() {

    private lateinit var editTextNewUsername: EditText
    private lateinit var editTextNewPassword: EditText
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_activity)

        editTextNewUsername = findViewById(R.id.editTextNewUsername)
        editTextNewPassword = findViewById(R.id.editTextNewPassword)
        buttonRegister = findViewById(R.id.buttonRegister)

        buttonRegister.setOnClickListener {
            // Implement your registration logic here
            val newUsername = editTextNewUsername.text.toString()
            val newPassword = editTextNewPassword.text.toString()

            // Example: Check if new username and password are not empty
            if (newUsername.isNotEmpty() && newPassword.isNotEmpty()) {
                // Replace this with your actual registration logic
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter new username and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
