# Android concepts examples

This is a collection of various code samples and demonstrations of Jetpack Compose and Kotin coroutines. 
The app is intended to demonstrate the correct and wrong usage of certain concepts.


## Coroutines examples

### Coroutine cooperation. How can `isActive` and `CancellationException` be used to quit a coroutine.

[Explanation post](https://www.nickmirosh.com/post/do-you-know-what-coroutines-are-cooperative-means)
[Code](https://github.com/nsmirosh/Android-concepts-examples/blob/main/app/src/main/java/nick/mirosh/androidsamples/ui/coroutines/cooperative_coroutine/CooperativeCancellationScreen.kt)

https://github.com/nsmirosh/Android-concepts-examples/assets/5850601/c242ef98-5d7a-4903-8640-50f06afc4e33

### How `rememberCoroutineScope()` can be used to cancel coroutine execution in Composition

[Explanation post](https://www.nickmirosh.com/post/are-you-using-coroutines-inside-your-composables-make-sure-to-use-remembercoroutinescope)
[Code](https://github.com/nsmirosh/Android-concepts-examples/blob/main/app/src/main/java/nick/mirosh/androidsamples/ui/coroutines/remember_coroutine_scope/RememberCoroutineScope.kt)

https://github.com/nsmirosh/MyAndroidSamples/assets/5850601/5d0a9793-a5e3-40be-9ed7-70e1682c496f

### Demonstration of the usage `async{}` vs regular `launch{}` and how scopes control their execution

[Explanation post](https://www.nickmirosh.com/post/do-you-know-how-scopes-work-in-coroutines)

https://github.com/nsmirosh/MyAndroidSamples/assets/5850601/01b10316-f5e5-4b9c-bc40-959d94ef5b9d

### Demonstration of how `coroutineScope` influences the execution of coroutines

[Explanation post on LinkedIn](https://www.linkedin.com/posts/nikolay-miroshnychenko-5838a25a_androiddevelopment-coroutines-kotlin-activity-7132272768728870912-BAcY?utm_source=share&utm_medium=member_desktop)

https://github.com/nsmirosh/Android-concepts-examples/assets/5850601/a54319e4-3961-4985-8437-483f7b775e48


## Jetpack Compose Side Effects
### `LaunchedEffect` and `rememberUpdatedState()` usage

Contains the demonstration of usage of `LaunchedEffect` and `rememberUpdatedState` in Jetpack
Compose.

[Explanation post on LinkedIn](https://www.linkedin.com/posts/nikolay-miroshnychenko-5838a25a_jetpackcompose-androiddevelopment-programming-activity-7118646129650528256-DtO1?utm_source=share&utm_medium=member_desktop)

https://github.com/nsmirosh/MyAndroidSamples/assets/5850601/a818c0d9-0d31-47be-afd6-778ad2272aaa


## Progress Animation 
### Usage of `Canvas` in order to build an animation 

I decided to build a cool progress animation using `Canvas`. The animation is located in the main menu under "Animation" section.
The idea and the design belong completely to UX flame - https://www.youtube.com/watch?v=LbktxnviLKI&ab_channel=UXFLAME.

https://github.com/nsmirosh/MyAndroidSamples/assets/5850601/44ec954c-cb32-4026-bd01-7b5e1521b7ae




