# AGENTS.md

## Structure
- Single-module Android app. App code lives in `app/src/main/java/com/kemoterapi/android/kalkulatorkemoterapi/`; `MainActivity` is the launcher and opens the calculator screens with explicit `Intent`s.
- `app/src/main/res/layout/` holds the XML screens. `ui/info/` contains the AUC help screen (`AucInfoActivity` + fragments).
- Keep XML view IDs stable. The Java code uses `findViewById` and `android:onClick`; if an ID changes, update the matching Activity in the same change.

## Build and Test
- Use the Gradle wrapper (`./gradlew`), not a system Gradle install.
- Common verification: `./gradlew assembleDebug` for layout/theme changes, `./gradlew test` for JVM unit tests.
- `./gradlew connectedAndroidTest` exists, but `app/src/androidTest/java/com/kemoterapi/android/kalkulatorkemoterapi/ExampleInstrumentedTest.java` still asserts the old package name `com.harinugroho.android.kemoterapikalkulator`, so that suite will fail until it is updated.

## UI and Themes
- The app already uses Material 3 (`Theme.Material3.DayNight.NoActionBar`) with tokens in `app/src/main/res/values/colors.xml` and `values-night/themes.xml`; preserve the light/dark token split.
- This app is Activity-based XML, not Compose.

## Repo Quirks
- Root `build.gradle` still includes `jcenter()`, so Gradle emits deprecation warnings.
- Ignore generated or local files such as `build/`, `app/release/`, `.gradle/`, `.idea/`, and `.DS_Store`.
