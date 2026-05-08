# Material 3 Migration Design

**Goal:** Migrate the existing Android app from Material Components 2 styling to a modern Material 3 look while keeping the current XML + Java architecture.

**Architecture:** Keep the existing Activity-based navigation and Java click handlers intact. Update the app theme to Material 3, then refresh each XML layout to use Material 3 widgets, spacing, typography, and surface treatment. The main screen becomes the primary visual reset point, and the calculator/detail screens follow the same visual language so the app feels consistent end to end.

**Tech Stack:** Android XML layouts, Java, AndroidX AppCompat, Material Components for Android (`com.google.android.material`), existing Activities.

---

## Scope

### In scope
- Replace the app theme from `Theme.MaterialComponents.*` to a Material 3 theme.
- Modernize the main menu layout.
- Update all calculator screens to use Material 3 components where appropriate.
- Keep all current navigation and calculations in Java.
- Preserve the existing app structure and screen flow.

### Out of scope
- Migrating to Jetpack Compose.
- Rewriting calculation logic.
- Changing the app’s feature set.
- Adding backend services or new business rules.

## File Structure

### Theme and shared resources
- `app/src/main/res/values/themes.xml` — Material 3 day theme.
- `app/src/main/res/values-night/themes.xml` — Material 3 night theme.
- `app/src/main/res/values/colors.xml` — update palette if needed for Material 3 contrast and surfaces.

### Screens
- `app/src/main/res/layout/activity_main.xml` — redesign main menu into a cleaner Material 3 menu.
- `app/src/main/res/layout/activity_cisplatin.xml`
- `app/src/main/res/layout/activity_docetaxel_carboplatin.xml`
- `app/src/main/res/layout/activity_emaco.xml`
- `app/src/main/res/layout/activity_emaep.xml`
- `app/src/main/res/layout/activity_gemcitabin_carboplatin.xml`
- `app/src/main/res/layout/activity_methotrexate.xml`
- `app/src/main/res/layout/activity_paclitaxel_carboplatin.xml`

### Java
- `app/src/main/java/com/kemoterapi/android/kalkulatorkemoterapi/MainActivity.java` — no navigation logic changes expected unless layout IDs change.
- Other Activity classes — only if a layout ID or widget type change requires a small Java update.

## UI Strategy

### Main screen
- Replace the current stacked `RelativeLayout` button list with a Material 3-friendly layout using a vertical container, consistent padding, and large touch targets.
- Use `MaterialButton` for action buttons.
- Introduce a clear title, supporting subtitle, and a card-like grouping for the menu actions.
- Keep the app entry flow simple: one screen, one primary action group.

### Calculator screens
- Use Material 3 text fields, buttons, and cards where input/output is shown.
- Standardize spacing, corner radius, and typography.
- Prefer surface-based grouping for input sections and result sections.
- Keep labels explicit and readable for clinical use.

## Behavior
- All existing `onClick` navigation methods remain functional.
- Screen transitions remain Activity-based and unchanged.
- Any layout ID changes must be mirrored in the matching Java file before the build is considered complete.

## Error Handling
- If a migrated layout removes or renames a view referenced in Java, the app must be updated before merge to avoid `NullPointerException` or `IllegalStateException` at runtime.
- If a Material 3 widget requires a theme attribute not yet defined, add the missing theme tokens rather than falling back to deprecated Material 2 attributes.
- Preserve readability and contrast for both light and dark mode.

## Testing
- Build the app after each group of layout changes to catch XML/theme regressions early.
- Launch the main screen and each calculator screen to verify:
  - buttons render correctly
  - text remains readable
  - navigation still works
  - no missing view IDs or crashes on open
- Check light and night theme rendering.
- Validate at least one representative input/output flow per calculator screen.

## Implementation Order
1. Update the app theme to Material 3.
2. Refresh shared colors and surface tokens if needed.
3. Redesign the main menu screen.
4. Migrate each calculator screen layout to Material 3 patterns.
5. Fix any Java references if widget IDs changed.
6. Run the app and verify navigation and calculations.

## Success Criteria
- The app uses a Material 3 theme.
- The main menu looks modern and consistent with Material 3.
- All existing screens still work with the current Java logic.
- The UI feels cohesive across light and dark mode.
- The project builds successfully after migration.
