import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.fromColorLong
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


/**
 * Template screen for visual consistency across
 * the entire app. This screen in particular will
 * not be directly used in the final product.
 *
 *
 * */

@Composable
fun InsertName(navController: NavController){
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {navController.popBackStack()},
                modifier = Modifier.testTag("backButton")){
                Text("Back")
            }
        }
    }

}