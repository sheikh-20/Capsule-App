# Capsule App

The Capsule App is an Android application designed to provide users with a curated sequence of screens, forming a cohesive and engaging user experience. This README.md file provides an overview of the project, its key features, technical details, and instructions for running the app.

## Features

### 1. Category screen
- **Implementation:**
  * Loads category from firebase(real-time database)
  * Categorize using Preference datastore

### 2. Notes Screen
- **Implementation:**
  * Loads content from a provided PDF hosted on firebase, rendering it in a WebView.
 
### 3. Video Screen
- **Implementation:**
  * Loads youtube video related to the category hosted on firebase

### 4. Quiz Screen
- **Implementation:**
  * Loads Quiz from firebase (real-time database)
  * Check the correct answer
  * Display dialog when completed

https://github.com/sheikh-20/Capsule-App/assets/121604647/d4614d5a-76c6-4731-bdd4-7d96df3b749c


## Project Structure

The project is organized into several packages:
- `ui`: Contains individual for different screens (Category, Video, Notes, Quiz).
- `domain`: Includes the Usecases containing buisness logic.
- `data`: Contains several repositories retrieves data from firebase.

## How to Run

1. **Clone the repository.**
2. **Open the project in Android Studio.**
3. **Build and run the application on an emulator or physical device.**


Feel free to reach out if you have any questions or need further clarification on the assignment.
