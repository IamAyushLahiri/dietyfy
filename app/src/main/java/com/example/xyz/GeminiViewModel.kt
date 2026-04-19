package com.example.xyz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GeminiViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    private lateinit var generativeModel: GenerativeModel

    init {
        generativeModel = GenerativeModel(
            modelName = "gemini-2.5-flash",
            apiKey = BuildConfig.GEMINI_API_KEY
        )
    }

    fun generateIndianDietPlan(healthInputs: HealthInputs) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val prompt = buildDietPrompt(healthInputs)
                val response = generativeModel.generateContent(prompt)
                response.text?.let {
                    _uiState.value = UiState.Success(it)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    private fun buildDietPrompt(inputs: HealthInputs): String {
        return """
       Create a simple daily Indian diet plan based on this profile:

       Age: ${inputs.age}, Gender: ${inputs.gender}, Height: ${inputs.height} cm, Weight: ${inputs.weight} kg, Activity: ${inputs.activityLevel}, Goal: ${inputs.goal}, Health conditions: ${
            if (inputs.healthConditions.isNotEmpty()) inputs.healthConditions.joinToString(
                ", "
            ) else "None"
        }, Diet type: ${inputs.dietType}, Allergies: ${
            if (inputs.allergies.isNotEmpty()) inputs.allergies.joinToString(
                ", "
            ) else "None"
        }, Preferred cuisine: ${inputs.preferredCuisine}.

       Requirements:
       1. at first show the personal information and dietary preferences as the user given.
       2. Use only traditional Indian meals (breakfast, lunch, dinner) with portion sizes,and give this headings into bold.
       3. Keep it short, practical, and easy to read in plain text.
       4. Adjust for health conditions and preferences.
       5. End with 2-3 easy cooking or eating tips in layman’s terms.
       6. Include water intake recommendations (e.g., 2-3 liters per day).
       7. Include a sleep tracker
       

       Response format:
       - Daily calorie target (just one number)
       - Meal plan: Breakfast, Lunch, Dinner (short sentences, no long explanation)


        **Personal Information:**
        - Age: ${inputs.age} years
        - Gender: ${inputs.gender}
        - Height: ${inputs.height} cm
        - Weight: ${inputs.weight} kg
        - BMI: ${calculateBMI(inputs.height, inputs.weight)}
        - Activity Level: ${inputs.activityLevel}
        - Goal: ${inputs.goal}

       **Health Conditions:**
        ${if (inputs.healthConditions.isNotEmpty()) inputs.healthConditions.joinToString(", ") else "None"}

        **Dietary Preferences:**
        - Diet Type: ${inputs.dietType}
        - Food Allergies: ${if (inputs.allergies.isNotEmpty()) inputs.allergies.joinToString(", ") else "None"}
        - Preferred Cuisine: ${inputs.preferredCuisine}

       
        """.trimIndent()
    }

    private fun calculateBMI(height: Float, weight: Float): Float {
        val heightInMeters = height / 100
        return weight / (heightInMeters * heightInMeters)
    }

    // Generic content generation (keeping original functionality)
    fun generateContent(prompt: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = generativeModel.generateContent(prompt)
                response.text?.let {
                    _uiState.value = UiState.Success(it)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}

// Data class to hold health inputs
data class HealthInputs(
    val age: Int,
    val gender: String, // "Male", "Female", "Other"
    val height: Float, // in cm
    val weight: Float, // in kg
    val activityLevel: String, // "Sedentary", "Light", "Moderate", "Active", "Very Active"
    val goal: String, // "Weight Loss", "Weight Gain", "Maintain Weight", "Muscle Gain", "General Health"
    val healthConditions: List<String>, // e.g., ["Diabetes", "Hypertension", "PCOD"]
    val dietType: String, // "Vegetarian", "Non-Vegetarian", "Vegan", "Jain", "Eggetarian"
    val allergies: List<String>, // e.g., ["Nuts", "Dairy", "Gluten"]
    val preferredCuisine: String // "North Indian", "South Indian", "Bengali", "Gujarati", "Mixed"
)

sealed interface UiState {
    object Initial : UiState
    object Loading : UiState
    data class Success(val output: String) : UiState
    data class Error(val error: String) : UiState
}
