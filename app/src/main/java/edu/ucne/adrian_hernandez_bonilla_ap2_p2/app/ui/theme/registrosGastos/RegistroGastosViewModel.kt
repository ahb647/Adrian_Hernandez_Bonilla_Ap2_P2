package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.ui.theme.registrosGastos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto.GastosRequestdto
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto.GastosResponsedto
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.domain.useCase.GastouseCase.GuardarGastouseCase
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.domain.useCase.GastouseCase.ObtenerGastosuseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistroGastosViewModel @Inject constructor(
    private val guardarGastoUseCase: GuardarGastouseCase,
    private val obtenerGastosUseCase: ObtenerGastosuseCase
) : ViewModel() {

    private val _gastos = MutableStateFlow<List<GastosResponsedto>>(emptyList())
    val gastos: StateFlow<List<GastosResponsedto>> get() = _gastos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun loadGastos() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = obtenerGastosUseCase()
            _gastos.value = result.getOrElse { emptyList() }
            _isLoading.value = false
        }
    }

    fun saveGasto(gasto: GastosRequestdto, gastoId: Int? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = guardarGastoUseCase(gastoId, gasto)
            if (result.isSuccess) loadGastos()
            _isLoading.value = false
        }
    }
}
