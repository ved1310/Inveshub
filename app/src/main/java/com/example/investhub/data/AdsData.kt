package com.example.investhub.data

import android.media.Image
import android.provider.ContactsContract.CommonDataKinds.Email

data class AdsData(
    val shopName:String,
    val ownerName: String,
    val location: String,
    val mobile:String,
    val email: String,
    val image:String

)
