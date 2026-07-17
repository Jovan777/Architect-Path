package com.example.pmuprojekat.ui.common

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.pmuprojekat.ui.home.AppPalette

@Composable
fun readableOutlinedTextFieldColors(
    focusedBorderColor: Color = AppPalette.Blue,
    unfocusedBorderColor: Color = AppPalette.Border,
    focusedContainerColor: Color = Color.White,
    unfocusedContainerColor: Color = Color.White,
    disabledContainerColor: Color = Color.White,
    errorBorderColor: Color = Color(0xFFE11D48),
    cursorColor: Color = AppPalette.TextPrimary,
    textColor: Color = AppPalette.TextPrimary,
    placeholderColor: Color = AppPalette.TextMuted,
    focusedLabelColor: Color? = null,
    unfocusedLabelColor: Color? = null,
    disabledLabelColor: Color? = null,
    errorLabelColor: Color? = null
) = OutlinedTextFieldDefaults.colors(
    focusedTextColor = textColor,
    unfocusedTextColor = textColor,
    disabledTextColor = textColor.copy(alpha = 0.62f),
    errorTextColor = textColor,
    cursorColor = cursorColor,
    errorCursorColor = errorBorderColor,
    focusedPlaceholderColor = placeholderColor,
    unfocusedPlaceholderColor = placeholderColor,
    disabledPlaceholderColor = placeholderColor.copy(alpha = 0.55f),
    errorPlaceholderColor = placeholderColor,
    focusedLabelColor = focusedLabelColor ?: Color.Unspecified,
    unfocusedLabelColor = unfocusedLabelColor ?: Color.Unspecified,
    disabledLabelColor = disabledLabelColor ?: Color.Unspecified,
    errorLabelColor = errorLabelColor ?: Color.Unspecified,
    focusedBorderColor = focusedBorderColor,
    unfocusedBorderColor = unfocusedBorderColor,
    disabledBorderColor = unfocusedBorderColor,
    errorBorderColor = errorBorderColor,
    focusedContainerColor = focusedContainerColor,
    unfocusedContainerColor = unfocusedContainerColor,
    disabledContainerColor = disabledContainerColor,
    errorContainerColor = focusedContainerColor
)
