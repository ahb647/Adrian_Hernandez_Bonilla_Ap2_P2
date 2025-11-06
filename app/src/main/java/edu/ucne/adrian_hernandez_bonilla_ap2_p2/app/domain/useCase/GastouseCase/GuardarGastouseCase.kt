package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.domain.useCase.GastouseCase

import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto.GastosRequestdto
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto.GastosResponsedto
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.domain.repository.GastosRepository
import javax.inject.Inject

class GuardarGastouseCase @Inject constructor(
    private val repository: GastosRepository,
    private val validarGasto: ValidarGastouseCase
) {
    suspend operator fun invoke(gastoId: Int?, gasto: GastosRequestdto): Result<Unit> {
        val validacion = validarGasto(gasto, gastoId)
        if (validacion.isFailure) return Result.failure(validacion.exceptionOrNull()!!)
        val model = GastosResponsedto(
            gastoId = gastoId,
            fecha = gasto.fecha,
            suplidor = gasto.suplidor,
            ncf = gasto.ncf,
            itbis = gasto.itbis,
            monto = gasto.monto
        )
        return try {
            if (gastoId == null || gastoId == 0) {
                repository.insertGasto(model)
            } else {
                repository.updateGasto(model)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}