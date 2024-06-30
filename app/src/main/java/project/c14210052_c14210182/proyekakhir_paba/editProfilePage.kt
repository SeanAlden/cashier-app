package project.c14210052_c14210182.proyekakhir_paba

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import project.c14210052_c14210182.proyekakhir_paba.dataClass.Users

class editProfilePage : AppCompatActivity() {

    private lateinit var _backButton : ImageButton
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var auth:FirebaseAuth
        val db = Firebase.firestore
        lateinit var userID:String
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        auth = Firebase.auth
        var intentData = intent.getParcelableExtra<Users>("kirimDataProfile")
        var _etFullname:EditText = findViewById(R.id.etFullNameEditProfile)
        var _etUsername:EditText = findViewById(R.id.etUserNameEditProfile)
        var _btnSave:Button = findViewById(R.id.btnSaveEditProfile)
        _etFullname.setText(intentData!!.fullname.toString())
        _etUsername.setText(intentData!!.username.toString())
        userID = auth.currentUser!!.uid

        _btnSave.setOnClickListener {
            val docRef = db.collection("users").document(userID)
            val newData = Users(docRef.id,_etFullname.text.toString(),_etUsername.text.toString(),intentData.email.toString(),intentData.password.toString(),intentData.status.toString())

            docRef.set(newData).addOnSuccessListener {
                Toast.makeText(this@editProfilePage,"Edit Profile Success",Toast.LENGTH_LONG).show()
            }
            finish()
        }

        _backButton = findViewById(R.id.btnBackFromEditProfile)

        _backButton.setOnClickListener {
//            val intent = Intent(this@editProfilePage, MainActivity::class.java)
//            startActivity(intent)
            finish()
        }
    }
}

