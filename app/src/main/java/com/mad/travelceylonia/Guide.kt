package com.mad.travelceylonia

import android.os.Parcel
import android.os.Parcelable

data class Guide(var id:String, var name: String, var desc:String, var costPerDay:Int):
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(desc)
        parcel.writeInt(costPerDay)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Guide> {
        override fun createFromParcel(parcel: Parcel): Guide {
            return Guide(parcel)
        }

        override fun newArray(size: Int): Array<Guide?> {
            return arrayOfNulls(size)
        }
    }
}
