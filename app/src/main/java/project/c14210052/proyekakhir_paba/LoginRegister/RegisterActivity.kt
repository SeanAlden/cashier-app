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
import project.c14210052.proyekakhir_paba.MainActivity
import project.c14210052.proyekakhir_paba.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var db = Firebase.firestore
    lateinit var userID: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val _btnSignUp: Button = findViewById(R.id.registerBtnSignUp)
        val _etEmail: EditText = findViewById(R.id.emailEdtTxtSignUp)
        val _etPassword: EditText = findViewById(R.id.passEdtTxtSignUp)
        val _etFullName: EditText = findViewById(R.id.etFullNameSignUp)
        val _btnToLoginPage : Button = findViewById(R.id.btnToSignInPage)

        auth = Firebase.auth

//        val fullNameEditText = findViewById<EditText>(R.id.fullNameEdtTxt)
//        val userNameEditText = findViewById<EditText>(R.id.userNameEdtTxt)
//        val emailEditText = findViewById<EditText>(R.id.emailEdtTxtSignUp)
//        val passwordEditText = findViewById<EditText>(R.id.passEdtTxtSignUp)
//        val registerButton = findViewById<Button>(R.id.registerBtnSignUp)
//        val signInButton = findViewById<Button>(R.id.signInButton)

        _btnToLoginPage.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }

        _btnSignUp.setOnClickListener {
            val email = _etEmail.text.toString()
            val password = _etPassword.text.toString()
            if (TextUtils.isEmpty(email)){
                _etEmail.setError("Email diperlukan")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)){
                _etPassword.setError("Password diperlukan")
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        userID = auth.currentUser!!.uid
                        val data = Users(userID,_etFullName.text.toString(),email,password, "user")
                        db.collection("users").document(userID).set(data)
                            .addOnSuccessListener {
                                _etFullName.setText("")
                                _etEmail.setText("")
                                _etPassword.setText("")
                                Toast.makeText(this, "Account Created", Toast.LENGTH_LONG).show()
                                finish()
                            }
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Autentikasi Gagal!",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }
}







