package com.pulseshift.contactsapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pulseshift.contactsapp.model.Contact
import com.pulseshift.contactsapp.viewmodel.ContactsViewModel
import androidx.compose.foundation.lazy.items  // ← Important!
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContactsScreen(
    onClickAdd: () -> Unit,
    viewModel: ContactsViewModel = viewModel()
) {

    viewModel.getContacts()

    val contacts by viewModel.contacts.observeAsState(emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onClickAdd) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Contact")
            }
        }
    ) { padding ->
        if (contacts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No contacts yet. Tap + to add one.")
            }
        } else {
            LazyColumn(Modifier.padding(padding)) {
                items(contacts) { contact ->
                    Card(Modifier.fillMaxWidth().padding(8.dp)) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Name: ${contact.name}")
                            Text("Phone: ${contact.phoneNumber}")
                            if (contact.email.isNotBlank()) {
                                Text("Email: ${contact.email}")
                            }
                            if (contact.imageUrl != null && contact.imageUrl!!.isNotBlank()) {
                                Text("Image: ${contact.imageUrl}")
                            }
                        }
                    }
                }
            }
        }
    }
}