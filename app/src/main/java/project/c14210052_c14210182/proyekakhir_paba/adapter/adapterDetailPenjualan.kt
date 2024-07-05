package project.c14210052_c14210182.proyekakhir_paba.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.c14210052_c14210182.proyekakhir_paba.R
import project.c14210052_c14210182.proyekakhir_paba.dataClass.detailPenjualan

class adapterDetailPenjualan(private val detailPenjualan: detailPenjualan) : RecyclerView.Adapter<adapterDetailPenjualan.DetailPenjualanViewHolder>() {

    class DetailPenjualanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaProduk: TextView = itemView.findViewById(R.id.tvItemNamaProduk)
        val jumlah: TextView = itemView.findViewById(R.id.tvItemJumlah)
        val harga: TextView = itemView.findViewById(R.id.tvItemHarga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailPenjualanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_penjualan, parent, false)
        return DetailPenjualanViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailPenjualanViewHolder, position: Int) {
        holder.namaProduk.text = detailPenjualan.namaProduk?.get(position) ?: "N/A"
        holder.jumlah.text = detailPenjualan.jumlah?.get(position) ?: "N/A"
        holder.harga.text = detailPenjualan.hargaPerProduk?.get(position) ?: "N/A"
    }

    override fun getItemCount(): Int {
        return detailPenjualan.namaProduk?.size ?: 0
    }
}
