# SkyCast

## Overview
SkyCast is a weather application that provides users with real-time weather information and forecasts. The app features a clean and intuitive user interface built using Jetpack Compose, following the MVVM Clean architecture.

## Setup Instructions
To run the project locally, please add your API key to the `local.properties` file:

API_KEY = 00fac2a82af6823afd335f2f8684288d


## Jetpack Components Used
- **ViewModel**: For managing UI-related data in a lifecycle-conscious way.
- **Lifecycle**: To ensure that the UI updates correctly according to the lifecycle of the app components.
- **Navigation**: To facilitate smooth transitions between different screens in the app.

## UI Framework
- **Jetpack Compose**: Utilized for building the UI, allowing for a more declarative approach to UI design.

## Architecture
- **MVVM Clean**: The app architecture is structured to separate concerns, making it easier to maintain and test.

## Dependency Injection
- **Hilt**: Implemented for managing dependencies throughout the application.

## Geolocation
I don't have enough time to work on the geocoder for location requests to get the latitude and longitude, so I decided to implement city selection with corresponding latitude and longitude for now.

## Testing
Due to time constraints, unit tests were not created. However, the project structure is set up in a way that testing can be easily implemented in the future.

## Features
Feel free to explore the codebase, which includes simple navigation between:
- **Login Screen**
- **Signup Screen**
- **Home Screen** with two tabs for different functionalities.
