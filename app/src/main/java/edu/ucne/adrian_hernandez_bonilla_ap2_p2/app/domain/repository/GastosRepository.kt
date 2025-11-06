package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.domain.repository


import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto.GastosResponsedto
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.remoteDataResource.Resource
import kotlinx.coroutines.flow.Flow

interface GastosRepository {

    fun getAllGastos(): Result<List<GastosResponsedto>>

    suspend fun getGastoById(id: Int): GastosResponsedto?

    suspend fun insertGasto(gasto: GastosResponsedto)

    suspend fun deleteGasto(gasto: GastosResponsedto)

    suspend fun updateGasto(gasto: GastosResponsedto)

    suspend fun syncGastos(): Resource<Unit>
}