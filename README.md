<img width="834" alt="Screenshot 2024-11-05 at 17 01 28" src="https://github.com/user-attachments/assets/cb658579-fd4a-4d4a-a39f-c0c3b1c5dd0c">  

# Interactive Android Concepts Examples

This is a collection of code samples and demonstrations focusing on Android development,
specifically Jetpack Compose and Kotlin coroutines.

The goal of this repository is to visually illustrate the usage of hard-to-understand Android and Kotlin concepts. The
samples aim to be interactive.

All of the video illustrations have corresponding code representations.

Feel free to play around with these examples to get a better understanding of each of the concepts.

## Contribution guidelines

You're welcome to contribute your own code samples.

To contribute:

1. Find a concept that is not straightforward and not yet covered.
2. Contact [me](https://www.linkedin.com/in/nickmirosh/) to make sure that your MR will get accepted
   before you start working on it.
3. Make sure that what you want to contribute is an interactive and / or visual representation of a
   certain Android concept.
4. Write a detailed post with an explanation of what's going on in your example.
   Consult [this screen](https://github.com/nsmirosh/Interactive-Android-Concepts/blob/main/app/src/main/java/nick/mirosh/androidsamples/ui/coroutines/remember_coroutine_scope/RememberCoroutineScopeScreen.kt)
   for reference and see the constant `EXPLANATION_LINK`. 
5. Open an MR with your changes.

## Contents

1. [Coroutines Examples](#coroutines-examples)
    - Coroutine cooperation. Building a cooperative coroutine. [Link](#coroutine-cooperation)
    - The difference between `async {}` and `launch{}`. How it influences coroutine execution and
      cancellation. [Link](#async-vs-launch)
    - How `coroutineScope {}` influences the order of execution in
      coroutines [Link](#coroutinescope--influence)
    - Why is `CancellationException` special? How exception propagation
      works. [Link](#cancellationexception-vs-ordinary-exceptions-exception-propagation)
2. [Jetpack Compose Examples](#jetpack-compose-examples)
    - Modifier precedence importance. A Drag-Drop example of modifier
      usage. [Link](#drag-drop-modifier)
    - Demonstration of the usage of `rememberUpdatedState()` for updating a scheduled
      lambda [Link](#launchedeffect-and-rememberupdatedstate)
    - How `rememberCoroutineScope` can be used to cancel the execution of a
      coroutine [Link](#remember-coroutine-scope)
3. [Other Android Concepts](#other-android-concepts)
    - Multiple processes. How you can have your app run in different
      processes. [Link](#multiple-processes)
4. [Progress animation](#canvas-based-animation)

## Coroutines Examples

### Coroutine Cooperation

Learn how `isActive` and `CancellationException` can be utilized for efficient coroutine
cooperation.

- **[Explanation Post](https://www.nickmirosh.com/post/do-you-know-what-coroutines-are-cooperative-means)**
- **[Code Sample](https://github.com/nsmirosh/Android-concepts-examples/blob/main/app/src/main/java/nick/mirosh/androidsamples/ui/coroutines/cooperative_coroutine/CooperativeCancellationScreen.kt)**   
 
  https://github.com/nsmirosh/Android-concepts-examples/assets/5850601/c242ef98-5d7a-4903-8640-50f06afc4e33

### `async{}` vs `launch{}`

Explore the differences between `async{}` and `launch{}` and how scopes affect their execution and
cancellation.

- **[Explanation Post](https://www.nickmirosh.com/post/do-you-know-how-scopes-work-in-coroutines)**
- **[Code](https://github.com/nsmirosh/Android-concepts-examples/blob/main/app/src/main/java/nick/mirosh/androidsamples/ui/coroutines/async/AsyncComparisonScreen.kt)**

https://github.com/nsmirosh/MyAndroidSamples/assets/5850601/01b10316-f5e5-4b9c-bc40-959d94ef5b9d

### `coroutineScope {}` influence

Understand how `coroutineScope` influences coroutine order of execution.

- **[Explanation Post](https://www.nickmirosh.com/post/do-you-know-how-coroutinescope-works-in-coroutines)**
- **[Code](https://github.com/nsmirosh/Android-concepts-examples/blob/main/app/src/main/java/nick/mirosh/androidsamples/ui/coroutines/coroutine_scope/CoroutineScopeScreen.kt)**

https://github.com/nsmirosh/Android-concepts-examples/assets/5850601/a54319e4-3961-4985-8437-483f7b775e48

### `CancellationException` vs ordinary exceptions. Exception propagation.

Learn why `CancellationException` is special in Kotlin coroutines and how exceptions are propagated
from child to parent.

- **[Explanation Post](https://www.nickmirosh.com/post/do-you-know-why-cancellationexception-is-special-in-coroutines)**
- **[Code](https://github.com/nsmirosh/Android-concepts-examples/blob/main/app/src/main/java/nick/mirosh/androidsamples/ui/coroutines/exceptions/different_exceptions/DifferentExceptionsScreen.kt)**

https://github.com/nsmirosh/Android-concepts-examples/assets/5850601/3b3a4bce-676c-45f4-ac9c-5d9e60648422

## Jetpack Compose Examples

### `LaunchedEffect` and `rememberUpdatedState()`

Demonstrates the usage of `LaunchedEffect` and `rememberUpdatedState` in Jetpack Compose.

- [Explanation Post](https://www.nickmirosh.com/post/are-you-aware-of-the-pitfalls-of-launchedeffect-in-jetpack-compose)
- [Code](https://github.com/nsmirosh/Android-concepts-examples/blob/main/app/src/main/java/nick/mirosh/androidsamples/ui/side_effects/LaunchedEffectScreen.kt)

https://github.com/nsmirosh/MyAndroidSamples/assets/5850601/a818c0d9-0d31-47be-afd6-778ad2272aaa

### Usage of `rememberCoroutineScope()`

Discover the use of `rememberCoroutineScope()` for canceling coroutine execution within Compose.

- [Explanation Post](https://www.nickmirosh.com/post/are-you-using-coroutines-inside-your-composables-make-sure-to-use-remembercoroutinescope)
- [Code](https://github.com/nsmirosh/Android-concepts-examples/blob/main/app/src/main/java/nick/mirosh/androidsamples/ui/coroutines/remember_coroutine_scope/RememberCoroutineScope.kt)

https://github.com/nsmirosh/MyAndroidSamples/assets/5850601/5d0a9793-a5e3-40be-9ed7-70e1682c496f

### Drag-drop `Modifier`

You can drag and drop the two modifiers in order to discover how it will influence the Composable.

- [Explanation Post](https://www.nickmirosh.com/post/do-you-know-how-to-position-your-modifiers-in-jetpack-compose)
- [Code](https://github.com/nsmirosh/Interactive-Android-Concepts/blob/main/app/src/main/java/nick/mirosh/androidsamples/ui/jetpack_compose/drag_drop_modifier/DragDropModifierScreen.kt)

https://github.com/nsmirosh/Interactive-Android-Concepts/assets/5850601/a2086b01-26a1-458d-94e1-1594f75d5653

## Other Android Concepts

### Multiple processes

Two services are launched in different processes and both of the have a RuntimeException().   
One service crashes the app and the one that is in the separate process does not.

- [Explanation Post](https://www.nickmirosh.com/post/is-it-possible-to-have-a-runtimeexception-in-your-android-app-and-have-it-crash)
- [Code](https://github.com/nsmirosh/Interactive-Android-Concepts/blob/main/app/src/main/java/nick/mirosh/androidsamples/ui/background_processing/multiple_processes/ProcessesScreen.kt)
 
https://github.com/nsmirosh/Interactive-Android-Concepts/assets/5850601/c304a65c-3ad0-4e54-a9f2-e628832c891f

## Progress Animation

### Canvas-Based Animation

A creative progress animation developed using `Canvas`, inspired by UX flame.

- **[Inspiration Source](https://www.youtube.com/watch?v=LbktxnviLKI&ab_channel=UXFLAME)**

https://github.com/nsmirosh/MyAndroidSamples/assets/5850601/44ec954c-cb32-4026-bd01-7b5e1521b7ae
