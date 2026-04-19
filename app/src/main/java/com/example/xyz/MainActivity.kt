package com.example.xyz

// ── All existing imports stay the same ──
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.myapp.navigation.NavGraph

class MainActivity : ComponentActivity() {
    private val viewModel: GeminiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController)
        }
    }
}

// ── Design Tokens ──────────────────────────────────────────────────
private val BgDeep       = Color(0xFF0A0F0E)
private val BgCard       = Color(0xFF0D1F19)
private val BgField      = Color(0x0AFFFFFF)
private val GreenPrimary = Color(0xFF2E9E58)
private val GreenLight   = Color(0xFF4ABB75)
private val GreenGlow    = Color(0x1A4ABB75)
private val BorderSubtle = Color(0x17FFFFFF)
private val BorderGreen  = Color(0x2A4ABB75)
private val TextPrimary  = Color(0xFFFFFFFF)
private val TextSub      = Color(0x73FFFFFF)
private val TextMuted    = Color(0x38FFFFFF)
private val ErrorRed     = Color(0xFFE34141)

// ── Top-Level Screen ────────────────────────────────────────────────
@Composable
fun DietPlanApp(viewModel: GeminiViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(listOf(Color(0xFF0D1F1A), Color(0xFF0A1510), Color(0xFF081208)))
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // ── Sticky Top Bar ──────────────────────────────────────
            TopBar()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SectionLabel("Personal Information")
                DietPlanForm(
                    onGeneratePlan = { viewModel.generateIndianDietPlan(it) },
                    isLoading = uiState is UiState.Loading
                )
                SectionLabel("Your Plan")
                ResultSection(uiState = uiState)
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

// ── Top Bar ─────────────────────────────────────────────────────────
@Composable
fun TopBar() {
    val infiniteTransition = rememberInfiniteTransition(label = "badge")
    val badgeBlink by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 0.3f,
        animationSpec = infiniteRepeatable(tween(1200), RepeatMode.Reverse),
        label = "blink"
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0D1F1A))
            .border(width = 1.dp, color = BorderGreen, shape = RoundedCornerShape(0.dp))
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Brush.linearGradient(listOf(Color(0xFF1A3A2A), Color(0xFF0F2218))))
                .border(1.dp, BorderGreen, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) { Text("🥗", fontSize = 17.sp) }

        Column {
            Text(
                "Dietify",
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                style = LocalTextStyle.current.copy(
                    brush = Brush.linearGradient(listOf(Color.White, Color(0xFFA8E69B), GreenLight))
                )
            )
            Text(
                "INDIAN DIET PLANNER",
                fontSize = 10.sp,
                letterSpacing = 1.5.sp,
                color = TextMuted,
                fontWeight = FontWeight.Light
            )
        }

        Spacer(Modifier.weight(1f))

        // Badge
        Row(
            modifier = Modifier
                .clip(CircleShape)
                .background(GreenGlow)
                .border(1.dp, BorderGreen, CircleShape)
                .padding(horizontal = 12.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(GreenLight.copy(alpha = badgeBlink))
            )
            Text("AI", fontSize = 10.sp, color = GreenLight, letterSpacing = 1.sp)
        }
    }
}

// ── Section Label ────────────────────────────────────────────────────
@Composable
fun SectionLabel(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text.uppercase(),
            fontSize = 10.sp,
            letterSpacing = 1.5.sp,
            color = GreenLight.copy(0.7f),
            fontWeight = FontWeight.Medium
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(Brush.horizontalGradient(listOf(GreenLight.copy(0.2f), Color.Transparent)))
        )
    }
}

// ── Info Card Shell ───────────────────────────────────────────────────
@Composable
fun InfoCard(
    icon: String,
    title: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(BgCard)
            .border(1.dp, BorderGreen, RoundedCornerShape(20.dp))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(9.dp))
                    .background(GreenGlow)
                    .border(1.dp, BorderGreen, RoundedCornerShape(9.dp)),
                contentAlignment = Alignment.Center
            ) { Text(icon, fontSize = 16.sp) }
            Column {
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextPrimary.copy(0.85f))
                Text(subtitle, fontSize = 11.sp, color = TextMuted)
            }
        }
        Spacer(Modifier.height(18.dp))
        content()
    }
}

