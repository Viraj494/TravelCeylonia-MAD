package com.mad.travelceylonia

data class HotelBookingData(var hotel:String, var fromDate : String, var toDate : String, var costPerDay : Int, var noOfDays :Int, var noOfRooms :Int, var totalCost: Int, var isPaid : String,var bookedBy : String)
