# Capsule App

The Capsule App is an Android application designed to provide users with a curated sequence of screens, forming a cohesive and engaging user experience. This README.md file provides an overview of the project, its key features, technical details, and instructions for running the app.

## Features

### 1. Category screen
- **Implementation:**
  * Loads category from firebase(real-time database)
  * Categorize using Preference datastore

### 2. Notes Screen
- **Task:** Display textual content related to a specific topic.
- **Implementation:** Loads content from a provided PDF hosted on Google Drive, rendering it in a WebView.

### 3. Quiz Screen
- **Task:** Present a set of multiple-choice questions.
- **Implementation:** Questions are stored in a `Quiz` class, providing a shuffled set for users to answer.

### 4. Quiz Result Screen
- **Task:** Display the result of the quiz.
- **Implementation:** After completing the quiz, users are shown their score as a percentage on the Quiz Result Screen.

https://github.com/sheikh-20/Capsule-App/assets/121604647/d4614d5a-76c6-4731-bdd4-7d96df3b749c


## Project Structure

The project is organized into several packages:
- `fragments`: Contains individual fragments for different screens (Video, Notes, Quiz, Quiz Result).
- `adapters`: Includes the `CollectionAdapter` responsible for managing the ViewPager.
- `utility`: Holds utility classes and data structures, such as the `Quiz` class and constants.

## How to Run

1. **Clone the repository.**
2. **Open the project in Android Studio.**
3. **Build and run the application on an emulator or physical device.**


Feel free to reach out if you have any questions or need further clarification on the assignment.
