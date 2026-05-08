# Material 3 Migration Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Migrate the existing XML + Java Android app to a modern Material 3 look without changing the app flow or calculation logic.

**Architecture:** Keep the current Activity-based navigation and all Java calculation code. Replace the old Material Components 2 theme with a Material 3 theme, then refresh each layout to use Material 3 widgets and spacing. Preserve existing view IDs so the Java code continues to work unchanged wherever possible.

**Tech Stack:** Android XML layouts, Java, AndroidX AppCompat, Material Components for Android (`com.google.android.material`), existing Activities.

---

## File map

- `app/src/main/res/values/colors.xml` — Material 3 palette tokens.
- `app/src/main/res/values/themes.xml` — light Material 3 app theme.
- `app/src/main/res/values-night/themes.xml` — dark Material 3 app theme.
- `app/src/main/res/layout/activity_main.xml` — main menu refresh.
- `app/src/main/res/layout/activity_cisplatin.xml` — calculator screen refresh.
- `app/src/main/res/layout/activity_docetaxel_carboplatin.xml` — calculator screen refresh.
- `app/src/main/res/layout/activity_emaco.xml` — calculator screen refresh.
- `app/src/main/res/layout/activity_emaep.xml` — calculator screen refresh.
- `app/src/main/res/layout/activity_gemcitabin_carboplatin.xml` — calculator screen refresh.
- `app/src/main/res/layout/activity_methotrexate.xml` — calculator screen refresh.
- `app/src/main/res/layout/activity_paclitaxel_carboplatin.xml` — calculator screen refresh.

## Layout migration rules

Use the same conversion pattern on every screen:
- Root container: `androidx.core.widget.NestedScrollView` or `ScrollView` with full-screen height.
- Section container: `LinearLayout` with vertical orientation, consistent padding, and spacing.
- Input fields: wrap each numeric field in `com.google.android.material.textfield.TextInputLayout`, and keep the existing view ID on the inner `TextInputEditText`.
- Action buttons: replace plain `Button` with `com.google.android.material.button.MaterialButton`.
- Results: group outputs inside `com.google.android.material.card.MaterialCardView` sections.
- Do not rename any IDs that are referenced from Java unless the Java file is updated in the same task.
- Keep the app functional in both light and dark mode.

## Task 1: Establish the Material 3 theme foundation

**Files:**
- Modify: `app/src/main/res/values/colors.xml`
- Modify: `app/src/main/res/values/themes.xml`
- Modify: `app/src/main/res/values-night/themes.xml`

- [ ] **Step 1: Replace the old purple/teal palette with Material 3 tokens**

Use these exact palette values in `colors.xml`:

```xml
<resources>
    <color name="md_theme_light_primary">#6750A4</color>
    <color name="md_theme_light_onPrimary">#FFFFFF</color>
    <color name="md_theme_light_primaryContainer">#EADDFF</color>
    <color name="md_theme_light_onPrimaryContainer">#21005D</color>
    <color name="md_theme_light_secondary">#625B71</color>
    <color name="md_theme_light_onSecondary">#FFFFFF</color>
    <color name="md_theme_light_secondaryContainer">#E8DEF8</color>
    <color name="md_theme_light_onSecondaryContainer">#1D192B</color>
    <color name="md_theme_light_tertiary">#7D5260</color>
    <color name="md_theme_light_onTertiary">#FFFFFF</color>
    <color name="md_theme_light_surface">#FFFBFE</color>
    <color name="md_theme_light_onSurface">#1C1B1F</color>
    <color name="md_theme_light_background">#FFFBFE</color>
    <color name="md_theme_light_onBackground">#1C1B1F</color>
    <color name="md_theme_light_outline">#79747E</color>
    <color name="md_theme_light_error">#B3261E</color>
    <color name="md_theme_light_onError">#FFFFFF</color>

    <color name="md_theme_dark_primary">#D0BCFF</color>
    <color name="md_theme_dark_onPrimary">#381E72</color>
    <color name="md_theme_dark_primaryContainer">#4F378B</color>
    <color name="md_theme_dark_onPrimaryContainer">#EADDFF</color>
    <color name="md_theme_dark_secondary">#CCC2DC</color>
    <color name="md_theme_dark_onSecondary">#332D41</color>
    <color name="md_theme_dark_secondaryContainer">#4A4458</color>
    <color name="md_theme_dark_onSecondaryContainer">#E8DEF8</color>
    <color name="md_theme_dark_tertiary">#EFB8C8</color>
    <color name="md_theme_dark_onTertiary">#492532</color>
    <color name="md_theme_dark_surface">#1C1B1F</color>
    <color name="md_theme_dark_onSurface">#E6E1E5</color>
    <color name="md_theme_dark_background">#1C1B1F</color>
    <color name="md_theme_dark_onBackground">#E6E1E5</color>
    <color name="md_theme_dark_outline">#938F99</color>
    <color name="md_theme_dark_error">#F2B8B5</color>
    <color name="md_theme_dark_onError">#601410</color>
</resources>
```

