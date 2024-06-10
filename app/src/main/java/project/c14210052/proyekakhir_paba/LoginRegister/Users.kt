package project.c14210052.proyekakhir_paba.LoginRegister

import android.os.Parcel
import android.os.Parcelable

data class Users(
    val idUsers: String?,
    val fullname: String?,
    val email: String?,
    val password: String?,
    val status: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idUsers)
        parcel.writeString(fullname)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Users> {
        override fun createFromParcel(parcel: Parcel): Users {
            return Users(parcel)
        }

        override fun newArray(size: Int): Array<Users?> {
            return arrayOfNulls(size)
        }
    }
}
