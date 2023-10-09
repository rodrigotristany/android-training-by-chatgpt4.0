package com.rodrigotristany.androidtraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.rodrigotristany.androidtraining.data.local.LocalDataSource
import com.rodrigotristany.androidtraining.data.MoviesRepository
import com.rodrigotristany.androidtraining.data.remote.RemoteDataSource
import com.rodrigotristany.androidtraining.data.local.MoviesDatabase
import com.rodrigotristany.androidtraining.ui.screens.home.Home
import com.rodrigotristany.androidtraining.ui.theme.AndroidTrainingTheme

class MainActivity : ComponentActivity() {

    lateinit var db : MoviesDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            MoviesDatabase::class.java, "movies-db"
        ).build()

        val repository = MoviesRepository(
            LocalDataSource(db.moviesDao()),
            RemoteDataSource()
        )

        setContent {
            Home(repository)
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
    AndroidTrainingTheme {
        Greeting("Android")
    }
}