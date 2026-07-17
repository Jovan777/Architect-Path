package com.example.pmuprojekat.ui.main


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun SettingsScreen(
    uiState: HomeUiState,
    onBack: () -> Unit,
    onSaveProfile: (
        displayName: String,
        currentLevel: String,
        learningGoal: String,
        preferredTaskFormat: String,
        learningFocus: String,
        aiFollowUpEnabled: Boolean
    ) -> Unit,
    onResetProgress: () -> Unit
) {
    var displayName by remember(uiState.userName) {
        mutableStateOf(uiState.userName)
    }

    var learningGoal by remember(uiState.learningGoal) {
        mutableStateOf(uiState.learningGoal)
    }

    var selectedLevelId by remember(uiState.selectedLevel) {
        mutableStateOf(uiState.selectedLevel)
    }

    var preferredTaskFormats by remember(uiState.preferredTaskFormat) {
        mutableStateOf(
            TaskPersonalizer.parsePreferredFormats(uiState.preferredTaskFormat)
                .map { it.displayName }
                .toSet()
        )
    }

    var learningFocus by remember(uiState.learningFocus) {
        mutableStateOf(uiState.learningFocus)
    }

    var aiFollowUpEnabled by remember(uiState.aiFollowUpEnabled) {
        mutableStateOf(uiState.aiFollowUpEnabled)
    }

    var showResetDialog by remember {
        mutableStateOf(false)
    }

    var savedMessageVisible by remember {
        mutableStateOf(false)
    }

    Scaffold(
        containerColor = AppPalette.Background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF8FAFC),
                            Color(0xFFF1F5F9),
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
                .padding(top = 12.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsHeader(onBack = onBack)

            SettingsProfileCard(
                displayName = displayName,
                onDisplayNameChange = {
                    displayName = it
                    savedMessageVisible = false
                }
            )

            SettingsLearningCard(
                selectedLevelId = selectedLevelId,
                onSelectedLevelChange = {
                    selectedLevelId = it
                    savedMessageVisible = false
                },
                learningGoal = learningGoal,
                onLearningGoalChange = {
                    learningGoal = it
                    savedMessageVisible = false
                },
                preferredTaskFormat = preferredTaskFormats,
                onPreferredTaskFormatChange = {
                    preferredTaskFormats = it
                    savedMessageVisible = false
                },
                learningFocus = learningFocus,
                onLearningFocusChange = {
                    learningFocus = it
                    savedMessageVisible = false
                },
                aiFollowUpEnabled = aiFollowUpEnabled,
                onAiFollowUpChange = {
                    aiFollowUpEnabled = it
                    savedMessageVisible = false
                }
            )

            Button(
                onClick = {
                    val safeName = displayName.trim().ifBlank { "Korisnik" }

                    onSaveProfile(
                        safeName,
                        selectedLevelId,
                        learningGoal,
                        encodePreferredTaskFormats(preferredTaskFormats),
                        learningFocus,
                        aiFollowUpEnabled
                    )

                    displayName = safeName
                    savedMessageVisible = true
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppPalette.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Sačuvaj podešavanja",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (savedMessageVisible) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0xFFECFDF5),
                    border = BorderStroke(1.dp, Color(0xFFBBF7D0))
                ) {
                    Text(
                        modifier = Modifier.padding(14.dp),
                        text = "Podešavanja su sačuvana.",
                        color = AppPalette.Green,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            DangerZoneCard(
                onResetClick = {
                    showResetDialog = true
                }
            )
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = {
                showResetDialog = false
            },
            title = {
                Text(
                    text = "Resetovanje progresa",
                    fontWeight = FontWeight.ExtraBold
                )
            },
            text = {
                Text(
                    text = "Da li si siguran da želiš da resetuješ progres? Biće obrisani svi sačuvani odgovori, rešeni zadaci, XP i statistika napretka. Ovo ne menja tvoja profilna podešavanja.",
                    lineHeight = 20.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onResetProgress()
                        showResetDialog = false
                    }
                ) {
                    Text(
                        text = "Da, resetuj",
                        color = Color(0xFFE11D48),
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showResetDialog = false
                    }
                ) {
                    Text("Odustani")
                }
            }
        )
    }
}

@Composable
private fun SettingsHeader(
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .clickable { onBack() },
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            shadowElevation = 6.dp,
            border = BorderStroke(1.dp, AppPalette.Border)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                text = "‹",
                color = AppPalette.TextPrimary,
                fontSize = 30.sp,
                fontWeight = FontWeight.Light
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = "Podešavanja",
                color = AppPalette.TextPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "Uredi profil, cilj učenja i način rada.",
                color = AppPalette.TextSecondary,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun SettingsProfileCard(
    displayName: String,
    onDisplayNameChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xFF60A5FA),
                                    Color(0xFFA78BFA)
                                )
                            )
                        )
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = displayName.trim().ifBlank { "K" }.take(1).uppercase(),
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "Profil korisnika",
                        color = AppPalette.TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "Ime se prikazuje na početnoj i profilnoj strani.",
                        color = AppPalette.TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = displayName,
                onValueChange = onDisplayNameChange,
                label = {
                    Text("Ime korisnika")
                },
                singleLine = true,
                shape = RoundedCornerShape(18.dp),
                colors = readableOutlinedTextFieldColors()
            )
        }
    }
}