// ── Styled Field ─────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(label.uppercase(), fontSize = 10.sp, letterSpacing = 1.sp, color = TextMuted)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, fontSize = 13.sp, color = TextMuted) },
            singleLine = singleLine,
            maxLines = maxLines,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = TextPrimary),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GreenLight.copy(0.4f),
                unfocusedBorderColor = BorderSubtle,
                focusedContainerColor = GreenGlow,
                unfocusedContainerColor = BgField,
                cursorColor = GreenLight
            )
        )
    }
}

// ── Styled Dropdown ───────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalDropdown(
    label: String,
    selected: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(label.uppercase(), fontSize = 10.sp, letterSpacing = 1.sp, color = TextMuted)
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = selected,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = GreenLight.copy(0.6f),
                        modifier = Modifier.size(18.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                shape = RoundedCornerShape(12.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = TextPrimary),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenLight.copy(0.4f),
                    unfocusedBorderColor = BorderSubtle,
                    focusedContainerColor = GreenGlow,
                    unfocusedContainerColor = BgField
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color(0xFF122018))
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, fontSize = 14.sp, color = TextSub) },
                        onClick = { onSelect(option); expanded = false }
                    )
                }
            }
        }
    }
}

// ── Thin Divider ─────────────────────────────────────────────────────
@Composable
fun GreenDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Brush.horizontalGradient(listOf(Color.Transparent, GreenLight.copy(0.12f), Color.Transparent)))
    )
}

