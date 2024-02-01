package com.tatara.coursesapp

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tatara.coursesapp.ui.theme.CoursesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoursesAppTheme {

                // navigation system
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable(route = "home") {
                            HomeScreen(
                                onDetailsClick = { title ->
                                    navController.navigate("details/title=$title")
                                },
                                onAboutClick = {
                                    navController.navigate("about")
                                }
                            )
                        }
                        composable(route = "about") {
                            AboutScreen(onNavigateUp = { navController.popBackStack() })
                        }
                        composable(route = "details/title={title}",
                            arguments = listOf(
                                navArgument("title") {
                                    type = NavType.StringType
                                    nullable = true
                                }
                            ),
                        ) { backStackEntry ->
                            val arguments = requireNotNull(backStackEntry.arguments)
                            val title = arguments.getString("title")
                            if (title != null) {
                                DetailsScreen(
                                    title = title,
                                    onNavigateUp = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onDetailsClick: (title: String) -> Unit, onAboutClick: () -> Unit
) {
    Scaffold { padding ->
        LazyColumn(contentPadding = padding) {
            item {
                HomeAppBar(onAboutClick)
            }
            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
            items(allCourses) { item ->
                CourseCard(item, onClick = { onDetailsClick(item.title) })
            }
        }
    }
}

@Composable
private fun HomeAppBar(onAboutClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = "Courses", style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = onAboutClick) {
            Text(text = "About", fontSize = 24.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseCard(item: Courses, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Column {
            Image(
                painter = painterResource(id = item.thumbnail),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(text = item.title)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.body, maxLines = 1, style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            AppBar(title = "About", onNavigateUp)
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "This app is a demonstration about the navigation in android jetpack compose")
                Spacer(modifier = Modifier.height(20.dp))
                val udemy_link = LocalUriHandler.current
                Button(onClick = {
                    udemy_link.openUri(
                        "https://www.udemy.com/"
                    )
                }) {
                    Text(text = "See more courses")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String, onNavigateUp: () -> Unit) {
    TopAppBar(
        title = { Text(text = title, fontSize = 24.sp) },
        navigationIcon = {
            IconButton(onClick = { onNavigateUp() }) {
                Icon(Icons.Rounded.ArrowBack, contentDescription = "Go back")
            }
        },
        modifier = Modifier.padding(vertical = 10.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(title: String, onNavigateUp: () -> Unit) {
    // seraching for the correct course
    val course = allCourses.first { it.title == title }

    Scaffold { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            Row(
                modifier = Modifier.padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onNavigateUp() }) {
                    Icon(Icons.Rounded.ArrowBack, contentDescription = "Go back")
                }
            }
            Image(
                painterResource(id = course.thumbnail), contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = course.title, fontSize = 28.sp, lineHeight = 32.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = course.body,
                    Modifier.fillMaxSize(),
                    fontSize = 18.sp
                )
            }
        }
    }
}

