package project.c14210052.proyekakhir_paba.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.c14210052.proyekakhir_paba.R
import project.c14210052.proyekakhir_paba.dataClass.KategoriProduk
import project.c14210052.proyekakhir_paba.dataClass.Produk

class adapterKategori(
    private val kategoriList: MutableList<KategoriProduk>,
    private val onEditClick: (KategoriProduk) -> Unit,
    private val onDeleteClick: (KategoriProduk) -> Unit
) : RecyclerView.Adapter<adapterKategori.KategoriViewHolder>() {

    inner class KategoriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryId: TextView = itemView.findViewById(R.id.categoryId)
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        val choiceBtn: ImageButton = itemView.findViewById(R.id.choiceBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_category, parent, false)
        return KategoriViewHolder(view)
    }

    override fun onBindViewHolder(holder: KategoriViewHolder, position: Int) {
        val kategori = kategoriList[position]
        holder.categoryId.text = kategori.idKategori.toString()
        holder.categoryName.text = kategori.namaKategori

        holder.choiceBtn.setOnClickListener {
            showPopupMenu(it, kategori)
        }
    }

    override fun getItemCount(): Int {
        return kategoriList.size
    }

    private fun showPopupMenu(view: View, kategori: KategoriProduk) {
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