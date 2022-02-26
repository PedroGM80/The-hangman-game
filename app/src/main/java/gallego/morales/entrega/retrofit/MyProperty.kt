package gallego.morales.entrega.retrofit
data class MyProperty(
    val id: String,
    val title: String
) {
    override fun toString(): String {
        return title
    }
}