@Composable
private fun SettingsLearningCard(
    selectedLevelId: String,
    onSelectedLevelChange: (String) -> Unit,
    learningGoal: String,
    onLearningGoalChange: (String) -> Unit,
    preferredTaskFormat: Set<String>,
    onPreferredTaskFormatChange: (Set<String>) -> Unit,
    learningFocus: String,
    onLearningFocusChange: (String) -> Unit,
    aiFollowUpEnabled: Boolean,
    onAiFollowUpChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Podešavanja učenja",
                color = AppPalette.TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )

            SettingsDropdown(
                title = "Moj nivo",
                value = LearningLevel.fromId(selectedLevelId).displayName,
                options = LearningLevel.entries.map { it.displayName },
                onValueSelected = { selectedName ->
                    LearningLevel.entries
                        .firstOrNull { it.displayName == selectedName }
                        ?.let { onSelectedLevelChange(it.id) }
                }
            )

            SettingsDropdown(
                title = "Cilj učenja",
                value = learningGoal,
                options = listOf(
                    "1 zadatak dnevno",
                    "1–2 zadatka dnevno",
                    "1 talas nedeljno",
                    "Brzi napredak",
                    "Priprema za ispit"
                ),
                onValueSelected = onLearningGoalChange
            )

            SettingsMultiSelectChips(
                title = "Preferirani format",
                options = listOf(
                    TaskFormatPreference.INTERACTIVE_STEPS.displayName,
                    TaskFormatPreference.QUIZ_QUESTIONS.displayName,
                    TaskFormatPreference.CODE_PSEUDOCODE.displayName,
                    TaskFormatPreference.MAPPING_CARDS.displayName,
                    TaskFormatPreference.ARCHITECTURAL_SCENARIOS.displayName
                ),
                selected = preferredTaskFormat,
                onSelectedChange = onPreferredTaskFormatChange
            )

            SettingsDropdown(
                title = "Fokus učenja",
                value = learningFocus,
                options = listOf(
                    TaskFocusPreference.BALANCED_LEARNING.displayName,
                    TaskFocusPreference.DESIGN_PATTERNS.displayName,
                    TaskFocusPreference.REFACTORING.displayName,
                    TaskFocusPreference.PRODUCTION_THINKING.displayName,
                    TaskFocusPreference.ARCHITECTURAL_DECISION_MAKING.displayName
                ),
                onValueSelected = onLearningFocusChange
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                color = Color(0xFFF8FAFC),
                border = BorderStroke(1.dp, AppPalette.Border)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "AI follow-up pitanja",
                            color = AppPalette.TextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Text(
                            text = "Prikaz dodatnog pitanja nakon završenog zadatka.",
                            color = AppPalette.TextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )
                    }

                    Switch(
                        checked = aiFollowUpEnabled,
                        onCheckedChange = onAiFollowUpChange
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsDropdown(
    title: String,
    value: String,
    options: List<String>,
    onValueSelected: (String) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(
            text = title,
            color = AppPalette.TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Box {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFF8FAFC),
                border = BorderStroke(1.dp, AppPalette.Border)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 13.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = value,
                        color = AppPalette.TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "⌄",
                        color = AppPalette.TextSecondary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(option)
                        },
                        onClick = {
                            onValueSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsMultiSelectChips(
    title: String,
    options: List<String>,
    selected: Set<String>,
    onSelectedChange: (Set<String>) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            color = AppPalette.TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold
        )

        options.forEach { option ->
            val isSelected = selected.contains(option)

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val updated = if (isSelected) {
                            selected - option
                        } else {
                            selected + option
                        }

                        onSelectedChange(
                            updated.ifEmpty { setOf(option) }
                        )
                    },
                shape = RoundedCornerShape(18.dp),
                color = if (isSelected) AppPalette.Blue.copy(alpha = 0.12f) else Color(0xFFF8FAFC),
                border = BorderStroke(
                    width = if (isSelected) 1.5.dp else 1.dp,
                    color = if (isSelected) AppPalette.Blue else AppPalette.Border
                )
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 13.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = if (isSelected) AppPalette.Blue else Color.White,
                        border = BorderStroke(1.dp, if (isSelected) AppPalette.Blue else AppPalette.Border)
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            text = if (isSelected) "✓" else "",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = option,
                        color = AppPalette.TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

private fun encodePreferredTaskFormats(selected: Set<String>): String {
    val formats = TaskFormatPreference.entries
        .filter { selected.contains(it.displayName) }
        .toSet()

    return TaskPersonalizer.encodePreferredFormats(formats)
}

@Composable
private fun DangerZoneCard(
    onResetClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.dp, Color(0xFFFECACA))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Resetovanje napretka",
                color = Color(0xFFE11D48),
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "Resetovanje napretka briše rešene zadatke, odgovore, XP i statistiku. Pitanja i podešavanja profila ostaju sačuvani.",
                color = AppPalette.TextSecondary,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )

            Button(
                onClick = onResetClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE11D48),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Resetuj napredak",
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}
