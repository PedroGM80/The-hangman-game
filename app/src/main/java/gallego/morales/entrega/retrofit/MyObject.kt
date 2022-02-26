package gallego.morales.entrega.retrofit

object MyObject {
    val retrofitService: DataApiService by lazy { retrofit.create(DataApiService::class.java) }
}