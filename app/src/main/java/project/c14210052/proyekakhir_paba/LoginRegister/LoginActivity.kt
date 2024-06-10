package project.c14210052.proyekakhir_paba.LoginRegister

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
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import project.c14210052.proyekakhir_paba.MainActivity
import project.c14210052.proyekakhir_paba.R

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_login)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val _emailEditText = findViewById<EditText>(R.id.emailEdtTxtSignIn)
        val _passwordEditText = findViewById<EditText>(R.id.passEdtTxtSignIn)
        val _loginButton = findViewById<Button>(R.id.loginBtn)
        val _goToSignUpButton = findViewById<Button>(R.id.signUpButton)

        auth = Firebase.auth

        _goToSignUpButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        _loginButton.setOnClickListener {
            val email = _emailEditText.text.toString()
            val password = _passwordEditText.text.toString()

            if (TextUtils.isEmpty(email)) {
                _emailEditText.setError("Email diperlukan")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) { _passwordEditText.setError("Password diperlukan")
                _passwordEditText.setError("Password diperlukan")
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        Toast.makeText(this, "Login Sukses", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Autentikasi gagal",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}








