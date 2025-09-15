package com.pulseshift.contactsapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Arrangement
import com.pulseshift.contactsapp.viewmodel.ContactsViewModel

@Composable
fun ContactDetailsScreen(contactId: Int, viewModel: ContactsViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getContactById(contactId)
    }

    val contact by viewModel.contactLiveData.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "",
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        contact?.let {
            Text(text = it.name, style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            Text(text = "📞 ${it.phoneNumber}")
            if (it.email.isNotBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(text = "📧 ${it.email}")
            }
            if (!it.imageUrl.isNullOrBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(text = "🖼️ ${it.imageUrl}")
            }

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(imageVector = Icons.Default.Call, contentDescription = "Call")
                Icon(imageVector = Icons.Outlined.Send, contentDescription = "Message")
            }
        }
    }
}