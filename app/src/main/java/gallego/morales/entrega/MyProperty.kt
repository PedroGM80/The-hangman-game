package gallego.morales.entrega
data class MyProperty(
    val id: String,
    val title: String
) {
    override fun toString(): String {
        return title
    }
}