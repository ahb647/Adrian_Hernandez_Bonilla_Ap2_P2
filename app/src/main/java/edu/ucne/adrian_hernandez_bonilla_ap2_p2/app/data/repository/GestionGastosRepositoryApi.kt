package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.repository

import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.GestionGastosApi
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto.GastosRequestdto
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto.GastosResponsedto
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.remoteDataResource.Resource
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.domain.repository.GastosRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class GestionGastosRepositoryApi @Inject constructor(
    private val api: GestionGastosApi
) : GastosRepository {

    private suspend fun <T> call(block: suspend () -> retrofit2.Response<T>): Result<T?> = try {
        val r = block()
        if (r.isSuccessful) Result.success(r.body()) else Result.failure(IllegalStateException("HTTP ${r.code()}"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getAllGastos(): Result<List<GastosResponsedto>> = runBlocking {
        withContext(Dispatchers.IO) {
            call { api.obtenerGastos() }.map { it ?: emptyList() }
        }
    }

    override suspend fun getGastoById(id: Int): GastosResponsedto? =
        call { api.obtenerGastoPorId(id) }.getOrNull()

    override suspend fun insertGasto(gasto: GastosResponsedto) {
        val req = GastosRequestdto(
            fecha = gasto.fecha,
            suplidor = gasto.suplidor,
            ncf = gasto.ncf,
            itbis = gasto.itbis ?: 0.0,
            monto = gasto.monto ?: 0.0
        )
        call { api.crearGasto(req) }.getOrThrow()
    }

    override suspend fun deleteGasto(gasto: GastosResponsedto) {
        throw UnsupportedOperationException("DELETE no disponible en la API")
    }

    override suspend fun updateGasto(gasto: GastosResponsedto) {
        val id = requireNotNull(gasto.gastoId)
        val req = GastosRequestdto(
            fecha = gasto.fecha,
            suplidor = gasto.suplidor,
            ncf = gasto.ncf,
            itbis = gasto.itbis ?: 0.0,
            monto = gasto.monto ?: 0.0
        )
        call { api.actualizarGasto(id, req) }.getOrThrow()
    }

    override suspend fun syncGastos(): Resource<Unit> = Resource.Success(Unit)
}
