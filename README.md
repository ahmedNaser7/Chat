# Chat Application

This is a simple chat application that allows users to register, login, and chat with their friends or family in different rooms. The application uses Firebase for authentication and real-time database, Room for local data storage, LiveData for observing data changes, and ViewModel for managing UI-related data.

## screanshots


## Features

- User Registration and Login
- Create and Join Chat Rooms
- Real-time Messaging
- Local Data Storage with Room
- Data Observation with LiveData
- UI State Management with ViewModel

## Technologies Used

- [Firebase Authentication](https://firebase.google.com/docs/auth)
- [Firebase Realtime Database](https://firebase.google.com/docs/database)
- [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)

## Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio)
- A Firebase project with Realtime Database and Authentication set up

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/chat-application.git
    ```
2. Open the project in Android Studio.

3. Add your `google-services.json` file to the `app` directory.

4. Sync the project with Gradle files.

### Configuration

1. Set up Firebase Authentication:
    - Enable Email/Password authentication in the Firebase console.

2. Set up Firebase Realtime Database:
    - Add the required database rules in the Firebase console to allow read and write access.

### Usage

1. **Register**: Create a new account using an email and password.
2. **Login**: Access the application using the registered credentials.
3. **Create Room**: Create a new chat room to start a conversation.
4. **Join Room**: Join an existing chat room using the room ID.
5. **Chat**: Send and receive messages in real-time.

## Architecture

The application follows the MVVM (Model-View-ViewModel) architecture pattern for a clean separation of concerns and easy testability.

- **Model**: Contains the data classes and the repository for data handling.
- **View**: Includes the UI components and observes the data from the ViewModel.
- **ViewModel**: Acts as a bridge between the Model and the View, holding the UI data and handling logic.

