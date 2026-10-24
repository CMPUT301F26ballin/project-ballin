import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun projectBallinApp(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login"){
        composable("login") {LoginScreen(navController)}
    }
}