package antonio.femxa.appfinal.ui.screens.tablero

sealed class TableroUiState {
    data object Waiting : TableroUiState()
    data class Fail(val failCount: Int) : TableroUiState()
    data class Correct(val palabra: List<List<Char?>>) : TableroUiState()
}