// ── Main Form ─────────────────────────────────────────────────────────
@Composable
fun DietPlanForm(onGeneratePlan: (HealthInputs) -> Unit, isLoading: Boolean) {
    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var healthConditions by remember { mutableStateOf("") }
    var allergies by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("Male") }
    var selectedActivity by remember { mutableStateOf("Moderate") }
    var selectedGoal by remember { mutableStateOf("General Health") }
    var selectedDiet by remember { mutableStateOf("Vegetarian") }
    var selectedCuisine by remember { mutableStateOf("Mixed") }
    var showError by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // ── Card 1: Body Metrics ───────────────────────────────────
        InfoCard("👤", "Body Metrics", "Used to calculate your daily caloric needs") {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ProfessionalTextField(
                        value = age, onValueChange = { age = it },
                        label = "Age", placeholder = "e.g. 28",
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.weight(1f)
                    )
                    ProfessionalDropdown(
                        label = "Gender", selected = selectedGender,
                        options = listOf("Male", "Female", "Other"),
                        onSelect = { selectedGender = it },
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ProfessionalTextField(
                        value = height, onValueChange = { height = it },
                        label = "Height (cm)", placeholder = "e.g. 170",
                        keyboardType = KeyboardType.Decimal,
                        modifier = Modifier.weight(1f)
                    )
                    ProfessionalTextField(
                        value = weight, onValueChange = { weight = it },
                        label = "Weight (kg)", placeholder = "e.g. 65",
                        keyboardType = KeyboardType.Decimal,
                        modifier = Modifier.weight(1f)
                    )
                }
                ProfessionalDropdown(
                    label = "Activity Level", selected = selectedActivity,
                    options = listOf("Sedentary", "Light", "Moderate", "Active", "Very Active"),
                    onSelect = { selectedActivity = it }
                )
            }
        }

        // ── Card 2: Goals & Preferences ───────────────────────────
        InfoCard("🎯", "Goals & Preferences", "Customize your plan to your lifestyle") {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ProfessionalDropdown(
                        label = "Health Goal", selected = selectedGoal,
                        options = listOf("General Health", "Weight Loss", "Weight Gain", "Muscle Gain", "Maintain Weight"),
                        onSelect = { selectedGoal = it },
                        modifier = Modifier.weight(1f)
                    )
                    ProfessionalDropdown(
                        label = "Diet Type", selected = selectedDiet,
                        options = listOf("Vegetarian", "Non-Vegetarian", "Vegan", "Jain", "Eggetarian"),
                        onSelect = { selectedDiet = it },
                        modifier = Modifier.weight(1f)
                    )
                }
                ProfessionalDropdown(
                    label = "Preferred Cuisine", selected = selectedCuisine,
                    options = listOf("Mixed", "North Indian", "South Indian", "Bengali", "Gujarati"),
                    onSelect = { selectedCuisine = it }
                )
                GreenDivider()
                ProfessionalTextField(
                    value = healthConditions, onValueChange = { healthConditions = it },
                    label = "Health Conditions",
                    placeholder = "e.g. Diabetes, Hypertension",
                    singleLine = false, maxLines = 2
                )
                ProfessionalTextField(
                    value = allergies, onValueChange = { allergies = it },
                    label = "Food Allergies",
                    placeholder = "e.g. Nuts, Dairy, Gluten",
                    singleLine = false, maxLines = 2
                )
            }
        }

        // ── Error ──────────────────────────────────────────────────
        AnimatedVisibility(visible = showError.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(ErrorRed.copy(0.08f))
                    .border(1.dp, ErrorRed.copy(0.2f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("⚠️", fontSize = 14.sp)
                Text(showError, fontSize = 13.sp, color = ErrorRed.copy(0.9f))
            }
        }

        // ── CTA Button ─────────────────────────────────────────────
        Button(
            onClick = {
                val validation = validateInputs(age, height, weight)
                if (validation.isEmpty()) {
                    showError = ""
                    onGeneratePlan(
                        HealthInputs(
                            age = age.toInt(),
                            gender = selectedGender,
                            height = height.toFloat(),
                            weight = weight.toFloat(),
                            activityLevel = selectedActivity,
                            goal = selectedGoal,
                            healthConditions = parseCommaSeparatedList(healthConditions),
                            dietType = selectedDiet,
                            allergies = parseCommaSeparatedList(allergies),
                            preferredCuisine = selectedCuisine
                        )
                    )
                } else {
                    showError = validation
                }
            },
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp),
            elevation = ButtonDefaults.buttonElevation(0.dp),
            enabled = !isLoading
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        if (!isLoading)
                            Brush.linearGradient(listOf(Color(0xFF2E9E58), Color(0xFF1D7A3F), Color(0xFF155C30)))
                        else
                            Brush.linearGradient(listOf(Color(0xFF1A4A2A), Color(0xFF122018))),
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        CircularProgressIndicator(Modifier.size(18.dp), color = GreenLight, strokeWidth = 2.dp, trackColor = GreenLight.copy(0.1f))
                        Text("Generating…", fontSize = 14.sp, color = TextSub, letterSpacing = 0.5.sp)
                    }
                } else {
                    Text("Generate My Diet Plan", fontSize = 14.sp, color = Color.White, letterSpacing = 0.5.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

// ── Result Section ────────────────────────────────────────────────────
@Composable
fun ResultSection(uiState: UiState) {
    AnimatedContent(targetState = uiState, label = "result") { state ->
        when (state) {
            is UiState.Initial -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(BgCard)
                        .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
                        .padding(36.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("🍱", fontSize = 36.sp)
                    Text(
                        "Fill in your details above and tap 'Generate My Diet Plan' to receive your personalized Indian diet plan.",
                        fontSize = 13.sp, color = TextMuted, textAlign = TextAlign.Center, lineHeight = 20.sp
                    )
                }
            }

            is UiState.Loading -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(BgCard)
                        .border(1.dp, BorderGreen, RoundedCornerShape(20.dp))
                        .padding(28.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    CircularProgressIndicator(Modifier.size(28.dp), color = GreenLight, strokeWidth = 2.dp, trackColor = GreenLight.copy(0.1f))
                    Column {
                        Text("Generating your plan…", fontSize = 14.sp, color = TextPrimary.copy(0.8f), fontWeight = FontWeight.Medium)
                        Text("This usually takes a few seconds", fontSize = 12.sp, color = TextMuted)
                    }
                }
            }

            is UiState.Success -> {
                InfoCard("✅", "Your Personalized Diet Plan", "Tailored to your inputs and goals") {
                    FormattedMarkdownText(text = state.output, modifier = Modifier.fillMaxWidth())
                }
            }

            is UiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(ErrorRed.copy(0.06f))
                        .border(1.dp, ErrorRed.copy(0.2f), RoundedCornerShape(20.dp))
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Something went wrong", fontSize = 15.sp, fontWeight = FontWeight.Medium, color = ErrorRed)
                    Text(state.error, fontSize = 13.sp, color = ErrorRed.copy(0.7f), lineHeight = 20.sp)
                    Text("Check your internet connection and try again.", fontSize = 12.sp, color = TextMuted)
                }
            }
        }
    }
}

