package project.c14210052_c14210182.proyekakhir_paba.dataClass

import android.os.Parcel
import android.os.Parcelable

data class kategoriProduk(
    val idKategori: Int = 0,
    val namaKategori: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idKategori)
        parcel.writeString(namaKategori)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<kategoriProduk> {
        override fun createFromParcel(parcel: Parcel): kategoriProduk {
            return kategoriProduk(parcel)
        }

        override fun newArray(size: Int): Array<kategoriProduk?> {
            return arrayOfNulls(size)
        }
    }
}
