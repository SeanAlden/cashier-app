package project.c14210052_c14210182.proyekakhir_paba.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.c14210052_c14210182.proyekakhir_paba.R
import project.c14210052_c14210182.proyekakhir_paba.dataClass.DetailPenjualan

class adapterRiwayat(
    private val riwayatList: List<DetailPenjualan>,
    private val onClickListener: (DetailPenjualan) -> Unit
) : RecyclerView.Adapter<adapterRiwayat.RiwayatViewHolder>() {

    inner class RiwayatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ID: TextView = itemView.findViewById(R.id.tvIDRiwayatPenjualan)
        val tanggal: TextView = itemView.findViewById(R.id.tvTanggalRiwayatPenjualan)
        val total: TextView = itemView.findViewById(R.id.tvTotalRiwayatPenjualan)

        fun bind(detailPenjualan: DetailPenjualan) {
            ID.text = detailPenjualan.id
            tanggal.text = detailPenjualan.tanggal
            total.text = detailPenjualan.total.toString()

            itemView.setOnClickListener {
                onClickListener(detailPenjualan)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiwayatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat_penjualan, parent, false)
        return RiwayatViewHolder(view)
    }

    override fun onBindViewHolder(holder: RiwayatViewHolder, position: Int) {
        holder.bind(riwayatList[position])
    }

    override fun getItemCount(): Int = riwayatList.size
}
