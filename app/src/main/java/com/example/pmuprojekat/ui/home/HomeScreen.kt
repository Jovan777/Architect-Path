package com.example.pmuprojekat.ui.home


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pmuprojekat.core.model.LearningLevel
import com.example.pmuprojekat.ui.theme.PMUProjekatTheme
import com.example.pmuprojekat.ui.main.MainBottomBar
import com.example.pmuprojekat.ui.main.MainTab

object AppPalette {
    val Navy = Color(0xFF0F172A)
    val TextPrimary = Color(0xFF101A3D)
    val TextSecondary = Color(0xFF64748B)
    val TextMuted = Color(0xFF94A3B8)

    val Blue = Color(0xFF2563EB)
    val Green = Color(0xFF10B981)
    val Orange = Color(0xFFF59E0B)
    val Purple = Color(0xFF8B5CF6)
    val Indigo = Color(0xFF6366F1)

    val Background = Color(0xFFF8FAFC)
    val Card = Color(0xFFFFFFFF)
    val Border = Color(0xFFE2E8F0)
}

private data class LevelVisualStyle(
    val accentColor: Color,
    val softColor: Color,
    val illustration: String
)

private data class TaskFormatUiModel(
    val label: String,
    val icon: String,
    val accentColor: Color
)

private val taskFormats = listOf(
    TaskFormatUiModel("Jedan tačan", "✓", AppPalette.Blue),
    TaskFormatUiModel("Više tačnih", "☑", AppPalette.Green),
    TaskFormatUiModel("Drag & Drop", "↕", AppPalette.Purple),
    TaskFormatUiModel("Kod", "</>", AppPalette.Green),
    TaskFormatUiModel("UML", "▣", AppPalette.Orange),
    TaskFormatUiModel("AI follow-up", "✦", AppPalette.Indigo)
)

@Composable
fun SoftwareDesignHomeScreen(
    uiState: HomeUiState,
    onLevelSelected: (String) -> Unit,
    onStartLearning: () -> Unit,
    onQuestionClick: (String) -> Unit,
    selectedTab: MainTab = MainTab.HOME,
    onBottomTabSelected: (MainTab) -> Unit = {}
) {
    Scaffold(
        containerColor = AppPalette.Background,
        bottomBar = {
            MainBottomBar(
                selectedTab = selectedTab,
                onTabSelected = onBottomTabSelected
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF8FAFC),
                            Color(0xFFF1F5F9),
                            Color(0xFFFFFFFF)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(
                        top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                    )
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
                    .padding(top = 12.dp, bottom = 28.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                HomeHeader(
                    userName = uiState.userName,
                    streakDays = uiState.streakDays
                )

                ActiveLearningCard(
                    title = uiState.activeCardTitle,
                    subtitle = uiState.activeCardSubtitle,
                    progressPercent = uiState.activeCardProgressPercent,
                    levelId = uiState.selectedLevel
                )

                SectionHeader(
                    title = "Izaberi nivo",
                    actionText = "${uiState.totalQuestions} zadataka u bazi"
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    uiState.levels.forEach { level ->
                        LevelCard(
                            level = level,
                            selected = level.levelId == uiState.selectedLevel,
                            onClick = { onLevelSelected(level.levelId) }
                        )
                    }
                }

                TaskFormatsSection()

                DashboardPreviewSection(uiState = uiState)

                RecommendedQuestionsSection(
                    questions = uiState.questionPreviews,
                    onQuestionClick = onQuestionClick
                )

                MotivationalCard(
                    selectedLevelName = uiState.selectedLevelName,
                    hasQuestions = uiState.questionPreviews.isNotEmpty(),
                    onStartLearning = onStartLearning
                )
            }
        }
    }
}

@Composable
private fun HomeHeader(
    userName: String,
    streakDays: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppLogo()

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Ćao, $userName 👋",
                color = AppPalette.TextPrimary,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Dizajn. Refaktor. Isporuči.",
                color = AppPalette.TextSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }

        StreakBadge(days = streakDays)
    }
}

