package project.c14210052.proyekakhir_paba.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.c14210052.proyekakhir_paba.R
import project.c14210052.proyekakhir_paba.dataClass.KategoriProduk

class KategoriAdapter(private val kategoriList: List<KategoriProduk>) :
    RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_category, parent, false)
        return KategoriViewHolder(view)
    }

    override fun onBindViewHolder(holder: KategoriViewHolder, position: Int) {
        val kategori = kategoriList[position]
        holder.categoryId.text = kategori.idKategori.toString()
        holder.categoryName.text = kategori.namaKategori
    }

    override fun getItemCount(): Int {
        return kategoriList.size
    }

    class KategoriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryId: TextView = itemView.findViewById(R.id.categoryId)
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
    }
}