package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote

import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto.GastosRequestdto
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto.GastosResponsedto
import retrofit2.Response
import retrofit2.http.*


interface GestionGastosApi {

    @GET("/api/Gastos")
    suspend fun obtenerGastos(): Response<List<GastosResponsedto>>

    @GET("/api/Gastos/{id}")
    suspend fun obtenerGastoPorId(@Path("id") id: Int): Response<GastosResponsedto>

    @POST("/api/Gastos")
    suspend fun crearGasto(@Body gasto: GastosRequestdto): Response<GastosResponsedto>

    @PUT("/api/Gastos/{id}")
    suspend fun actualizarGasto(@Path("id") id: Int, @Body gasto: GastosRequestdto): Response<GastosResponsedto>
}