- [ ] **Step 2: Switch the app theme parent to Material 3**

Replace the existing theme block in `themes.xml` with:

```xml
<style name="Theme.KemoterapiKalkulator" parent="Theme.Material3.DayNight.NoActionBar">
    <item name="colorPrimary">@color/md_theme_light_primary</item>
    <item name="colorOnPrimary">@color/md_theme_light_onPrimary</item>
    <item name="colorPrimaryContainer">@color/md_theme_light_primaryContainer</item>
    <item name="colorOnPrimaryContainer">@color/md_theme_light_onPrimaryContainer</item>
    <item name="colorSecondary">@color/md_theme_light_secondary</item>
    <item name="colorOnSecondary">@color/md_theme_light_onSecondary</item>
    <item name="colorSecondaryContainer">@color/md_theme_light_secondaryContainer</item>
    <item name="colorOnSecondaryContainer">@color/md_theme_light_onSecondaryContainer</item>
    <item name="colorTertiary">@color/md_theme_light_tertiary</item>
    <item name="colorOnTertiary">@color/md_theme_light_onTertiary</item>
    <item name="android:colorBackground">@color/md_theme_light_background</item>
    <item name="colorSurface">@color/md_theme_light_surface</item>
    <item name="colorOnSurface">@color/md_theme_light_onSurface</item>
    <item name="colorOutline">@color/md_theme_light_outline</item>
    <item name="colorError">@color/md_theme_light_error</item>
    <item name="colorOnError">@color/md_theme_light_onError</item>
</style>
```

Mirror the same structure in `values-night/themes.xml`, swapping to the dark tokens.

- [ ] **Step 3: Verify the app still resolves the theme cleanly**

Run:
```bash
./gradlew assembleDebug
```
Expected: `BUILD SUCCESSFUL`.

- [ ] **Step 4: Commit the theme foundation**

```bash
git add app/src/main/res/values/colors.xml app/src/main/res/values/themes.xml app/src/main/res/values-night/themes.xml
git commit -m "feat: switch app theme to material 3"
```

## Task 2: Redesign the main menu screen

**Files:**
- Modify: `app/src/main/res/layout/activity_main.xml`

- [ ] **Step 1: Replace the `RelativeLayout` root with a modern scrollable container**

Use this structure:

