# Temporary Form Prefill Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Keep `usia`, `beratBadan`, `tinggiBadan`, and `serumKreatinin` values in memory while the app is open, prefill them across all dose calculator forms, and clear them on reset.

**Architecture:** Add one small in-memory cache class that owns the four shared values. Each calculator `Activity` binds its inputs on `onCreate` so the cache restores saved text and listens for edits. Each `Reset` handler clears the cache before emptying the visible fields.

**Tech Stack:** Android activities, `EditText`, `TextWatcher`, Java, Gradle wrapper

---

### Task 1: Add shared in-memory cache

**Files:**
- Create: `app/src/main/java/com/kemoterapi/android/kalkulatorkemoterapi/PatientInputCache.java`

- [ ] **Step 1: Write the cache helper**

```java
final class PatientInputCache {
    static void bind(EditText usiaField, EditText beratField, EditText tinggiField, EditText serumField)
    static void clear()
}
```

- [ ] **Step 2: Make it restore and track edits**

```java
// restore cached values into each field
// attach TextWatcher listeners that keep the static values in sync
```

### Task 2: Wire calculator screens to the cache

**Files:**
- Modify: `app/src/main/java/com/kemoterapi/android/kalkulatorkemoterapi/Cisplatin.java`
- Modify: `app/src/main/java/com/kemoterapi/android/kalkulatorkemoterapi/GemcitabinCarboplatin.java`
- Modify: `app/src/main/java/com/kemoterapi/android/kalkulatorkemoterapi/DocetaxelCarboplatin.java`
- Modify: `app/src/main/java/com/kemoterapi/android/kalkulatorkemoterapi/EMACO.java`
- Modify: `app/src/main/java/com/kemoterapi/android/kalkulatorkemoterapi/EMAEP.java`
- Modify: `app/src/main/java/com/kemoterapi/android/kalkulatorkemoterapi/Methotrexate.java`
- Modify: `app/src/main/java/com/kemoterapi/android/kalkulatorkemoterapi/PaclitaxelCarboplatin.java`

- [ ] **Step 1: Bind the fields in `onCreate()`**

```java
PatientInputCache.bind(
        findViewById(R.id.usia),
        findViewById(R.id.beratBadan),
        findViewById(R.id.tinggiBadan),
        findViewById(R.id.serumKreatinin));
```

- [ ] **Step 2: Clear the cache in each reset handler**

```java
PatientInputCache.clear();
```

### Task 3: Verify build

**Files:**
- None

- [ ] **Step 1: Run Android build**

Run: `./gradlew assembleDebug`

- [ ] **Step 2: Confirm the app compiles with the new shared cache**

Expected: build succeeds without Java compile errors.
