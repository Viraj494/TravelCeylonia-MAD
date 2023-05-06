package com.mad.travelceylonia

import android.os.Parcel
import android.os.Parcelable

data class ManageHotelBookingData(var id: String, var hotel:String, var fromDate : String, var toDate : String, var costPerDay : Int, var noOfDays :Int, var noOfRooms:Int ,var totalCost: Int, var isPaid : String, var bookedBy : String):
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(hotel)
        parcel.writeString(fromDate)
        parcel.writeString(toDate)
        parcel.writeInt(costPerDay)
        parcel.writeInt(noOfDays)
        parcel.writeInt(noOfRooms)
        parcel.writeInt(totalCost)
        parcel.writeString(isPaid)
        parcel.writeString(bookedBy)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ManageHotelBookingData> {
        override fun createFromParcel(parcel: Parcel): ManageHotelBookingData {
            return ManageHotelBookingData(parcel)
        }

        override fun newArray(size: Int): Array<ManageHotelBookingData?> {
            return arrayOfNulls(size)
        }
    }
}
