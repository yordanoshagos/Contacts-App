package com.pulseshift.contactsapp.screens

import android.Manifest
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.autofill.ContentDataType.Companion.Date
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.pulseshift.contactsapp.viewmodel.ContactsViewModel
import java.io.File
import java.util.Date
import java.util.Locale
import kotlin.Result.Companion.success

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ContactDetailsScreen(
    contactId: Int,
    navController: NavController,
    viewModel: ContactsViewModel = viewModel()
) {
    val ctx = LocalContext.current
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }
    var cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()

    ){ success ->
        if(success && tempPhotoUri!=null){
            photoUri = tempPhotoUri
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){isGranted ->
        if (isGranted){
            tempPhotoUri = createImageUri(ctx)
            tempPhotoUri?.let { uri ->
                cameraLauncher.launch(uri)
            }
        }


    }

    LaunchedEffect(Unit) {
        viewModel.getContactById(contactId)
    }

    val contact by viewModel.contactLiveData.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(34.dp))

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(16.dp))

            contact?.let {
                Text(text = it.name, style = MaterialTheme.typography.titleLarge)
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
}

fun createImageUri( context: Context): Uri?{
    return try {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss",
            Locale.getDefault()).format(Date())

        val fileName = "JPEG_$timestamp"
        val storageDIr = File(context.getExternalFilesDir(null), "Pictures")

        if(!storageDIr.exists()){
            storageDIr.mkdirs()
        }

        val imageFile = File.createTempFile(fileName, ".jpg", storageDIr)

        FileProvider.getUriForFile(context,
            "${context.packageName}.provider", imageFile
        )

    }catch (e: Exception){
        e.printStackTrace()
        null
    } as Uri?

}