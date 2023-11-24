# distilled Tech Test Solution

*Ensure you have the latest version of Android studio (Electric Eel preferrably) before cloning this repository.*

This is an android application that displays a list of movies. 
At the top of the screen, there is a switch that can be used to toggle the sorting arrangment of the list.

<img src="https://firebasestorage.googleapis.com/v0/b/memo-24031.appspot.com/o/WhatsApp%20Image%202023-05-07%20at%2023.19.40.jpeg?alt=media&token=573d68d3-5277-4ffb-86ab-7d1d2d33db7f" width="300" height="650" />

## Architecture

While developing this application, I used the **Google recommended** [android app architecture](https://developer.android.com/topic/architecture#recommended-app-arch) 
as a guideline to organize the code base into three distint layers: UI layer, Domain layer and Data layer.

The UI layer of the app is written completely in Jetpack Compose. There are no Fragments, only Composable screens.

## Module Structure

I also followed the **Google recommended** [modularization structure](https://developer.android.com/topic/modularization/patterns#types-of-modules),
and I grouped the code base into three main modules: 
1) ```app```: This is the container module of the application. Its sole duty is to display all features of the application
2) ```core```: The core module serves as the base module. It contains code that can be resued across any part of the code base.
3) ```feature```: The feature module contains all features of the application.

## Test

The code base contains unit tests and UI test for the Composable screens. These can be found here:

UI tests: https://github.com/Jeff-Emuveyan/distilled/blob/master/feature/movies/src/androidTest/java/com/example/movies/MoviesScreenTest.kt

Domain layer unit tests: https://github.com/Jeff-Emuveyan/distilled/blob/master/core/domain/src/test/java/com/example/domain/FormatMovieListResponseUseCaseTest.kt

Repository unit tests: https://github.com/Jeff-Emuveyan/distilled/blob/master/core/data/src/test/java/com/example/data/repository/MovieRepositoryTest.kt

## Something extra! 😁

It is very important to monitor the performance of an android app so that issues such as slow startup time and UI jank can be spotted early and resolved.

Because of this, I wrote a [macrobenchmark](https://developer.android.com/topic/performance/benchmarking/macrobenchmark-overview) test to monitor the startup time
of the app.

This test can be found here:

https://github.com/Jeff-Emuveyan/distilled/blob/master/benchmark/src/main/java/com/example/benchmark/DistilledAppStartupBenchmark.kt


