# Android Kotlin Authentication System

This project is a production-ready **Authentication System** module built with **Kotlin**, **XML Layouts**, and **Clean Architecture**. It provides a fully functional and scalable authentication flow using MVVM, Dependency Injection (Hilt), and the Repository Pattern, with a modern UI design implemented using ConstraintLayout and Material Design 3.

## Features

- **Authentication Flows**: Email/Password Login, Signup, and Logout.
- **Firebase Authentication**: Integrated with Firebase for real-world usability and production security.
- **Firestore Integration**: Automatically persists and manages user profile data in Cloud Firestore upon successful authentication.
- **Google Sign-In**: Fully implemented Google authentication using the standard `GoogleSignInClient` for a seamless account selection experience.
- **Premium UI/UX**: Modern, responsive design with gradients, shadows, and smooth transitions, implemented using XML ConstraintLayout for a high-quality user experience.
- **Clean Architecture + MVVM**: Strict separation of concerns across Data, Domain, and Presentation layers ensuring code testability and modularity.

## Architecture & Tech Stack

- **UI Framework**: XML Layouts (Material Components 3)
- **Binding**: View Binding
- **Language**: Kotlin
- **Architecture**: Clean Architecture (Presentation, Domain, Data) + MVVM
- **Dependency Management**: Gradle Kotlin DSL with Version Catalogs (`libs.versions.toml`)
- **Dependency Injection**: Hilt (Dagger)
- **Asynchronous Flow**: Kotlin Coroutines & Flow
- **Backend/Services**: Firebase SDK (Auth & Firestore)

## Folder Structure

```text
app/src/main/java/com/ck/events/app/
├── data/
│   ├── repository/       # Concrete repositories implementing Domain protocols
│   └── source/           # Remote data sources (Firebase Auth, Firestore)
├── di/                   # Hilt Dependency Injection Modules
├── domain/
│   ├── model/            # Domain Entities (User, AuthResult)
│   ├── repository/       # Repository interfaces
├── presentation/
│   ├── activities/       # LoginActivity, SignupActivity, HomeActivity
│   └── viewmodels/       # ViewModels backing the UI
└── App.kt                # Hilt Application class

app/src/main/res/
├── layout/               # XML Layout files (activity_login.xml, etc.)
├── drawable/             # Vector assets and custom shapes
└── values/               # Colors, Themes, and Strings
```

## Setup Instructions

### 1. Configure Firebase

To utilize the Firebase authentication and Firestore layers:

1. Create a project in the [Firebase Console](https://console.firebase.google.com/).
2. Add an **Android App** with the package name `com.ck.events.app`.
3. Enable **Email/Password** and **Google** authentication in the Auth section.
4. Download the `google-services.json` file.
5. Place `google-services.json` into the `app/` directory of this project.

### 2. Enable Google Login

For Google Sign-In to function correctly on your local environment:

1. **SHA-1 Fingerprint**: You must add your local debug SHA-1 to your Firebase project.
   - Run `./gradlew signingReport` in your terminal.
   - Copy the SHA-1 value from the `debug` variant.
   - Add this fingerprint to your app settings in the Firebase Console.
2. **Web Client ID**: 
   - Navigate to **Authentication** > **Sign-in method** > **Google** in Firebase.
   - Copy the **Web client ID** found under the "Web SDK configuration" section.
   - For this project, put your client ID in `LoginActivity.kt`:
     `val serverClientId = "YOUR_CLIENT_ID_HERE"`

### 3. Build and Run

- Open the project in **Android Studio**.
- Sync Gradle to install all dependencies.
- Select your target device or emulator and press **Run** (`Shift + F10`).

## Custom Backend Integration

The project is structured to easily swap Firebase with a custom backend.
To integrate a custom API:
1. Navigate to `data/source/AuthRemoteDataSource.kt`.
2. Implement your API logic (e.g., using Retrofit) inside the methods.
3. The rest of the app (Domain and Presentation) will remain untouched!

---

## Contribution

Feel free to fork and improve this project! Pull requests are welcome.

---

## License

This project is licensed under the Apache-2.0 License.

---
*Note: Do not commit your real API key to public repositories.*