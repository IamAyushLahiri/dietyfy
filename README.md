# 🥗 Dietfy — AI-Powered Personalized Diet Planner

**A Smart Android Application for Personalized Diet Planning Using Kotlin, Jetpack Compose, and Gemini AI.**

Dietfy is an Android application designed to generate personalized diet plans based on user-provided information, dietary preferences, and health-related requirements.

By integrating AI-powered generation into a mobile application, Dietfy aims to make personalized meal planning more accessible through a simple and intuitive user experience.

The project demonstrates Android application development, API integration, and the use of generative AI to create structured, user-focused content.

---

## 📱 Application Screenshots

Explore the application workflow, from the splash screen to AI-generated diet plan results.

<table>
  <tr>
    <td align="center" width="50%">
      <b>1. Splash Screen</b>
    </td>
    <td align="center" width="50%">
      <b>2. Home Screen</b>
    </td>
  </tr>
  <tr>
    <td>
      <img src="./WhatsApp%20Image%202026-10-29%20at%2012.08.47%20PM.jpeg" alt="Dietfy splash screen" width="100%">
    </td>
    <td>
      <img src="./WhatsApp%20Image%202026-10-29%20at%2012.08.48%20PM.jpeg" alt="Dietfy home screen" width="100%">
    </td>
  </tr>
  <tr>
    <td align="center" width="50%">
      <b>3. AI Generation</b>
    </td>
    <td align="center" width="50%">
      <b>4. Generated Results</b>
    </td>
  </tr>
  <tr>
    <td>
      <img src="./WhatsApp%20Image%202026-10-29%20at%2012.08.49%20PM.jpeg" alt="Dietfy generating a personalized diet plan" width="100%">
    </td>
    <td>
      <img src="./WhatsApp%20Image%202026-10-29%20at%2012.08.50%20PM.jpeg" alt="Dietfy generated diet plan results" width="100%">
    </td>
  </tr>
</table>

---

## ✨ Key Features

* **AI-Powered Diet Generation:** Uses generative AI to help create diet plans from user-provided information.
* **Personalized Planning:** Generates diet suggestions based on the information and requirements entered by the user.
* **Simple User Interface:** Provides an Android interface for interacting with the diet-planning workflow.
* **Generation Progress:** Includes a generation screen while the diet plan is being prepared.
* **Results Presentation:** Displays the generated diet plan in a dedicated results interface.
* **Native Android Development:** Built using Kotlin and modern Android development tools.

*The exact recommendations and supported inputs depend on the application's current implementation.*

---

## 🛠️ Tech Stack

| Technology        | Purpose                                               |
| ----------------- | ----------------------------------------------------- |
| Kotlin            | Primary programming language                          |
| Jetpack Compose   | Declarative Android UI development                    |
| Android SDK       | Native Android application development                |
| Retrofit          | HTTP communication with remote APIs, where configured |
| Google Gemini API | AI-powered content generation, where configured       |
| Gradle            | Build automation and dependency management            |
| Android Studio    | Development environment                               |

---

## 🏗️ How Dietfy Works

The application follows a straightforward diet-generation workflow.

1. **Launch the Application:** The user opens Dietfy and reaches the home screen.
2. **Provide Information:** The user enters the information requested by the application.
3. **Generate a Diet Plan:** The application sends the relevant request to its configured AI service.
4. **Process the Response:** The application receives the generated response and prepares it for presentation.
5. **View the Results:** The user reviews the resulting diet suggestions in the results screen.

This workflow demonstrates how a mobile application can integrate a generative AI service into a user-facing feature.

---

## 🚀 Getting Started

Follow these instructions to set up the project locally.

### Prerequisites

Make sure you have:

* Android Studio installed.
* Android SDK compatible with the project.
* A JDK version compatible with the project's Gradle configuration.
* An Android emulator or physical Android device.
* Internet access for remote API requests.
* A valid Gemini API key, if required by the current implementation.

### 1. Clone the Repository

Open a terminal and run:

```bash
git clone https://github.com/IamAyushLahiri/dietyfy.git
```

### 2. Open the Project

Launch Android Studio and select **Open**.

Choose the cloned `dietyfy` project directory.

### 3. Sync Gradle

Allow Android Studio to synchronise the Gradle project and download its dependencies.

Resolve any SDK or dependency configuration issues before running the application.

### 4. Configure the API Key

If the project uses the Gemini API, configure the API key through the secret-management mechanism expected by the existing project.

Do not commit actual API keys or other private credentials to GitHub.

See the API Security section below for important precautions.

### 5. Run the Application

Start an Android emulator or connect a physical Android device.

Click **Run** in Android Studio to build and launch Dietfy.

---

## 🔐 API Security

AI-powered applications may require API credentials to communicate with external services.

Follow these security practices:

* Never publish private API keys in source code or README examples.
* Keep local secret configuration files out of version control.
* Add sensitive configuration files to `.gitignore` where appropriate.
* Document the configuration process without exposing actual credentials.
* Restrict API usage and monitor requests through the provider's available controls.

**Important:** An API key embedded in a distributed Android application can potentially be extracted from the application package. For production applications, consider routing sensitive AI requests through a secured backend rather than relying on a client-side secret.

If an actual secret has already been committed publicly, revoke or rotate it. Deleting it from the latest version alone may not remove it from Git history.

---

## 📂 Project Structure

The project uses an Android Gradle-based structure.

```text
dietyfy/
├── app/
├── gradle/
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
└── README.md
```

This is a simplified representation of the repository's root structure. The actual application source files, resources, and configuration may contain additional directories.

---

## 🎯 Learning Outcomes

Dietfy provides practical experience with:

* Developing Android applications using Kotlin.
* Creating user interfaces with Jetpack Compose.
* Integrating remote APIs into a mobile application.
* Connecting an AI service to an application workflow.
* Handling generated content and presenting results.
* Managing Android builds and dependencies with Gradle.
* Documenting software projects for GitHub and portfolio use.

---

## 🔮 Future Improvements

Potential improvements for future versions include:

* Add dedicated loading, success, empty, and error states.
* Improve validation of user inputs.
* Add retry handling for failed API requests.
* Improve the structure and readability of generated diet plans.
* Allow users to save and revisit previously generated plans.
* Add unit tests for important application logic.
* Improve accessibility and support for different screen sizes.
* Introduce a secured backend for sensitive API operations.
* Add clear nutritional disclaimers and appropriate safety guidance.

These are potential enhancements, not claims about features already implemented.

---

## ⚠️ Health Disclaimer

Dietfy is a software project intended to demonstrate AI-assisted diet planning.

AI-generated dietary suggestions may be inaccurate, incomplete, or unsuitable for an individual's medical needs. Generated content should not replace professional medical advice or consultation with a registered dietitian.

Users with medical conditions, food allergies, or specialised nutritional requirements should consult a qualified healthcare professional before making significant dietary changes.

---

## 👨‍💻 Author

**Ayush Lahiri**

IT Undergraduate | Android Developer

* **GitHub Profile:** [@IamAyushLahiri](https://github.com/IamAyushLahiri)
* **Project Repository:** [Dietfy](https://github.com/IamAyushLahiri/dietyfy)

Interested in Android development, Kotlin, modern mobile application design, API integration, and AI-powered applications.

---

## 📄 License

No license is specified in this README.

If you intend to allow others to reuse, modify, or distribute the project, consider adding an appropriate `LICENSE` file.

---

<div align="center">

**Built with Kotlin, Jetpack Compose, and AI**

*Making personalized diet planning more accessible through technology.*

</div>
