package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.ui.theme.registrosGastos


import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto.GastosRequestdto


sealed class RegistroGastosEvent {

    data class SuplidorChanged(val value: String) : RegistroGastosEvent()
    data class NcfChanged(val value: String) : RegistroGastosEvent()
    data class FechaChanged(val value: String) : RegistroGastosEvent()
    data class ItbisChanged(val value: Double) : RegistroGastosEvent()
    data class MontoChanged(val value: Double) : RegistroGastosEvent()

    data class Save(val gasto: GastosRequestdto) : RegistroGastosEvent()
    data class Delete(val gasto: GastosRequestdto) : RegistroGastosEvent()
    data class Select(val gasto: GastosRequestdto) : RegistroGastosEvent()

    object LoadGastos : RegistroGastosEvent()

}