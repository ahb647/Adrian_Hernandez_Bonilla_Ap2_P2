package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.ui.theme.registrosGastos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto.GastosRequestdto
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.data.remote.dto.GastosResponsedto
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroGastosScreen(
    viewModel: RegistroGastosViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val gastos by viewModel.gastos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editing by remember { mutableStateOf<GastosResponsedto?>(null) }

    LaunchedEffect(Unit) { viewModel.loadGastos() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Gastos") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            Surface(shape = RoundedCornerShape(10.dp)) {
                FilledIconButton(
                    onClick = {
                        editing = null
                        showDialog = true
                    },
                    modifier = Modifier.size(56.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (isLoading && gastos.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Cargando...") }
            } else if (gastos.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("No hay gastos registrados") }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(gastos) { g ->
                        GastoItem(
                            gasto = g,
                            onEdit = {
                                editing = g
                                showDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        val base = editing
        GastoDialog(
            title = if (base == null) "Nuevo gasto" else "Editar gasto",
            initialSuplidor = base?.suplidor.orEmpty(),
            initialNcf = base?.ncf.orEmpty(),
            initialItbis = base?.itbis?.toString().orEmpty(),
            initialMonto = base?.monto?.toString().orEmpty(),
            onDismiss = {
                editing = null
                showDialog = false
            },
            onConfirm = { suplidor, ncf, itbisText, montoText, fechaHoy ->
                val itbis = itbisText.toDoubleOrNull() ?: 0.0
                val monto = montoText.toDoubleOrNull() ?: 0.0
                val request = GastosRequestdto(
                    suplidor = suplidor.trim(),
                    ncf = ncf.trim(),
                    itbis = itbis,
                    monto = monto,
                    fecha = fechaHoy
                )
                val id = base?.gastoId
                viewModel.saveGasto(request, id)
                editing = null
                showDialog = false
            }
        )
    }
}

@Composable
fun GastoItem(
    gasto: GastosResponsedto,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(gasto.suplidor, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text("NCF: ${gasto.ncf}", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(4.dp))
            Text("Fecha: ${gasto.fecha ?: "-"}", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Divider()
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("ITBIS: ${gasto.itbis ?: 0.0}")
                Text("Monto: ${gasto.monto ?: 0.0}")
                Spacer(Modifier.weight(1f))
                TextButton(onClick = onEdit) { Text("Editar") }
            }
        }
    }
}

@Composable
fun GastoDialog(
    title: String,
    initialSuplidor: String,
    initialNcf: String,
    initialItbis: String,
    initialMonto: String,
    onDismiss: () -> Unit,
    onConfirm: (suplidor: String, ncf: String, itbis: String, monto: String, fechaHoy: String) -> Unit
) {
    var suplidor by remember { mutableStateOf(initialSuplidor) }
    var ncf by remember { mutableStateOf(initialNcf) }
    var itbis by remember { mutableStateOf(initialItbis) }
    var monto by remember { mutableStateOf(initialMonto) }
    val fechaHoy = remember {
        LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    val isValid = suplidor.isNotBlank() && ncf.isNotBlank() &&
            (itbis.toDoubleOrNull() ?: -1.0) >= 0.0 &&
            (monto.toDoubleOrNull() ?: -1.0) > 0.0

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = suplidor,
                    onValueChange = { suplidor = it },
                    label = { Text("Suplidor") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = ncf,
                    onValueChange = { ncf = it },
                    label = { Text("NCF") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = itbis,
                    onValueChange = {
                        if (it.matches(Regex("^\\d*\\.?\\d*$"))) itbis = it
                    },
                    label = { Text("ITBIS") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = monto,
                    onValueChange = {
                        if (it.matches(Regex("^\\d*\\.?\\d*$"))) monto = it
                    },
                    label = { Text("Monto") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = fechaHoy,
                    onValueChange = {},
                    label = { Text("Fecha (hoy)") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    enabled = false
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = isValid,
                onClick = { onConfirm(suplidor, ncf, itbis, monto, fechaHoy) }
            ) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
