package project.c14210052_c14210182.proyekakhir_paba.dataClass

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

data class detailPenjualan(
    var id: String? = "",
    val tanggal: String? = "",
    val waktu: String? = "",
    val namaProduk: ArrayList<String>? = ArrayList(),
    val hargaPerProduk: ArrayList<String>? = ArrayList(),
    val jumlah: ArrayList<String>? = ArrayList(),
    val total: Int = 0,
    val bayar: Int = 0,
    val kembalian: Int = 0
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(tanggal)
        parcel.writeString(waktu)
        parcel.writeStringList(namaProduk)
        parcel.writeStringList(hargaPerProduk)
        parcel.writeStringList(jumlah)
        parcel.writeInt(total)
        parcel.writeInt(bayar)
        parcel.writeInt(kembalian)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<detailPenjualan> {
        override fun createFromParcel(parcel: Parcel): detailPenjualan {
            return detailPenjualan(parcel)
        }

        override fun newArray(size: Int): Array<detailPenjualan?> {
            return arrayOfNulls(size)
        }
    }
}
