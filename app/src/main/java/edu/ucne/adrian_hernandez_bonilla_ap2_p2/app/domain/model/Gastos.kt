package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.domain.model

import androidx.room.Dao

data class Gastos(


    val gastosId: Int,
    val fecha: String,
    val suplidor: String,
    val ncf : String,
    val itbs:  Int,
    val monto : Double,
)
