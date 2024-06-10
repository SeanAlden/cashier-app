package project.c14210052.proyekakhir_paba.LoginRegister

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.MainScope
import project.c14210052.proyekakhir_paba.MainActivity
import project.c14210052.proyekakhir_paba.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()

        val fullNameEditText = findViewById<EditText>(R.id.fullNameEdtTxt)
        val userNameEditText = findViewById<EditText>(R.id.userNameEdtTxt)
        val emailEditText = findViewById<EditText>(R.id.emailEdtTxtSignUp)
        val passwordEditText = findViewById<EditText>(R.id.passEdtTxtSignUp)
        val registerButton = findViewById<Button>(R.id.registerBtnSignUp)
        val signInButton = findViewById<Button>(R.id.signInButton)

        registerButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString().trim()
            val userName = userNameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (fullName.isNotEmpty() && userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                signUp(fullName, userName, email, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        signInButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun signUp(fullName: String, userName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("RegisterActivity", "createUserWithEmail:success")
                    val user = auth.currentUser
                    val userId = user?.uid

                    val userMap = hashMapOf(
                        "fullName" to fullName,
                        "userName" to userName,
                        "email" to email
                    )

                    if (userId != null) {
                        db.collection("users").document(userId)
                            .set(userMap)
                            .addOnSuccessListener {
                                Log.d("RegisterActivity", "DocumentSnapshot successfully written!")
                                // Save username in SharedPreferences
                                val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("username", userName)
                                editor.apply()

                                // Navigate to your main activity
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Log.w("RegisterActivity", "Error writing document", e)
                                Toast.makeText(this, "Registration failed.", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Log.w("RegisterActivity", "User ID is null")
                        Toast.makeText(this, "User ID is null.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.w("RegisterActivity", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}







