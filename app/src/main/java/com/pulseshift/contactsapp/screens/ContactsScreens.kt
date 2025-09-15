package com.pulseshift.contactsapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pulseshift.contactsapp.model.Contact
import com.pulseshift.contactsapp.viewmodel.ContactsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    onClickAdd: () -> Unit,
    onClickContact: (Int) -> Unit, // ✅ Accept click handler
    viewModel: ContactsViewModel = viewModel()
) {
    viewModel.getContacts()
    val contacts by viewModel.contacts.observeAsState(emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "My contacts") }) },
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
                    Card(
                        onClick = { onClickContact(contact.contactId) }, // ✅ Use it here
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.AccountCircle,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(Modifier.width(16.dp))
                            Column {
                                Text("Name: ${contact.name}")
                                Text("Phone: ${contact.phoneNumber}")
                                if (contact.email.isNotBlank()) {
                                    Text("Email: ${contact.email}")
                                }
                                if (!contact.imageUrl.isNullOrBlank()) {
                                    Text("Image: ${contact.imageUrl}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}