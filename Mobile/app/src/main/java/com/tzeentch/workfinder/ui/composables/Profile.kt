package com.tzeentch.workfinder.ui.composables

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tzeentch.workfinder.local.FileHelper
import com.tzeentch.workfinder.ui.ProfileScreenStates
import com.tzeentch.workfinder.ui.composables.components.loader.Loader
import com.tzeentch.workfinder.viewModels.MainViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: MainViewModel) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getUserData()
    }

    val context = LocalContext.current
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            val file = FileHelper().convertUriToFile(uri, context)
            if (file != null) {
                viewModel.uploadPhoto(photo = file.readBytes())
            }
        }

    when (val res = viewModel.profileState.collectAsState().value) {
        is ProfileScreenStates.UserProfile -> {

            val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(res.url)) }
            Column {
                Text(text = "Профиль", fontSize = 23.sp, color = Color(0xFF284779))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }, modifier = Modifier
                            .height(55.dp)
                            .weight(1f)
                    ) {
                        Text(text = "Создать  CV")
                    }
                    if (res.url.isNotEmpty()) {
                        Button(
                            onClick = {
                                context.startActivity(intent)
                            }, modifier = Modifier
                                .height(55.dp)
                                .weight(1f)
                        ) {
                            Text(text = "Скачать  CV")
                        }
                    }
                }
            }
        }

        else -> {
            Loader()
        }
    }
}