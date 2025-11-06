package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.ui.theme.registrosGastos


import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto.GastosRequestdto

data class RegistroGastosState(


    val isLoading: Boolean = false,
    val registrosGastos: List<GastosRequestdto> = emptyList(),
    val error: String? = null
)
