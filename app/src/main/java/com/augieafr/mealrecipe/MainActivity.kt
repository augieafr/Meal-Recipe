package com.augieafr.mealrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.augieafr.mealrecipe.ui.home_screen.HomeScreen
import com.augieafr.mealrecipe.ui.theme.MealRecipeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealRecipeTheme {
                // A surface container using the 'background' color from the theme
                HomeScreen(modifier = Modifier.fillMaxSize(), navigateToDetail = { /*TODO*/ }) {

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MealRecipeTheme {
        Greeting("Android")
    }
}