// ── Markdown Renderer (styled) ─────────────────────────────────────────
@Composable
fun FormattedMarkdownText(text: String, modifier: Modifier = Modifier) {
    val lines = text.split("\n")
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(0.dp)) {
        lines.forEach { line ->
            when {
                line.startsWith("# ") -> Text(
                    line.removePrefix("# "), fontSize = 20.sp, fontWeight = FontWeight.Bold,
                    color = TextPrimary, modifier = Modifier.padding(vertical = 10.dp)
                )
                line.startsWith("## ") -> Text(
                    line.removePrefix("## "), fontSize = 17.sp, fontWeight = FontWeight.Medium,
                    color = GreenLight, modifier = Modifier.padding(vertical = 8.dp)
                )
                line.startsWith("### ") -> Text(
                    line.removePrefix("### "), fontSize = 15.sp, fontWeight = FontWeight.Medium,
                    color = GreenLight.copy(0.75f), modifier = Modifier.padding(vertical = 6.dp)
                )
                line.trim().startsWith("- ") || line.trim().startsWith("* ") -> Row(
                    modifier = Modifier.padding(vertical = 3.dp, horizontal = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(Modifier.size(5.dp).clip(CircleShape).background(GreenLight.copy(0.6f)).align(Alignment.CenterVertically))
                    Text(
                        line.trim().removePrefix("- ").removePrefix("* "),
                        fontSize = 13.sp, color = TextSub, lineHeight = 20.sp
                    )
                }
                line.trim().matches(Regex("^\\d+\\. .*")) -> Row(
                    modifier = Modifier.padding(vertical = 3.dp, horizontal = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("${line.trim().substringBefore(". ")}.", fontSize = 13.sp, color = GreenLight, fontWeight = FontWeight.Medium)
                    Text(line.trim().substringAfter(". "), fontSize = 13.sp, color = TextSub, lineHeight = 20.sp)
                }
                line.isBlank() -> Spacer(Modifier.height(6.dp))
                else -> if (line.isNotBlank()) Text(
                    line, fontSize = 13.sp, lineHeight = 20.sp, color = TextSub,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}
// Line ~420 — add these at the bottom of the file

private fun validateInputs(age: String, height: String, weight: String): String {
    if (age.isBlank()) return "Please enter your age"
    if (height.isBlank()) return "Please enter your height"
    if (weight.isBlank()) return "Please enter your weight"
    return try {
        val ageInt = age.toInt()
        if (ageInt < 1 || ageInt > 120) return "Please enter a valid age (1–120)"
        val heightFloat = height.toFloat()
        if (heightFloat < 50 || heightFloat > 250) return "Please enter a valid height (50–250 cm)"
        val weightFloat = weight.toFloat()
        if (weightFloat < 20 || weightFloat > 300) return "Please enter a valid weight (20–300 kg)"
        ""
    } catch (e: NumberFormatException) {
        "Please enter valid numbers"
    }
}

private fun parseCommaSeparatedList(input: String): List<String> =
    if (input.isBlank()) emptyList()
    else input.split(",").map { it.trim() }.filter { it.isNotBlank() }