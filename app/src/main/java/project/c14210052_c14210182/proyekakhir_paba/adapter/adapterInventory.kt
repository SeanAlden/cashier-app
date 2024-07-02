package project.c14210052_c14210182.proyekakhir_paba.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.c14210052_c14210182.proyekakhir_paba.R
import project.c14210052_c14210182.proyekakhir_paba.dataClass.Produk

class adapterInventory(
    private val productList: MutableList<Produk>,
) : RecyclerView.Adapter<adapterInventory.InventoryViewHolder>() {

    inner class InventoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nama: TextView = itemView.findViewById(R.id.tvProdukInventory)
        val jumlah: TextView = itemView.findViewById(R.id.tvSisaInventory)
        val satuan: TextView = itemView.findViewById(R.id.tvSatuanInventory)

        fun bind(produk: Produk) {
            nama.text = produk.namaProduk
            jumlah.text = produk.jumlahProduk.toString()
            satuan.text = produk.satuanProduk

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_inventory, parent, false)
        return InventoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterInventory.InventoryViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

}