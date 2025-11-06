package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.domain.useCase.GastouseCase


import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.domain.repository.GastosRepository
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto.GastosResponsedto

import javax.inject.Inject

class ObtenerGastosuseCase @Inject constructor(
    private val gastosRepository: GastosRepository
) {
    suspend operator fun invoke(): Result<List<GastosResponsedto>> {
        return gastosRepository.getAllGastos()
    }
}