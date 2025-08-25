# Data Agrin

A native Android application built with Jetpack Compose, designed to help agricultural operators
manage their daily tasks, log activities, and monitor weather conditions, with a strong focus on
offline-first functionality and local data persistence.

## Project Summary

This application addresses the needs of agricultural operators who often work with limited internet
connectivity. By leveraging a modern Android architecture and local data persistence, Data Agrin
ensures that critical tasks and activity logs are always accessible, even without a network
connection.

## Screenshots

| Daily Tasks                                                                | Activity Log                                                       | Weather                                                                                          | Activity History                                                          |
|----------------------------------------------------------------------------|--------------------------------------------------------------------|--------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| *(Placeholder for a screenshot showing a list of tasks with their status)* | *(Placeholder for a screenshot of the activity registration form)* | *(Placeholder for a screenshot of the weather display with temperature, humidity, and forecast)* | *(Placeholder for a screenshot showing a list of past logged activities)* |

## Key Features

- **Daily Task Management**: View and update the status of daily tasks, including the activity name,
  plot/area, and scheduled time.
- **Offline-First Data Persistence**: All tasks and activity logs are stored locally using Room,
  ensuring full functionality even in areas with no connectivity.
- **Activity Logging**: A dedicated form allows users to record completed activities with details
  such as type, plot, start/end times, and observations.
- **Historical Records**: Users can access and view a complete history of all logged activities.
- **Weather Integration**: Displays current temperature, humidity, and a short-term forecast by
  integrating with a public weather API.
- **Intelligent Caching**: The weather data is locally persisted, providing the last known forecast
  when the device is offline.
- **API Status Indicator**: A visual indicator (e.g., text or a colored dot) informs the user
  whether the displayed weather data is live from the API or a cached, persistent copy.

## Technologies

- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern declarative UI toolkit
- **Room** - Local database for persistent storage
- **Ktor** - For network communication with the weather API
- **Koin** - Dependency injection framework
- **JUnit, MockK, Turbine** - Testing frameworks for unit and integration tests

## Architecture

- **MVVM / Clean Architecture** - A layered approach to ensure separation of concerns, testability,
  and maintainability.
    - **Presentation Layer**: Manages UI components and user interactions.
    - **Domain Layer**: Contains business logic, use cases, and abstract data interfaces.
    - **Data Layer**: Handles data sources (local Room database, remote API) and their
      implementations.
- **SOLID Principles** - Adhering to five core principles for writing scalable, maintainable, and
  robust code.

## Installation

1. Clone the repository:
   ```
   git clone https://github.com/mrenann/data-agrin.git
   ```
2. Open the project in Android Studio.
3. Sync Gradle dependencies.
4. Build and run the application on an emulator or a physical device (API 26+).

## Usage

- **Daily Tasks Screen**: View your list of tasks. Tap on a task to change its status.
- **Activity Log Screen**: Navigate to the log screen to fill out the form and save a new activity.
  You can also view the history of your logged activities.
- **Weather Screen**: Go to the weather screen to see the latest forecast. The app will use cached
  data if you are offline.