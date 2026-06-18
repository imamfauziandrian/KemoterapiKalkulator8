# BEP Renal Adjustment Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add GFR-based dose adjustment to the `BEP` regimen using the active selected GFR and show the adjusted output in the existing XML screen.

**Architecture:** Keep the change local to `BEP.java` and `activity_bep.xml`. Reuse the existing `selectedGfr` pattern from other regimens, add small static helpers for renal multipliers and rounded dose calculation, and update the layout note block to explain the new rules.

**Tech Stack:** Android Views/XML, Java, Material 3, JUnit 4, Gradle wrapper

---

### Task 1: Lock the renal rules in unit tests

**Files:**
- Modify: `app/src/test/java/com/kemoterapi/android/kalkulatorkemoterapi/ExampleUnitTest.java`
- Test: `app/src/test/java/com/kemoterapi/android/kalkulatorkemoterapi/ExampleUnitTest.java`

- [ ] **Step 1: Write the failing test**

```java
@Test
public void bepGfrDoseMultipliers_followRequestedRules() {
    assertEquals(1.0, BEP.hitungPengaliDosisBleomycinBerdasarkanGfr(50), 0.0);
    assertEquals(0.75, BEP.hitungPengaliDosisBleomycinBerdasarkanGfr(49), 0.0);
    assertEquals(0.5, BEP.hitungPengaliDosisBleomycinBerdasarkanGfr(9), 0.0);
}
```

- [ ] **Step 2: Run test to verify it fails**

Run: `./gradlew testDebugUnitTest --tests com.kemoterapi.android.kalkulatorkemoterapi.ExampleUnitTest.bepGfrDoseMultipliers_followRequestedRules`
Expected: fail because the new `BEP` helper methods do not exist yet.

- [ ] **Step 3: Extend the test for etoposide, cisplatin, and rounded adjusted dose**

```java
assertEquals(0.75, BEP.hitungPengaliDosisEtoposideBerdasarkanGfr(49), 0.0);
assertEquals(0.0, BEP.hitungPengaliDosisCisplatinBerdasarkanGfr(39), 0.0);
assertEquals(23, BEP.hitungDosisDisesuaikan(30, 0.75));
```

- [ ] **Step 4: Re-run the test target**

Run: `./gradlew testDebugUnitTest --tests com.kemoterapi.android.kalkulatorkemoterapi.ExampleUnitTest`
Expected: the BEP tests still fail until production code is added.

### Task 2: Implement BEP renal adjustment helpers and output

**Files:**
- Modify: `app/src/main/java/com/kemoterapi/android/kalkulatorkemoterapi/BEP.java`
- Test: `app/src/test/java/com/kemoterapi/android/kalkulatorkemoterapi/ExampleUnitTest.java`

- [ ] **Step 1: Add `selectedGfr` and apply it to all BEP drugs**

```java
double selectedGfr = GfrUtils.getSelectedGfr(gfr, gfrObese, isGfrObese);
```

- [ ] **Step 2: Add static helper methods for BEP renal multipliers**

```java
static double hitungPengaliDosisBleomycinBerdasarkanGfr(double gfr) { ... }
static double hitungPengaliDosisEtoposideBerdasarkanGfr(double gfr) { ... }
static double hitungPengaliDosisCisplatinBerdasarkanGfr(double gfr) { ... }
static int hitungDosisDisesuaikan(double dose, double multiplier) { ... }
```

- [ ] **Step 3: Add minimal display helpers for normal, reduced, and replacement output**

```java
private void setAdjustedDose(TextView view, double dose, String unit, double multiplier) { ... }
private void setAdjustedCisplatinDose(TextView view, double dose, double gfr) { ... }
```

- [ ] **Step 4: Run the BEP unit tests**

Run: `./gradlew testDebugUnitTest --tests com.kemoterapi.android.kalkulatorkemoterapi.ExampleUnitTest`
Expected: BEP assertions pass if the environment Java version is compatible.

### Task 3: Update the BEP screen copy and reset behavior

**Files:**
- Modify: `app/src/main/res/layout/activity_bep.xml`
- Modify: `app/src/main/java/com/kemoterapi/android/kalkulatorkemoterapi/BEP.java`

- [ ] **Step 1: Add a GFR adjustment note block to the layout**

```xml
<TextView
    android:text="Penyesuaian dosis berdasarkan GFR"
    ... />
```

- [ ] **Step 2: Reset BEP output views to defaults**

```java
((TextView) findViewById(R.id.bleomycin)).setText(buildDoseText(30, "units"));
((TextView) findViewById(R.id.etoposide)).setText("0");
((TextView) findViewById(R.id.cisplatin)).setText("0");
```

- [ ] **Step 3: Assemble the app**

Run: `./gradlew assembleDebug`
Expected: debug APK builds if the environment Java version is compatible with AGP 8.13.2.
