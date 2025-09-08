package com.pulseshift.contactsapp.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object Contacts : Screen("contacts")
    object AddContact : Screen("addContact")
}
@Composable
fun AppNavigation() {
    val nav = rememberNavController()
    NavHost(nav, startDestination = Screen.Contacts.route) {
        composable(Screen.Contacts.route) {
            ContactsScreen(onClickAdd = { nav.navigate(Screen.AddContact.route) })
        }
        composable(Screen.AddContact.route) {
            AddContactScreen(onClickBack = { nav.popBackStack() })
        }
    }
}