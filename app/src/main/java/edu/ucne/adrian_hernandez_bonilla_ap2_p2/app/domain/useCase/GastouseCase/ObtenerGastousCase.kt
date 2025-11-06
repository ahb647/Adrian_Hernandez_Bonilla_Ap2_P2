package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.domain.useCase.GastouseCase

import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto.GastosResponsedto
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.domain.repository.GastosRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ObtenerGastouseCase @Inject constructor(
    private val repository: GastosRepository
) {
    suspend operator fun invoke(): Result<List<GastosResponsedto>> = withContext(Dispatchers.IO) {
        repository.getAllGastos()
    }
}
