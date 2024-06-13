package project.c14210052.proyekakhir_paba

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
//import project.c14210052.proyekakhir_paba.ARG_PARAM1
//import project.c14210052.proyekakhir_paba.ARG_PARAM2

/**
 * A simple [Fragment] subclass.
 * Use the [fProfile.newInstance] factory method to
 * create an instance of this fragment.
 */

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class fProfile : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var _rvAsetProfile: RecyclerView
    var listUsers: ArrayList<Users> = arrayListOf()
    private lateinit var auth: FirebaseAuth

    var db = Firebase.firestore
    lateinit var userID: String
    lateinit var user: FirebaseUser

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
//        _rvAsetProfile.layoutManager = LinearLayoutManager(context)
        _rvAsetProfile = view.findViewById(R.id.rvProfile)
        userID = auth.currentUser!!.uid
        user = auth.currentUser!!
        showData()
    }


    fun addUsers() {
        var docRef = db.collection("users").document(userID)

        docRef.get().addOnSuccessListener {
            listUsers.clear()
            val hasil = Users(
                docRef.id,
                it.getString("fullname"),
                it.getString("email"),
                it.getString("password"),
                it.getString("status")
            )
            listUsers.add(hasil)
            _rvAsetProfile.adapter?.notifyDataSetChanged()
        }
    }

    fun showData() {
        _rvAsetProfile.layoutManager = LinearLayoutManager(super.requireActivity() as MainActivity)
        val adapterPr = adapterProfile(listUsers)
        _rvAsetProfile.adapter = adapterPr

        adapterPr.setOnItemClickCallback(object : adapterProfile.OnItemClickCallBack {
            override fun editProfile(data: Users) {
                val intent = Intent(requireActivity() as MainActivity, editProfilePage::class.java)
                intent.putExtra("kirimDataProfile", data)
                startActivity(intent)
            }

            override fun signOut(data: Users) {
                Firebase.auth.signOut()
                startActivity(Intent(requireActivity() as MainActivity, loginPage::class.java))
                Toast.makeText(
                    requireActivity() as MainActivity,
                    "Sign Out Berhasil",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun deleteProfile(data: Users) {
                AlertDialog.Builder(requireActivity() as MainActivity).setTitle("Delete Account")
                    .setMessage("Apakah Anda yakin ingin menghapus akun?")
                    .setPositiveButton("Hapus", DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(
                            requireActivity() as MainActivity,
                            "Delete Akun Sukses",
                            Toast.LENGTH_LONG
                        ).show()
                        db.collection("users").document(userID).delete()
                        user.delete()
                        startActivity(
                            Intent(
                                requireActivity() as MainActivity,
                                loginPage::class.java
                            )
                        )
                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->

                    })
                    .show()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        addUsers()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}