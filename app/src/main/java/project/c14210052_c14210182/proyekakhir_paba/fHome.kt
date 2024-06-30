package project.c14210052_c14210182.proyekakhir_paba

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import project.c14210052_c14210182.proyekakhir_paba.adapter.adapterNews
import project.c14210052_c14210182.proyekakhir_paba.dataClass.NewsItem

//import project.c14210052.proyekakhir_paba.ARG_PARAM1
//import project.c14210052.proyekakhir_paba.ARG_PARAM2

/**
 * A simple [Fragment] subclass.
 * Use the [fHome.newInstance] factory method to
 * create an instance of this fragment.
 */

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class fHome : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        auth = FirebaseAuth.getInstance()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerViewNews: RecyclerView = view.findViewById(R.id.recyclerViewNews)
        recyclerViewNews.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // tes data buat tampilan news di home page (masih hardcode)
        val newsList = listOf(
            NewsItem("Harga Beras hingga Gula di Ritel Bisa Melonjak Efek Dolar AS Menguat", R.drawable.beras),
            NewsItem("SGM Eksplor-Alfamart Salurkan Bantuan ke 5.000 Anak PAUD Se-Indonesia", R.drawable.susu),
            NewsItem("Lifebuoy Berkolaborasi dengan Kemenkes, Halodoc, dan PDUI Hadirkan Program Mobil Siaga", R.drawable.sabun),
            NewsItem("Ini Beda Keju Alami dan Keju Olahan dan Kandungan Nutrisinya", R.drawable.keju)
        )

        // Set the adapter
        val adapter = adapterNews(newsList)
        recyclerViewNews.adapter = adapter

        val _produkBtn: ImageButton = view.findViewById(R.id.btnProduk)
        val _supplierBtn: ImageButton = view.findViewById(R.id.btnSupplier)
        val _cashierBtn: ImageButton = view.findViewById(R.id.btnCashier)
        val _inventoryBtn: ImageButton = view.findViewById(R.id.btnInventory)
        val _tvUsernameCall: TextView = view.findViewById(R.id.tvUsernameCall)

        _produkBtn.setOnClickListener {
            val intentProduk = Intent(activity, daftarProdukPage::class.java)
            startActivity(intentProduk)
        }

        _supplierBtn.setOnClickListener {
            val intentSupplier = Intent(activity, supplierListPage::class.java)
            startActivity(intentSupplier)
        }

        _cashierBtn.setOnClickListener {
            val intentCashier = Intent(activity, cashierPage::class.java)
            startActivity(intentCashier)
        }

        _inventoryBtn.setOnClickListener {
            val intentInventory = Intent(activity, inventoryPage::class.java)
            startActivity(intentInventory)
        }

        // mengambil username dari firestore dan tampilkan
        val userID = auth.currentUser?.uid
        if (userID != null) {
            db.collection("users").document(userID).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val username = document.getString("username") ?: "User"
                        _tvUsernameCall.text = username
                    }
                }
                .addOnFailureListener {
                    // Handle failure
                }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fHome().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}