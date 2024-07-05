package project.c14210052_c14210182.proyekakhir_paba

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
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
import project.c14210052_c14210182.proyekakhir_paba.dataClass.Users

class registerPage : AppCompatActivity() {

    // inisialisasi firebase auth untuk autentikasi user yang register / daftar akun
    private lateinit var auth: FirebaseAuth
    // membuat database untuk menyimpan akun user yang didaftarkan
    var db = Firebase.firestore
    // inisialisasi id untuk akun user yang didaftarkan
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

        // inisialisasi untuk tiap komponen seperti teks inputan, tombol, dan lainnya
        val _btnSignUp: Button = findViewById(R.id.btnCreateAccount)
        val _etEmail: EditText = findViewById(R.id.etEmaill)
        val _etPassword: EditText = findViewById(R.id.etPasswordd)
        val _etFullName: EditText = findViewById(R.id.etFull_Name)
        val _etUsername: EditText = findViewById(R.id.etUsername)
        val _btnToLoginPage : Button = findViewById(R.id.btnGoToSignInPage)

        auth = Firebase.auth

        _btnToLoginPage.setOnClickListener {
            startActivity(Intent(this@registerPage, loginPage::class.java))
        }

        _btnSignUp.setOnClickListener {
            val email = _etEmail.text.toString()
            val password = _etPassword.text.toString()

            // melakukan pengecekan, jika email dan password belum diisi, maka akan menampilkan
            // pesan untuk mengisi email dan password

            if (TextUtils.isEmpty(email)){
                _etEmail.setError("Email diperlukan")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)){
                _etPassword.setError("Password diperlukan")
                return@setOnClickListener
            }

            // melakukan pengecekan panjang password
            if (password.length < 6) {
                _etPassword.setError("Password harus terdiri dari minimal 6 karakter")
                return@setOnClickListener
            }

            // autentikasi user ketika melakukan register
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        userID = auth.currentUser!!.uid
                        val data = Users(userID,_etFullName.text.toString(), _etUsername.text.toString(),email,password, "user")
                        db.collection("users").document(userID).set(data)
                            .addOnSuccessListener {
                                _etFullName.setText("")
                                _etUsername.setText("")
                                _etEmail.setText("")
                                _etPassword.setText("")
                                Toast.makeText(this, "Account Created", Toast.LENGTH_LONG).show()

                                finish()
                            }
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







