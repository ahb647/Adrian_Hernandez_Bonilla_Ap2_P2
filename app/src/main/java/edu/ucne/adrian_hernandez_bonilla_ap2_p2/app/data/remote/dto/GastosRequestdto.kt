package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto




import kotlinx.serialization.Serializable

@Serializable
data class GastosRequestdto(

    val fecha: String? = null,
    val suplidor: String,
    val ncf: String,
    val itbis: Double? = null,
    val monto: Double? = null




)
