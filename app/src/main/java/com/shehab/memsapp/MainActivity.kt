package com.shehab.memsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shehab.memsapp.Instance.INSTANCE
import com.shehab.memsapp.Model.Meme
import com.shehab.memsapp.Screens.DetailsScreen
import com.shehab.memsapp.Screens.HomeScreen
import com.shehab.memsapp.ui.theme.MemsAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemsAppTheme {

                var MemesList by remember{
                    mutableStateOf(listOf<Meme>())
                }
                val scoop = rememberCoroutineScope()
                LaunchedEffect(key1=true) {

                    scoop.launch(Dispatchers.IO) {
                        val response= INSTANCE.RetrofitINSTANCE.getMemsList()
                        if (response.isSuccessful){
                            withContext(Dispatchers.Main){
                                MemesList=response.body()!!.data.memes


                            }

                        }

                    } }



                val navController= rememberNavController()
                NavHost(navController =navController , startDestination = "HomeScreen" ){
                    composable(route ="HomeScreen"){
                        HomeScreen(memeList = MemesList,navController=navController)

                    }
                    composable(route ="DetailsScreen?name={name}&url={url}",
                        arguments = listOf(navArgument(name = "name"){type=NavType.StringType},
                        navArgument(name="url"){type=NavType.StringType}
                        )){
                        DetailsScreen(name=it.arguments?.getString("name"),
                                url=it.arguments?.getString("url"))

                    }
                }
            }
        }
    }
}
