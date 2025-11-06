package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.domain.useCase.GastouseCase

data class GastosuseCase(


    val validarGasto: ValidarGastouseCase,
    val guardarGasto: GuardarGastouseCase,
    val obtenerGasto: ObtenerGastouseCase,
    val obtenerGastos: ObtenerGastosuseCase,
)
