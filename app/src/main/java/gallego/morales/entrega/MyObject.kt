package gallego.morales.entrega

object MyObject {
    val retrofitService: DataApiService by lazy { retrofit.create(DataApiService::class.java) }
}