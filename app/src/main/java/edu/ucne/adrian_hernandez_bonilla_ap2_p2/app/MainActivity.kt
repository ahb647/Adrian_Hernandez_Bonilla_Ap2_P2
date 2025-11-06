package edu.ucne.adrian_hernandez_bonilla_ap2_p2.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.ui.theme.Adrian_Hernandez_Bonilla_Ap2_P2Theme
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.ui.theme.registrosGastos.RegistroGastosScreen
import edu.ucne.adrian_hernandez_bonilla_ap2_p2.app.ui.theme.util.Route

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Adrian_Hernandez_Bonilla_Ap2_P2Theme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Gastos_List,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Route.Gastos_List) {
            RegistroGastosScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

    }
}
