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
import com.google.firebase.ktx.Firebase

class loginPage : AppCompatActivity() {

    // inisialisasi firebase auth untuk autentikasi user yang login
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        // melakukan cek kalau user telah melakukan sign in, maka akan langsung ke MainActivity

        val currentUser = auth.currentUser
        // kalau terdapat user yang terdaftar, maka akan langsung menavigasi ke main activity
        if (currentUser != null) {
            startActivity(Intent(this@loginPage, MainActivity::class.java))
            // animasi
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
            return
        }

        // inisialisasi untuk elemen-elemen halaman seperti inputan teks, dan tombol
        var _goToSignUpButton : Button = findViewById(R.id.btnGoToSignUpPage)
        var _loginButton : Button = findViewById(R.id.btnLogin)
        var _emailEditText : EditText = findViewById(R.id.etEmail)
        var _passwordEditText : EditText = findViewById(R.id.etPassword)


        _goToSignUpButton.setOnClickListener {
            startActivity(Intent(this@loginPage, registerPage::class.java))
        }

        _loginButton.setOnClickListener {
            val email = _emailEditText.text.toString()
            val password = _passwordEditText.text.toString()

            // melakukan pengecekan, jika email dan password belum diisi, maka akan menampilkan
            // pesan untuk mengisi email dan password

            if (TextUtils.isEmpty(email)) {
                _emailEditText.setError("Email diperlukan")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                _passwordEditText.setError("Password diperlukan")
                return@setOnClickListener
            }

            // autentikasi user ketika melakukan sign in
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    // kalau berhasil, maka akan masuk ke halaman main activity
                    if (task.isSuccessful) {
                        val intent = Intent(this@loginPage, MainActivity::class.java)
                        startActivity(intent)
                        // animasi
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        Toast.makeText(this, "Login Sukses", Toast.LENGTH_LONG).show()
                        finish()
//                        startActivity(Intent(this@loginPage, MainActivity::class.java))
//                        Toast.makeText(this, "Login Sukses", Toast.LENGTH_LONG).show()
//                        finish()
                    } else {
                        // kalau gagal, menampilkan pesan error kepada user
                        Toast.makeText(this, "Autentikasi gagal, silahkan coba lagi", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}








