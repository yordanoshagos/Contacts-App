package com.pulseshift.contactsapp.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object Contacts : Screen("contacts")
    object AddContact : Screen("addContact")
    object ContactDetails : Screen("contactDetails/{contactId}") {
        fun createRoute(contactId: Int) = "contactDetails/$contactId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController() // ✅ Define navController

    NavHost(navController = navController, startDestination = Screen.Contacts.route) {
        composable(Screen.Contacts.route) {
            ContactsScreen(
                onClickAdd = { navController.navigate(Screen.AddContact.route) },
                onClickContact = { contactId ->
                    navController.navigate(Screen.ContactDetails.createRoute(contactId))
                }
            )
        }
        composable(Screen.AddContact.route) {
            AddContactScreen(onClickBack = { navController.popBackStack() })
        }
        composable(
            route = Screen.ContactDetails.route,
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val contactId = navBackStackEntry.arguments?.getInt("contactId") ?: 0
            ContactDetailsScreen(contactId)
        }
    }
}