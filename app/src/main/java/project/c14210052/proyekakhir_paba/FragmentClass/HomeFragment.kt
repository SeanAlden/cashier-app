package project.c14210052.proyekakhir_paba.FragmentClass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
//import project.c14210052.proyekakhir_paba.ARG_PARAM1
//import project.c14210052.proyekakhir_paba.ARG_PARAM2
import project.c14210052.proyekakhir_paba.Cashier.CashierActivity
import project.c14210052.proyekakhir_paba.Inventory.InventoryActivity
import project.c14210052.proyekakhir_paba.LoginRegister.User
import project.c14210052.proyekakhir_paba.Product.DaftarProdukActivity
import project.c14210052.proyekakhir_paba.R
import project.c14210052.proyekakhir_paba.Supplier.SupplierListActivity

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val produkBtn: ImageButton = view.findViewById(R.id.produkBtn)
        val supplierBtn: ImageButton = view.findViewById(R.id.supplierBtn)
        val cashierBtn: ImageButton = view.findViewById(R.id.cashierBtn)
        val inventoryBtn: ImageButton = view.findViewById(R.id.inventoryBtn)

        // Get username from SharedPreferences
        val sharedPreferences = activity?.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences?.getString("username", "User")
//        val userJson = sharedPreferences?.getString("user", null)
//        val user = if (userJson != null) Gson().fromJson(userJson, User::class.java) else null

        // Set username to TextView
        val shopNameTextView = view.findViewById<TextView>(R.id.shopName)
        shopNameTextView.text = username
//        shopNameTextView.text = user?.userName ?

        produkBtn.setOnClickListener {
            val intentProduk = Intent(activity, DaftarProdukActivity::class.java)
            startActivity(intentProduk)
        }

        supplierBtn.setOnClickListener {
            val intentSupplier = Intent(activity, SupplierListActivity::class.java)
            startActivity(intentSupplier)
        }

        cashierBtn.setOnClickListener {
            val intentCashier = Intent(activity, CashierActivity::class.java)
            startActivity(intentCashier)
        }

        inventoryBtn.setOnClickListener {
            val intentInventory = Intent(activity, InventoryActivity::class.java)
            startActivity(intentInventory)
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
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}