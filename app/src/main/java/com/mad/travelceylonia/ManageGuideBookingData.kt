package com.mad.travelceylonia

import android.os.Parcel
import android.os.Parcelable

data class ManageGuideBookingData(var id: String ,var guide:String, var fromDate : String, var toDate : String, var costPerDay : Int, var noOfDays :Int, var totalCost: Int, var isPaid : String, var bookedBy : String):
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(guide)
        parcel.writeString(fromDate)
        parcel.writeString(toDate)
        parcel.writeInt(costPerDay)
        parcel.writeInt(noOfDays)
        parcel.writeInt(totalCost)
        parcel.writeString(isPaid)
        parcel.writeString(bookedBy)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ManageGuideBookingData> {
        override fun createFromParcel(parcel: Parcel): ManageGuideBookingData {
            return ManageGuideBookingData(parcel)
        }

        override fun newArray(size: Int): Array<ManageGuideBookingData?> {
            return arrayOfNulls(size)
        }
    }
}
