package project.c14210052_c14210182.proyekakhir_paba.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import project.c14210052_c14210182.proyekakhir_paba.R
import project.c14210052_c14210182.proyekakhir_paba.dataClass.kategoriProduk

class adapterKategori(
    private val kategoriList: MutableList<kategoriProduk>,
    private val onEditClick: (kategoriProduk) -> Unit,
    private val onDeleteClick: (kategoriProduk) -> Unit
) : RecyclerView.Adapter<adapterKategori.KategoriViewHolder>() {

    inner class KategoriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryId: TextView = itemView.findViewById(R.id.categoryId)
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        val choiceBtn: ImageButton = itemView.findViewById(R.id.choiceBtn)
        val productUsedTotal: TextView = itemView.findViewById(R.id.jumlahProdukUseCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_category, parent, false)
        return KategoriViewHolder(view)
    }

    override fun onBindViewHolder(holder: KategoriViewHolder, position: Int) {
        val kategori = kategoriList[position]
        holder.categoryId.text = kategori.idKategori.toString()
        holder.categoryName.text = kategori.namaKategori

        getJumlahProduk(kategori.namaKategori, holder.productUsedTotal)

        holder.choiceBtn.setOnClickListener {
            showPopupMenu(it, kategori)
        }
    }

    override fun getItemCount(): Int {
        return kategoriList.size
    }

    private fun getJumlahProduk(namaKategori: String?, productUsedTotal: TextView) {
        val db = FirebaseFirestore.getInstance()
        val productsRef = db.collection("tbProduk")
            .whereEqualTo("kategoriProduk", namaKategori)

        productsRef.get().addOnSuccessListener { documents ->
            productUsedTotal.text = documents.size().toString()
        }.addOnFailureListener {
            productUsedTotal.text = "0"
        }
    }

    private fun showPopupMenu(view: View, kategori: kategoriProduk) {
        val popup = PopupMenu(view.context, view)
        popup.inflate(R.menu.category_options_menu)
        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.action_edit -> {
                    onEditClick(kategori)
                    true
                }
                R.id.action_delete -> {
                    onDeleteClick(kategori)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }


//    class KategoriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val categoryId: TextView = itemView.findViewById(R.id.categoryId)
//        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
//    }
}