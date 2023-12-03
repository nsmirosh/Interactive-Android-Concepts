
# Android Concepts Examples

This is my collection of  code samples and demonstrations focusing on Android development, specifically Jetpack Compose and Kotlin coroutines. This repository is designed to illustrate both correct and incorrect usage of various Android development concepts.

## Contents
1. [Coroutines Examples](#coroutines-examples)
    - [Coroutine Cooperation](#coroutine-cooperation)
    - [`async{}` vs `launch{}](#async-vs-launch)
    - [`coroutineScope {}` influence](#coroutinescope--influence)
2. [Jetpack Compose Examples](#jetpack-compose-examples)
    - [`LaunchedEffect` and `rememberUpdatedState()`](#launched-effect-remember-updated-state)
    - [Usage of `rememberCoroutineScope()`](#remember-coroutine-scope)
3. [Progress Animation](#progress-animation)

## Coroutines Examples

### Coroutine Cooperation
Learn how `isActive` and `CancellationException` can be utilized for efficient coroutine management.
- **[Explanation Post](https://www.nickmirosh.com/post/do-you-know-what-coroutines-are-cooperative-means)**
- **[Code Sample](https://github.com/nsmirosh/Android-concepts-examples/blob/main/app/src/main/java/nick/mirosh/androidsamples/ui/coroutines/cooperative_coroutine/CooperativeCancellationScreen.kt)**  

https://github.com/nsmirosh/Android-concepts-examples/assets/5850601/c242ef98-5d7a-4903-8640-50f06afc4e33

### `async{}` vs `launch{}`
Explore the differences between `async{}` and `launch{}` and how scopes affect their execution.
- **[Explanation Post](https://www.nickmirosh.com/post/do-you-know-how-scopes-work-in-coroutines)**
- **[Code](https://github.com/nsmirosh/Android-concepts-examples/blob/main/app/src/main/java/nick/mirosh/androidsamples/ui/coroutines/async/AsyncComparisonScreen.kt)**

https://github.com/nsmirosh/MyAndroidSamples/assets/5850601/01b10316-f5e5-4b9c-bc40-959d94ef5b9d

### `coroutineScope {}` influence
Understand how `coroutineScope` influences coroutine execution.
- **[Explanation Post](https://www.nickmirosh.com/post/do-you-know-how-coroutinescope-works-in-coroutines)**
- **[Code](https://github.com/nsmirosh/Android-concepts-examples/blob/main/app/src/main/java/nick/mirosh/androidsamples/ui/coroutines/coroutine_scope/CoroutineScopeScreen.kt)**

https://github.com/nsmirosh/Android-concepts-examples/assets/5850601/a54319e4-3961-4985-8437-483f7b775e48


## Jetpack Compose Examples

### `LaunchedEffect` and `rememberUpdatedState()`
Demonstrates the usage of `LaunchedEffect` and `rememberUpdatedState` in Jetpack Compose.
- **[LinkedIn Explanation Post](https://www.linkedin.com/posts/nikolay-miroshnychenko-5838a25a_jetpackcompose-androiddevelopment-programming-activity-7118646129650528256-DtO1?utm_source=share&utm_medium=member_desktop)**
- **[Code](https://github.com/nsmirosh/Android-concepts-examples/blob/main/app/src/main/java/nick/mirosh/androidsamples/ui/side_effects/LaunchedEffectScreen.kt)**

https://github.com/nsmirosh/MyAndroidSamples/assets/5850601/a818c0d9-0d31-47be-afd6-778ad2272aaa

### Usage of `rememberCoroutineScope()`
Discover the use of `rememberCoroutineScope()` for canceling coroutine execution within Compose.
- **[Explanation Post](https://www.nickmirosh.com/post/are-you-using-coroutines-inside-your-composables-make-sure-to-use-remembercoroutinescope)**
- **[Code](https://github.com/nsmirosh/Android-concepts-examples/blob/main/app/src/main/java/nick/mirosh/androidsamples/ui/coroutines/remember_coroutine_scope/RememberCoroutineScope.kt)**

https://github.com/nsmirosh/MyAndroidSamples/assets/5850601/5d0a9793-a5e3-40be-9ed7-70e1682c496f

## Progress Animation 

### Canvas-Based Animation
A creative progress animation developed using `Canvas`, inspired by UX flame.
- **[Inspiration Source](https://www.youtube.com/watch?v=LbktxnviLKI&ab_channel=UXFLAME)**

https://github.com/nsmirosh/MyAndroidSamples/assets/5850601/44ec954c-cb32-4026-bd01-7b5e1521b7ae


