package project.c14210052_c14210182.proyekakhir_paba.dataClass

import android.os.Parcel
import android.os.Parcelable

data class Supplier(
    var id: String? = "",
    val namaSupplier: String? = "",
    val emailSupplier: String? = "",
    val teleponSupplier: String? = "",
    val alamatSupplier: String? = "",
    val kotaSupplier: String? = "",
    val provinsiSupplier: String? = "",
    val kodeSupplier: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(namaSupplier)
        parcel.writeString(emailSupplier)
        parcel.writeString(teleponSupplier)
        parcel.writeString(alamatSupplier)
        parcel.writeString(kotaSupplier)
        parcel.writeString(provinsiSupplier)
        parcel.writeString(kodeSupplier)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Supplier> {
        override fun createFromParcel(parcel: Parcel): Supplier {
            return Supplier(parcel)
        }

        override fun newArray(size: Int): Array<Supplier?> {
            return arrayOfNulls(size)
        }
    }
}

//package project.c14210052.proyekakhir_paba.dataClass
//
//import android.os.Parcel
//import android.os.Parcelable
//
//data class Supplier(
//    var id: String?,
//    val namaSupplier: String?,
//    val emailSupplier: String?,
//    val teleponSupplier: String?,
//    val alamatSupplier: String?,
//    val kotaSupplier: String?,
//    val provinsiSupplier: String?,
//    val kodeSupplier: String?,
//) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString()
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(id)
//        parcel.writeString(namaSupplier)
//        parcel.writeString(emailSupplier)
//        parcel.writeString(teleponSupplier)
//        parcel.writeString(alamatSupplier)
//        parcel.writeString(kotaSupplier)
//        parcel.writeString(provinsiSupplier)
//        parcel.writeString(kodeSupplier)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<Supplier> {
//        override fun createFromParcel(parcel: Parcel): Supplier {
//            return Supplier(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Supplier?> {
//            return arrayOfNulls(size)
//        }
//    }
//}
