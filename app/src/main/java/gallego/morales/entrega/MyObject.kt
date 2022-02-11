package com.example.android.marsrealestate.network

import gallego.morales.entrega.DataApiService
import gallego.morales.entrega.retrofit

object MyObject {
    val retrofitService: DataApiService by lazy { retrofit.create(DataApiService::class.java) }
}