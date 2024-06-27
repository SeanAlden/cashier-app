package project.c14210052.proyekakhir_paba

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import project.c14210052.proyekakhir_paba.adapter.adapterSupplier
import project.c14210052.proyekakhir_paba.dataClass.Supplier

class supplierListPage : AppCompatActivity(), adapterSupplier.OnDeleteClickListener, adapterSupplier.OnEditClickListener{

    private lateinit var _recyclerView: RecyclerView
    private lateinit var _suppAdapter: adapterSupplier
    private lateinit var db: FirebaseFirestore
    private var _suppList = mutableListOf<Supplier>()
    private var _suppListener: ListenerRegistration? = null
    lateinit var _btnBack : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supplier_list)

        _recyclerView = findViewById(R.id.rvSupplier)
        _recyclerView.layoutManager = LinearLayoutManager(this)
        _suppAdapter = adapterSupplier(_suppList, this, this)
        _recyclerView.adapter = _suppAdapter

        val btnTambahSupp: ImageButton = findViewById(R.id.btnAddSupplier)
        btnTambahSupp.setOnClickListener {
            val intent = Intent(this@supplierListPage, addSupplierPage::class.java)
            startActivity(intent)
        }

        db = FirebaseFirestore.getInstance()

        _suppListener = db.collection("tbSupplier")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                snapshots?.let { snapshot ->
                    _suppList.clear()
                    for (document in snapshot.documents) {
                        val suppItem = document.toObject(Supplier::class.java)
                        suppItem?.let {
                            it.id = document.id
                            _suppList.add(it)
                        }
                    }
                    _suppAdapter.notifyDataSetChanged()
                }
            }
        _btnBack = findViewById(R.id.btnBackFromSupplierList)
        _btnBack.setOnClickListener {
            val intent = Intent(this@supplierListPage, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDeleteClick(position: Int) {
        if (position in _suppList.indices) {
            val suppItem = _suppList[position]

            db.collection("tbSupplier").document(suppItem.id)
                .delete()
                .addOnCompleteListener { task ->
                    _suppAdapter.notifyItemRemoved(position)
                    if (task.isSuccessful) {
                        println("Deleting item at position: $position, List size before removal: ${_suppList.size}")
                        println("List size after removal: ${_suppList.size}")
                    } else {
                        println("Failed to delete item from Firestore: ${task.exception?.message}")
                    }
                }
            println("Position: $position, List size: ${_suppList.size}, todoList: $_suppList")
        } else {
            println("Invalid position: $position, List size: ${_suppList.size}")
        }
    }

    override fun onEditClick(suppItem: Supplier) {
        val intent = Intent(this, editSupplierPage::class.java)
        intent.putExtra("SUPP_ID", suppItem.id)
        intent.putExtra("SUPP_NAME", suppItem.namaSupplier)
        intent.putExtra("SUPP_EMAIL", suppItem.emailSupplier)
        intent.putExtra("SUPP_TELP", suppItem.teleponSupplier)
        intent.putExtra("SUPP_ALAMAT", suppItem.alamatSupplier)
        intent.putExtra("SUPP_KOTA", suppItem.kotaSupplier)
        intent.putExtra("SUPP_PROV", suppItem.provinsiSupplier)
        intent.putExtra("SUPP_KODE", suppItem.kodeSupplier)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _suppListener?.remove()
    }
}






//    private lateinit var sharedPreferences: SharedPreferences
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var supplierAdapter: adapterSupplier
//    private lateinit var _btnBack: ImageButton
//    private lateinit var suppliers : MutableList<Supplier>
//    private val gson = Gson()
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_supplier_list)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        // Initialize suppliers and adapter
//        suppliers = mutableListOf()
////        supplierAdapter = adapterSupplier(suppliers, this::deleteSupplier, this::editSupplier)
//
//        sharedPreferences = getSharedPreferences("SupplierData", MODE_PRIVATE)
//
////        val clearData = intent.getBooleanExtra("clearData", false)
////        if (clearData) {
////            sharedPreferences.edit().clear().apply()
////        }
//
//        val suppliersJson = sharedPreferences.getString("suppliers", "[]")
//        val suppliersType = object : TypeToken<List<Supplier>>() {}.type
//        val suppliers: MutableList<Supplier> = gson.fromJson(suppliersJson, suppliersType)
//
//        recyclerView = findViewById(R.id.rvSupplier)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        supplierAdapter = adapterSupplier(suppliers, { position ->
//            suppliers.removeAt(position)
//            val editor = sharedPreferences.edit()
//            editor.putString("suppliers", gson.toJson(suppliers))
//            editor.apply()
//            supplierAdapter.notifyItemRemoved(position)
//        }, { supplier ->
//            val intent = Intent(this, supplierInformationPage::class.java)
////            intent.putExtra("namaSupplier", supplier.namaSupplier)
////            intent.putExtra("alamatSupplier", supplier.alamatSupplier)
////            intent.putExtra("kodeSupplier", supplier.kodeSupplier)
//            intent.putExtra("supplier", gson.toJson(supplier))
//            startActivity(intent)
//        })
//        recyclerView.adapter = supplierAdapter
//
//        val addSuppButton = findViewById<ImageButton>(R.id.btnAddSupplier)
//        _btnBack = findViewById(R.id.btnBackFromSupplierList)
//
//        addSuppButton.setOnClickListener {
//            val intent = Intent(this, addSupplierPage::class.java)
//            startActivity(intent)
//        }
//
//        _btnBack.setOnClickListener {
//            val intent = Intent(this@supplierListPage, MainActivity::class.java)
//            startActivity(intent)
//        }
//    }
//}




