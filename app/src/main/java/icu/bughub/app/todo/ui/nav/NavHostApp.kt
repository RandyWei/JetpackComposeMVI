package icu.bughub.app.todo.ui.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import icu.bughub.app.todo.ui.screens.EditorScreen
import icu.bughub.app.todo.ui.screens.ListScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavHostApp() {

    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navController, startDestination = Destinations.List.route) {

        //列表页
        composable(Destinations.List.route) {
            ListScreen {
                navController.navigate(Destinations.Editor(it).route)
            }
        }

        //详情或编辑页
        composable(Destinations.Editor("{${Params.Id.key}}").route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(Params.Id.key) ?: ""
            EditorScreen(id) {
                navController.popBackStack()
            }
        }

    }


}

sealed class Destinations(val route: String) {

    //列表页
    object List : Destinations("List")

    //详情页
    data class Editor(val id: String) : Destinations("Editor/$id")
}

sealed class Params(val key: String) {
    object Id : Params("id")
}