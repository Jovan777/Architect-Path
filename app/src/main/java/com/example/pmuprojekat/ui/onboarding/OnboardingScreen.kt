package com.example.pmuprojekat.ui.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.core.model.TaskFocusPreference
import com.example.pmuprojekat.core.model.TaskFormatPreference
import com.example.pmuprojekat.core.model.TaskPersonalizer
import com.example.pmuprojekat.ui.common.readableOutlinedTextFieldColors
import com.example.pmuprojekat.ui.home.AppPalette
import com.example.pmuprojekat.ui.home.HomeUiState

@Composable
fun OnboardingScreen(
    uiState: HomeUiState,
    onComplete: (
        displayName: String,
        currentLevel: String,
        preferredTaskFormat: String,
        learningFocus: String
    ) -> Unit
) {
    var displayName by remember(uiState.userName) {
        mutableStateOf(uiState.userName.takeUnless { it == "Marko" } ?: "")
    }
    var selectedLevelId by remember(uiState.selectedLevel) {
        mutableStateOf(uiState.selectedLevel)
    }
    var selectedFormats by remember(uiState.preferredTaskFormat) {
        mutableStateOf(TaskPersonalizer.parsePreferredFormats(uiState.preferredTaskFormat))
    }
    var attemptedSubmit by remember {
        mutableStateOf(false)
    }

    val nameIsValid = displayName.trim().isNotBlank()
    val formatsAreValid = selectedFormats.isNotEmpty()
    val canSubmit = nameIsValid && formatsAreValid

    Scaffold(
        containerColor = AppPalette.Background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFFF8FAFC),
                            Color(0xFFEFF6FF),
                            Color.White
                        )
                    )
                )
                .padding(innerPadding)
                .padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
                .padding(top = 18.dp, bottom = 26.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OnboardingHero()

            OnboardingSectionCard(
                step = "1",
                title = "Dobrodošao",
                subtitle = "Podesi svoj profil učenja da ti zadaci budu bolje prilagođeni."
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = displayName,
                    onValueChange = {
                        displayName = it
                        attemptedSubmit = false
                    },
                    label = { Text("Kako želiš da te zovemo?") },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    isError = attemptedSubmit && !nameIsValid,
                    colors = readableOutlinedTextFieldColors()
                )

                if (attemptedSubmit && !nameIsValid) {
                    Text(
                        text = "Unesi ime da bismo sačuvali profil.",
                        color = Color(0xFFE11D48),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            OnboardingSectionCard(
                step = "2",
                title = "Proceni svoj nivo",
                subtitle = "Izaberi nivo koji ti trenutno najviše odgovara."
            ) {
                LearningLevel.entries.forEach { level ->
                    OnboardingLevelCard(
                        level = level,
                        selected = selectedLevelId == level.id,
                        onClick = { selectedLevelId = level.id }
                    )
                }
            }

            OnboardingSectionCard(
                step = "3",
                title = "Omiljeni tipovi zadataka",
                subtitle = "Izaberi jedan ili više formata koje želiš češće da vidiš."
            ) {
                TaskFormatPreference.entries.forEach { format ->
                    OnboardingFormatRow(
                        format = format,
                        selected = selectedFormats.contains(format),
                        onClick = {
                            selectedFormats = if (selectedFormats.contains(format)) {
                                selectedFormats - format
                            } else {
                                selectedFormats + format
                            }
                            attemptedSubmit = false
                        }
                    )
                }

                if (attemptedSubmit && !formatsAreValid) {
                    Text(
                        text = "Izaberi bar jedan tip zadataka.",
                        color = Color(0xFFE11D48),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Button(
                onClick = {
                    attemptedSubmit = true

                    if (canSubmit) {
                        onComplete(
                            displayName.trim(),
                            selectedLevelId,
                            TaskPersonalizer.encodePreferredFormats(selectedFormats),
                            TaskFocusPreference.BALANCED_LEARNING.displayName
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(22.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppPalette.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Započni učenje",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
private fun OnboardingHero() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = AppPalette.Navy),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(
                            AppPalette.Navy,
                            Color(0xFF1D4ED8),
                            Color(0xFF4F46E5)
                        )
                    )
                )
                .padding(22.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "PMU učenje",
                    color = Color(0xFFBFDBFE),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = "Napravi profil za zadatke koji imaju smisla za tebe.",
                    color = Color.White,
                    fontSize = 25.sp,
                    lineHeight = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = "Nivo i omiljeni tipovi zadataka postaće početni filteri u tabu Zadaci.",
                    color = Color(0xFFDCEBFE),
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )
            }
        }
    }
}

@Composable
private fun OnboardingSectionCard(
    step: String,
    title: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = AppPalette.Blue.copy(alpha = 0.12f),
                    border = BorderStroke(1.dp, AppPalette.Blue.copy(alpha = 0.22f))
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                        text = step,
                        color = AppPalette.Blue,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.width(11.dp))

                Column {
                    Text(
                        text = title,
                        color = AppPalette.TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = subtitle,
                        color = AppPalette.TextSecondary,
                        fontSize = 12.5.sp,
                        lineHeight = 18.sp
                    )
                }
            }

            content()
        }
    }
}

@Composable
private fun OnboardingLevelCard(
    level: LearningLevel,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = if (selected) AppPalette.Blue.copy(alpha = 0.1f) else Color(0xFFF8FAFC),
        border = BorderStroke(
            width = if (selected) 1.6.dp else 1.dp,
            color = if (selected) AppPalette.Blue else AppPalette.Border
        )
    ) {
        Row(
            modifier = Modifier.padding(13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(if (selected) AppPalette.Blue else Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = level.displayName.take(1).uppercase(),
                    color = if (selected) Color.White else AppPalette.TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.width(11.dp))

            Column {
                Text(
                    text = level.displayName,
                    color = AppPalette.TextPrimary,
                    fontSize = 14.5.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = levelOnboardingDescription(level),
                    color = AppPalette.TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }
        }
    }
}

@Composable
private fun OnboardingFormatRow(
    format: TaskFormatPreference,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        color = if (selected) AppPalette.Indigo.copy(alpha = 0.1f) else Color(0xFFF8FAFC),
        border = BorderStroke(
            width = if (selected) 1.6.dp else 1.dp,
            color = if (selected) AppPalette.Indigo else AppPalette.Border
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 13.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = if (selected) AppPalette.Indigo else Color.White,
                border = BorderStroke(1.dp, if (selected) AppPalette.Indigo else AppPalette.Border)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                    text = if (selected) "✓" else "",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = format.displayName,
                color = AppPalette.TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

private fun levelOnboardingDescription(level: LearningLevel): String {
    return when (level) {
        LearningLevel.BEGINNER -> "Učim osnove i želim postepeno vođenje."
        LearningLevel.JUNIOR -> "Razumem osnove, ali vežbam primenu."
        LearningLevel.MEDIOR -> "Želim složenije zadatke i obrasce."
        LearningLevel.SENIOR -> "Vežbam analizu posledica i tehničke odluke."
        LearningLevel.ARCHITECT -> "Želim sistemsko razmišljanje i arhitektonske odluke."
    }
}