```xml
<androidx.core.widget.NestedScrollView ...>
    <LinearLayout
        android:orientation="vertical"
        android:padding="24dp"
        ...>

        <com.google.android.material.card.MaterialCardView ...>
            <LinearLayout ...>
                <TextView ... android:text="Perhitungan Dosis Kemoterapi" />
                <TextView ... android:text="Ginekologi Onkologi" />
                <TextView ... android:text="Pilih regimen yang ingin dihitung" />
            </LinearLayout>
        </com.google.android.material.card.MaterialCardView>

        <com.google.android.material.button.MaterialButton
            android:id="@+id/pacliCarbo"
            android:onClick="klikPacliCarbo"
            android:text="Paclitaxel Carboplatin" />

        <com.google.android.material.button.MaterialButton
            android:id="@+id/cisplatin"
            android:onClick="klikCisplatin"
            android:text="Cisplatin" />

        <com.google.android.material.button.MaterialButton
            android:id="@+id/gemciCarbo"
            android:onClick="klikGemciCarbo"
            android:text="Gemcitabin Carboplatin" />

        <com.google.android.material.button.MaterialButton
            android:id="@+id/doceCarbo"
            android:onClick="klikDoceCarbo"
            android:text="Docetaxel Carboplatin" />

        <com.google.android.material.button.MaterialButton
            android:id="@+id/mTX"
            android:onClick="klikMTX"
            android:text="Methotrexate" />

        <com.google.android.material.button.MaterialButton
            android:id="@+id/emaco"
            android:onClick="klikEmaco"
            android:text="EMACO" />

        <com.google.android.material.button.MaterialButton
            android:id="@+id/emaep"
            android:onClick="klikEmaEP"
            android:text="EMA-EP" />

    </LinearLayout>
</androidx.core.widget.NestedScrollView>
```

Keep every existing button ID and `onClick` method name unchanged so `MainActivity.java` does not need edits.

- [ ] **Step 2: Verify the main menu still opens every Activity**

Run:
```bash
./gradlew assembleDebug
```
Expected: `BUILD SUCCESSFUL`.

- [ ] **Step 3: Commit the main menu redesign**

```bash
git add app/src/main/res/layout/activity_main.xml
git commit -m "feat: refresh main menu with material 3"
```

## Task 3: Refresh the simpler calculator screens

**Files:**
- Modify: `app/src/main/res/layout/activity_cisplatin.xml`
- Modify: `app/src/main/res/layout/activity_gemcitabin_carboplatin.xml`
- Modify: `app/src/main/res/layout/activity_methotrexate.xml`

- [ ] **Step 1: Convert each screen to the shared Material 3 form pattern**

For each file, keep all IDs used by Java exactly the same, but change the widget types and structure:
- root to `NestedScrollView`
- body to vertical `LinearLayout`
- input fields to `TextInputLayout` + `TextInputEditText`
- action buttons to `MaterialButton`
- output groups to `MaterialCardView`

Use this input pattern for every numeric field:

```xml
<com.google.android.material.textfield.TextInputLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Usia">

    <com.google.android.material.textfield.TextInputEditText
        android:id="@+id/usia"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="number"
        android:imeOptions="actionNext" />
</com.google.android.material.textfield.TextInputLayout>
```

Use the same idea for `beratBadan`, `tinggiBadan`, and `serumKreatinin` in each file. Keep the result `TextView` IDs unchanged so the existing `findViewById(...)` calls still work.

- [ ] **Step 2: Keep the calculation flow readable with card-based result sections**

Group each screen’s calculated values into a result card with short labels and consistent spacing. Do not rename output IDs like `IndeksMassaTubuh`, `LuasPermukaanTubuh`, or any dose/result fields that Java updates.

- [ ] **Step 3: Run a build after the three-screen conversion**

Run:
```bash
./gradlew assembleDebug
```
Expected: `BUILD SUCCESSFUL`.

- [ ] **Step 4: Commit the shared calculator refresh**

```bash
git add app/src/main/res/layout/activity_cisplatin.xml app/src/main/res/layout/activity_gemcitabin_carboplatin.xml app/src/main/res/layout/activity_methotrexate.xml
git commit -m "feat: modernize calculator screens with material 3"
```

## Task 4: Refresh the more complex calculator screens

**Files:**
- Modify: `app/src/main/res/layout/activity_paclitaxel_carboplatin.xml`
- Modify: `app/src/main/res/layout/activity_docetaxel_carboplatin.xml`