@Composable
private fun AppLogo() {
    Box(
        modifier = Modifier
            .size(54.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        AppPalette.Navy,
                        Color(0xFF1E3A8A),
                        AppPalette.Purple
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "S",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
private fun StreakBadge(days: Int) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(18.dp),
        shadowElevation = 8.dp,
        tonalElevation = 2.dp,
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "🔥", fontSize = 18.sp)

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = days.toString(),
                color = AppPalette.TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
private fun ActiveLearningCard(
    title: String,
    subtitle: String,
    progressPercent: Int,
    levelId: String
) {
    val style = levelStyle(levelId)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = AppPalette.Card),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                MiniIconBox(
                    text = "≋",
                    accentColor = style.accentColor,
                    softColor = style.softColor
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        color = AppPalette.TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = subtitle,
                        color = AppPalette.TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = "$progressPercent%",
                    color = style.accentColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            ProgressLine(
                progress = progressPercent / 100f,
                color = style.accentColor,
                trackColor = Color(0xFFE2E8F0),
                height = 8.dp
            )
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    actionText: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title.uppercase(),
            color = AppPalette.TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.4.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        if (actionText != null) {
            Text(
                text = actionText,
                color = AppPalette.Blue,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun LevelCard(
    level: LevelSummaryUi,
    selected: Boolean,
    onClick: () -> Unit
) {
    val style = levelStyle(level.levelId)
    val borderColor = if (selected) style.accentColor else AppPalette.Border
    val elevation = if (selected) 10.dp else 5.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = AppPalette.Card),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        border = BorderStroke(
            width = if (selected) 1.5.dp else 1.dp,
            color = borderColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .height(68.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(style.accentColor)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                style.accentColor,
                                style.accentColor.copy(alpha = 0.72f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = level.number.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 17.sp
                )
            }

            Spacer(modifier = Modifier.width(13.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = level.title,
                    color = AppPalette.TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = level.description,
                    color = AppPalette.TextSecondary,
                    fontSize = 12.5.sp,
                    lineHeight = 17.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(9.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${level.topicCount} tipova",
                        color = AppPalette.TextMuted,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "  •  ",
                        color = AppPalette.TextMuted,
                        fontSize = 11.5.sp
                    )

                    Text(
                        text = "${level.questionCount} pitanja",
                        color = AppPalette.TextMuted,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            LevelIllustration(
                style = style
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "›",
                color = if (selected) style.accentColor else AppPalette.TextMuted,
                fontSize = 30.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
private fun LevelIllustration(
    style: LevelVisualStyle
) {
    Box(
        modifier = Modifier
            .size(62.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        style.accentColor.copy(alpha = 0.22f),
                        style.softColor
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = style.illustration,
            color = style.accentColor,
            fontSize = if (style.illustration == "</>") 17.sp else 28.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TaskFormatsSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionHeader(
            title = "Stilovi zadataka",
            actionText = "Fleksibilni engine"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            taskFormats.forEach { format ->
                TaskFormatChip(format = format)
            }
        }
    }
}

@Composable
private fun TaskFormatChip(format: TaskFormatUiModel) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        shadowElevation = 5.dp,
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 13.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(format.accentColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = format.icon,
                    color = format.accentColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = format.label,
                color = AppPalette.TextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun DashboardPreviewSection(
    uiState: HomeUiState
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionHeader(title = "Današnji pregled")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                modifier = Modifier.weight(1f),
                icon = "▤",
                title = uiState.totalQuestions.toString(),
                subtitle = "pitanja u bazi",
                accentColor = AppPalette.Blue
            )

            StatCard(
                modifier = Modifier.weight(1f),
                icon = "✓",
                title = uiState.completedQuestions.toString(),
                subtitle = "rešena pitanja",
                accentColor = AppPalette.Green
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                modifier = Modifier.weight(1f),
                icon = "◎",
                title = "${uiState.overallProgressPercent}%",
                subtitle = "ukupan napredak",
                accentColor = AppPalette.Purple
            )

            StatCard(
                modifier = Modifier.weight(1f),
                icon = "✦",
                title = uiState.xp.toString(),
                subtitle = "osvojeni XP",
                accentColor = AppPalette.Orange
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MiniPreviewCard(
                modifier = Modifier.weight(1f),
                title = "UML primer",
                accentColor = AppPalette.Orange
            ) {
                UmlPreview()
            }

            MiniPreviewCard(
                modifier = Modifier.weight(1f),
                title = "Kod primer",
                accentColor = AppPalette.Navy
            ) {
                CodePreview()
            }
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    icon: String,
    title: String,
    subtitle: String,
    accentColor: Color
) {
    Card(
        modifier = modifier.height(112.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    color = accentColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Column {
                Text(
                    text = title,
                    color = AppPalette.TextPrimary,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = subtitle,
                    color = AppPalette.TextSecondary,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun RecommendedQuestionsSection(
    questions: List<QuestionPreviewUi>,
    onQuestionClick: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionHeader(
            title = "Preporučeni zadaci",
            actionText = if (questions.isEmpty()) null else "Iz Room baze"
        )

        if (questions.isEmpty()) {
            EmptyQuestionsCard()
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                questions.forEach { question ->
                    QuestionPreviewCard(
                        question = question,
                        onClick = { onQuestionClick(question.questionId) }
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyQuestionsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Nema zadataka za ovaj nivo",
                color = AppPalette.TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Kada dodaš pitanja u seed bazu, pojaviće se ovde automatski.",
                color = AppPalette.TextSecondary,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }
    }
}

@Composable
private fun QuestionPreviewCard(
    question: QuestionPreviewUi,
    onClick: () -> Unit
) {
    val accent = difficultyColor(question.difficulty)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(accent.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = question.questionId,
                    color = accent,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = question.title,
                    color = AppPalette.TextPrimary,
                    fontSize = 14.5.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = question.typeLabel,
                        color = AppPalette.TextSecondary,
                        fontSize = 11.5.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "  •  ",
                        color = AppPalette.TextMuted,
                        fontSize = 11.5.sp
                    )

                    Text(
                        text = question.difficulty,
                        color = accent,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Text(
                text = "›",
                color = AppPalette.TextMuted,
                fontSize = 28.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
private fun MiniPreviewCard(
    modifier: Modifier = Modifier,
    title: String,
    accentColor: Color,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.height(176.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
        ) {
            Text(
                text = title,
                color = AppPalette.TextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(18.dp))
                    .background(accentColor.copy(alpha = 0.07f)),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
}

@Composable
private fun UmlPreview() {
    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UmlBox("User")
            UmlBox("Order")
        }

        Text(
            text = "↓       ↓",
            color = AppPalette.TextMuted,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        UmlBox("Item")
    }
}

@Composable
private fun UmlBox(text: String) {
    Box(
        modifier = Modifier
            .width(58.dp)
            .height(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = AppPalette.TextPrimary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun CodePreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppPalette.Navy, RoundedCornerShape(18.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        CodeLine("fun solve(pattern) {", AppPalette.Purple)
        CodeLine("  val role = mapRole()", Color.White)
        CodeLine("  return decision", AppPalette.Green)
        CodeLine("}", Color.White)
    }
}

@Composable
private fun CodeLine(
    text: String,
    color: Color
) {
    Text(
        text = text,
        color = color,
        fontSize = 10.sp,
        lineHeight = 15.sp,
        fontWeight = FontWeight.Medium
    )
}

@Composable
private fun MotivationalCard(
    selectedLevelName: String,
    hasQuestions: Boolean,
    onStartLearning: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = AppPalette.Navy),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            AppPalette.Navy,
                            Color(0xFF172554),
                            Color(0xFF312E81)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "∞",
                    color = Color(0xFF60A5FA),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Misli dugoročno.",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = "Izabrani nivo: $selectedLevelName. Dizajniraj za promenu i isporuči vrednost.",
                    color = Color(0xFFCBD5E1),
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onStartLearning,
                    enabled = hasQuestions,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppPalette.Blue,
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF334155),
                        disabledContentColor = Color(0xFF94A3B8)
                    )
                ) {
                    Text(
                        text = if (hasQuestions) "Započni" else "Nema dostupnih pitanja",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun DesignBottomNavigation() {
    Surface(
        color = Color.White.copy(alpha = 0.96f),
        shadowElevation = 16.dp,
        border = BorderStroke(1.dp, AppPalette.Border)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                )
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(icon = "⌂", label = "Početna", selected = true)
            BottomNavItem(icon = "≋", label = "Talasi", selected = false)
            BottomNavItem(icon = "▤", label = "Zadaci", selected = false)
            BottomNavItem(icon = "▥", label = "Napredak", selected = false)
            BottomNavItem(icon = "○", label = "Profil", selected = false)
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: String,
    label: String,
    selected: Boolean
) {
    val color = if (selected) AppPalette.Blue else Color(0xFF64748B)

    Column(
        modifier = Modifier
            .width(70.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable { }
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = icon,
            color = color,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = label,
            color = color,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            maxLines = 1
        )
    }
}

@Composable
private fun MiniIconBox(
    text: String,
    accentColor: Color,
    softColor: Color
) {
    Box(
        modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(softColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = accentColor,
            fontSize = 23.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
private fun ProgressLine(
    progress: Float,
    color: Color,
    trackColor: Color,
    height: Dp
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        label = "progress"
    )

    LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(100.dp)),
        color = color,
        trackColor = trackColor,
        strokeCap = StrokeCap.Round
    )
}

@Composable
private fun CircularProgressBadge(
    progress: Float,
    color: Color,
    size: Dp = 42.dp,
    strokeWidth: Dp = 5.dp
) {
    Canvas(
        modifier = Modifier.size(size)
    ) {
        drawArc(
            color = Color(0xFFE2E8F0),
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round
            )
        )

        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 360f * progress.coerceIn(0f, 1f),
            useCenter = false,
            style = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round
            )
        )
    }
}

private fun levelStyle(levelId: String): LevelVisualStyle {
    return when (levelId) {
        LearningLevel.BEGINNER.id -> LevelVisualStyle(
            accentColor = AppPalette.Blue,
            softColor = Color(0xFFEFF6FF),
            illustration = "💡"
        )

        LearningLevel.JUNIOR.id -> LevelVisualStyle(
            accentColor = AppPalette.Green,
            softColor = Color(0xFFECFDF5),
            illustration = "</>"
        )

        LearningLevel.MEDIOR.id -> LevelVisualStyle(
            accentColor = AppPalette.Orange,
            softColor = Color(0xFFFFF7ED),
            illustration = "◎"
        )

        LearningLevel.SENIOR.id -> LevelVisualStyle(
            accentColor = AppPalette.Purple,
            softColor = Color(0xFFF5F3FF),
            illustration = "◈"
        )

        LearningLevel.ARCHITECT.id -> LevelVisualStyle(
            accentColor = AppPalette.Indigo,
            softColor = Color(0xFFEEF2FF),
            illustration = "▦"
        )

        else -> LevelVisualStyle(
            accentColor = AppPalette.Blue,
            softColor = Color(0xFFEFF6FF),
            illustration = "?"
        )
    }
}

private fun difficultyColor(difficulty: String): Color {
    return when (difficulty.lowercase()) {
        "lako" -> AppPalette.Green
        "srednje" -> AppPalette.Orange
        "teže" -> AppPalette.Purple
        "hard" -> AppPalette.Purple
        "medium" -> AppPalette.Orange
        "easy" -> AppPalette.Green
        else -> AppPalette.Blue
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SoftwareDesignHomeScreenPreview() {
    PMUProjekatTheme {
        SoftwareDesignHomeScreen(
            uiState = HomeUiState(
                isLoading = false,
                userName = "Marko",
                selectedLevel = "beginner",
                selectedLevelName = "Početnik",
                totalQuestions = 11,
                selectedLevelQuestions = 4,
                completedQuestions = 0,
                streakDays = 0,
                xp = 0,
                levels = listOf(
                    LevelSummaryUi(
                        levelId = "beginner",
                        number = 1,
                        title = "Početnik",
                        description = "Osnove obrazaca i dobrih praksi",
                        topicCount = 4,
                        questionCount = 4
                    ),
                    LevelSummaryUi(
                        levelId = "junior",
                        number = 2,
                        title = "Junior",
                        description = "Primeni obrasce i piši čist kod",
                        topicCount = 4,
                        questionCount = 4
                    )
                ),
                questionPreviews = listOf(
                    QuestionPreviewUi(
                        questionId = "P1.1",
                        levelId = "beginner",
                        title = "Jedna instanca za ceo program",
                        typeLabel = "Prepoznavanje obrasca",
                        difficulty = "Lako",
                        wave = 1,
                        orderIndex = 1
                    )
                )
            ),
            onLevelSelected = {},
            onStartLearning = {},
            onQuestionClick = {}
        )
    }
}