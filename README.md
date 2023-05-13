# ![removal ai _tmp-645f086698496](https://github.com/andrewwu-dev/checkoff/assets/46847766/bea64597-28a8-474a-8622-c7c65fc033a5)

An Android todo app built in kotlin that allows users to create, edit, delete, complete and also undo deleted tasks. **Download link is in Setup section.**

[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/braEWT9UZEI/0.jpg)](https://www.youtube.com/watch?v=braEWT9UZEI)

## Implementation
- Using https://jsonplaceholder.typicode.com to act as a dummy API. In order to let the app simulate retrieving saved tasks from a server, the app will fetch 5 todos from the todo endpoint each time it boots up. The app will also send POST, PATCH, and DELETE requests to the endpoint based on what the user does with a task.
- Also using a Room database as local storage so the app is able to keep track of newly created tasks or deleted tasks and present them to the screen.
- Upon making a request to the repository, the app will first make a request to the server and then update the local database.
- Adding and editing a task share the same screen because the have the same UI.
- Networking is done with Retrofit and deserializer with Moshi
- Dependency injection with Hilt
- MVVM architecture with Jetpack Compose for UI and Coroutines to signal data.
- Unit testing with JUnit

## Setup
- Requires latest version of [Android Studio](https://developer.android.com/studio)
An APK download link is also available via google drive https://drive.google.com/file/d/1g240Oe83rRQeqBPvg6-ifcCnSD7zrDez/view?usp=share_link

## Project Breakdown
```
Checkoff[com.andrew.checkoff]
 ┣ 📂core
 ┃ ┗ 📂 data
 ┃ ┗ 📂 database
 ┃ ┗ 📂 model
 ┃ ┗ 📂 nav
 ┃ ┗ 📂 network
 ┃ ┗ 📂 theme
 ┃ ┗ 📂 ui
 ┣ 📂feature
 ┃ ┗ 📂 add_edit_task
 ┃ ┗ 📂 todo
 ┣ 📂server
 ┣ 📜MainActivity
 ┣ 📜MainApplication
 ```
**Name**

**Responsibilities**

**Key classes and good examples**
|Name|Responsibilities|Key class and examples|
|--|--|--|
| `core:data` | Fetching app data remotely/locally |`TaskRepository`|
|`core:database`|Local database via Room|`TaskDao` `CheckoffDatabase`|
|`core:model`|Model classes used through app|`TaskItem`|
|`core:nav`|Navigation controller and routes|`CheckoffNavHost`|
|`core:network`|Making/handling remote requests|`ApiService`|
|`core:theme`|Material design colors used by app UI|`Theme`|
|`core:ui`|Reusuable UI components|`MaxLimitTextField`|
|`feature:`|One feature per screen. Each screen has a state, viewmodel, and screen UI|`TodoScreen` `TodoViewModel`|

Unit tests are inside `com.andrew.checkoff(test)`.

## Future Improvements
- [Modularization](https://developer.android.com/topic/modularization). Currently the overhead is not worth it due the size of the repository. However, if the app becomes larger and gains more features then it is worth investing time into in order to reduce compile time significantly. The app currently uses a network model, entity model, and domain model and have the files organized in such a way that will make it easier to split into modules in the future.
- [Better remote/local datasources](https://github.com/MobileNativeFoundation/Store). Given more time I would have liked to implement a more robust way to collect data. Using `Store5` makes it easy to sync remote data with local data (i.e. if remote and local don't match, update them so they do). Also makes it easier to allow the app to use local data given no wifi and then upload data when wifi is returned.
- [Firebase](https://firebase.google.com/). Connecting the app to firebase will allow us to monitor crashes and track user statitistics (i.e. phone model, OS version, etc)
- Github workflows. Maybe useful to add some scripts for PRs such that a linter job is ran and maybe some rules such as requiring all unit tests to pass. Can also add a script to upload app to App store easier.
- Write UI tests using espresso.
- Given more time I would make the repositories more safe by implementing a `Result` class that contains a `Success<T>` and `Failure` object, where T is a generic. I can then wrap the repository functions in a try catch and return the `Success` if it works or `Failure` if an exception occurs. This way the viewmodels can respond better to the output of the repository, i.e. failure messages can be displayed on a toast. 
- Calendar and setting dates for events. Given more time I would have liked to implement a bottom tab bar where the user can switch between the todo list and a calendar. Todo tasks can be assigned deadlines and the app will send out a notification for upcoming tasks.
