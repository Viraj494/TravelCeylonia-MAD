package com.mad.travelceylonia

import android.os.Parcel
import android.os.Parcelable

data class DataClass(var id:String , var dataImage:Int, var dataTitle:String, var dataDesc: String, var dataDetailImage: Int,var dataLoc: String, var dataFuel:String, var dataSeats:String , var dataCost:Int):
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(dataImage)
        parcel.writeString(dataTitle)
        parcel.writeString(dataDesc)
        parcel.writeInt(dataDetailImage)
        parcel.writeString(dataLoc)
        parcel.writeString(dataFuel)
        parcel.writeString(dataSeats)
        parcel.writeInt(dataCost)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataClass> {
        override fun createFromParcel(parcel: Parcel): DataClass {
            return DataClass(parcel)
        }

        override fun newArray(size: Int): Array<DataClass?> {
            return arrayOfNulls(size)
        }
    }
}
