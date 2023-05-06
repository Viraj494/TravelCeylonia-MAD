package com.mad.travelceylonia

import android.os.Parcel
import android.os.Parcelable

data class BookingDataManage(var id: String , var vehicle: String, var fromDate: String, var toDate : String, var notOfDates : Long, var totalCost : Long, var bookedBy : String,var cost: Int, var isPaid:String):
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(vehicle)
        parcel.writeString(fromDate)
        parcel.writeString(toDate)
        parcel.writeLong(notOfDates)
        parcel.writeLong(totalCost)
        parcel.writeString(bookedBy)
        parcel.writeInt(cost)
        parcel.writeString(isPaid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookingDataManage> {
        override fun createFromParcel(parcel: Parcel): BookingDataManage {
            return BookingDataManage(parcel)
        }

        override fun newArray(size: Int): Array<BookingDataManage?> {
            return arrayOfNulls(size)
        }
    }
}
