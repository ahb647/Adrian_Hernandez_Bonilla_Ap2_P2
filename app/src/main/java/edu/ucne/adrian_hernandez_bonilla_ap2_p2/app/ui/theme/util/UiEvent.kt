package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.ui.theme.util



sealed class  UiEvent {
    data class ShowMessage(val message: String) : UiEvent()
    object NavigateBack : UiEvent()
    data class NavigateTo(val route: String) : UiEvent()


}