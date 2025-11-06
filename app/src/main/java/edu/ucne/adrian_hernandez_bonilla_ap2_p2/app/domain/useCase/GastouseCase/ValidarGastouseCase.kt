package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.domain.useCase.GastouseCase

import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto.GastosRequestdto
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.domain.repository.GastosRepository
import javax.inject.Inject

class ValidarGastouseCase @Inject constructor(
    private val repository: GastosRepository
) {
    suspend operator fun invoke(gasto: GastosRequestdto, currentId: Int? = null): Result<Unit> {
        if (gasto.suplidor.isBlank() || gasto.ncf.isBlank()) {
            return Result.failure(IllegalArgumentException("El suplidor o el NCF no pueden estar vacíos"))
        }
        val existentes = repository.getAllGastos().getOrNull().orEmpty()
        val ncfRepetido = existentes.any { it.ncf.equals(gasto.ncf, ignoreCase = true) && it.gastoId != currentId }
        return if (ncfRepetido) Result.failure(IllegalStateException("Ya existe un gasto con ese NCF")) else Result.success(Unit)
    }
}