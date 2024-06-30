package project.c14210052_c14210182.proyekakhir_paba.dataClass

data class detailPenjualan(
    val id: String,
    val tanggal: String,
    val waktu: String,
    val namaProduk: MutableList<String>,
    val hargaPerProduk: MutableList<String>,
    val jumlah: MutableList<String>,
    //total nya gk tau mau hitung manual atau di simpen ta
    val total: Int,
    val bayar: Int,
    val kembalian: Int
)
