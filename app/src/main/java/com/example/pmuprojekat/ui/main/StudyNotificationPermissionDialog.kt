package com.example.pmuprojekat.ui.main

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun StudyNotificationPermissionDialog(
    onAllow: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Podsetnici za učenje",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = "Dozvoli obaveštenja kako bismo te najviše jednom dnevno podsetili da sačuvaš niz i ispuniš cilj učenja."
            )
        },
        confirmButton = {
            TextButton(onClick = onAllow) {
                Text("Dozvoli")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Ne sada")
            }
        }
    )
}