- [ ] **Step 1: Convert both screens to Material 3 while preserving all Java-facing IDs**

Apply the same structure as Task 3, but keep the larger result areas intact. Preserve IDs such as `usia`, `beratBadan`, `tinggiBadan`, `serumKreatinin`, `IndeksMassaTubuh`, `LuasPermukaanTubuh`, `GFR_Normal`, `GFR_Obese`, `paclitaxel`, `carboplatin`, `carboplatinObese`, `carboplatin4060`, and `carboplatin40` in the Paclitaxel screen, and all equivalent IDs in the Docetaxel screen.

Use `MaterialButton` for both `Hitung` and `Reset` actions so the visual language matches the new theme.

- [ ] **Step 2: Keep the calculation outputs in clearly separated cards**

Split the screen into at least three visible regions:
1. input card
2. action button row
3. result card(s)

This keeps the dense medical output readable while still feeling like Material 3.

- [ ] **Step 3: Verify the two large layouts still compile cleanly**

Run:
```bash
./gradlew assembleDebug
```
Expected: `BUILD SUCCESSFUL`.

- [ ] **Step 4: Commit the larger calculator refresh**

```bash
git add app/src/main/res/layout/activity_paclitaxel_carboplatin.xml app/src/main/res/layout/activity_docetaxel_carboplatin.xml
git commit -m "feat: modernize complex calculator screens"
```

## Task 5: Refresh EMACO and EMA-EP

**Files:**
- Modify: `app/src/main/res/layout/activity_emaco.xml`
- Modify: `app/src/main/res/layout/activity_emaep.xml`

- [ ] **Step 1: Apply the same Material 3 structure to both oncology regimen screens**

Convert each file to the shared layout pattern used above:
- `NestedScrollView` root
- padded vertical `LinearLayout`
- `TextInputLayout` + `TextInputEditText` inputs
- `MaterialButton` actions
- `MaterialCardView` result sections

Preserve all IDs used by the Java code; do not rename any output fields or input fields.

- [ ] **Step 2: Tune spacing for the denser output sections**

These screens have more result text than the menu screens, so keep the result cards visually separated with clear headings and adequate vertical spacing. The goal is readability first, decoration second.

- [ ] **Step 3: Run the final build check after all screen migrations**

Run:
```bash
./gradlew assembleDebug
```
Expected: `BUILD SUCCESSFUL`.

- [ ] **Step 4: Commit the remaining screen refreshes**

```bash
git add app/src/main/res/layout/activity_emaco.xml app/src/main/res/layout/activity_emaep.xml
git commit -m "feat: finish material 3 migration for remaining screens"
```

## Task 6: Final smoke test and cleanup

**Files:**
- Verify: `app/src/main/java/com/kemoterapi/android/kalkulatorkemoterapi/*.java`
- Verify: all modified XML/theme files above

- [ ] **Step 1: Launch the app and open every screen once**

Run:
```bash
./gradlew installDebug
```
Then open the app and verify:
- the main menu loads with the new Material 3 design
- each menu button opens the correct Activity
- each calculator screen renders without clipped text
- each `Hitung` and `Reset` button still works
- light and dark mode both look acceptable

- [ ] **Step 2: Confirm Java did not need runtime fixes**

If any `findViewById(...)` call fails because an ID changed, restore the original ID in XML instead of changing the Java logic unless there is a strong reason to do so.

- [ ] **Step 3: Commit the completed migration**

```bash
git add app/src/main/java/com/kemoterapi/android/kalkulatorkemoterapi/*.java app/src/main/res/values/colors.xml app/src/main/res/values/themes.xml app/src/main/res/values-night/themes.xml app/src/main/res/layout/*.xml
git commit -m "feat: complete material 3 migration"
```

## Success criteria
- The app uses a Material 3 theme.
- The main menu looks modern and clearer.
- Every existing screen remains functional with the current Java logic.
- Light and dark mode both render well.
- The project builds successfully after the migration.
