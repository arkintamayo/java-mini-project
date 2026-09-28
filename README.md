# 🎨 Java Image Editor & Weather Forecast

A Java project built for **CSCI-C212: Introduction to Software Systems** at Indiana University. It has two parts: a GUI image editor with pixel-level transformations, and a command-line weather forecast tool that pulls live data from a public REST API.

This was a group project. The course provided the GUI framework, and my work covers the image transformations and the weather program (details below).

**Skills shown:** Java, object-oriented design, 2D pixel manipulation, HTTP GET requests, JSON parsing (Gson), command-line argument handling, IntelliJ IDEA.

---

## 🗂️ My Contributions

| Part                                               | What I built                                                                                                                                       |
| -------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Image transformations** (`ImageOperations.java`) | Six static methods that each take a `BufferedImage` and return a transformed image: `zeroRed`, `grayscale`, `invert`, `mirror`, `repeat`, `rotate` |
| **Weather forecast** (`WeatherForecast.java`)      | A program that calls the Open-Meteo API, parses the JSON response, and prints a 7-day forecast                                                     |

The starter GUI code (menus, zooming, undo/redo) was provided by the course. PPM file reading and writing was completed by other group members.

---

## 🖼️ Image Editor

The editor opens `.ppm` images and lets you apply transformations from the **Tools** menu. Each one is undoable.

| Tool          | What it does                                                                           |
| ------------- | -------------------------------------------------------------------------------------- |
| **Zero Red**  | Removes the red channel from every pixel                                               |
| **Grayscale** | Converts to grayscale using a weighted luminance formula (0.299 R + 0.587 G + 0.114 B) |
| **Invert**    | Produces a negative by replacing each channel with `255 - value`                       |
| **Mirror**    | Reflects the image horizontally or vertically                                          |
| **Repeat**    | Tiles the image `n` times, side by side or top to bottom                               |
| **Rotate**    | Rotates the image 90° clockwise or counterclockwise                                    |

### Screenshots

| Original | Grayscale | Invert |
| --- | --- | --- |
| ![Original image](/images/image-editor-original.png) | ![Grayscale](/images/image-editor-grayscale.png) | ![Invert](/images/image-editor-invert.png) |
 
| Mirror (Horizontal) | Mirror (Vertical) | Repeat (Horizontal) | Repeat (Vertical) |
| --- | --- | --- | --- |
| ![Mirror horizontal](/images/image-editor-mirror-horizontal.png) | ![Mirror vertical](/images/image-editor-mirror-vertical.png) | ![Repeat horizontal](/images/image-editor-repeat-horizontal.png) | ![Repeat vertical](/images/image-editor-repeat-vertical.png)
 
| Rotate (Clockwise) | Rotate (Counter-clockwise) |
| --- | --- |
| ![Rotate clockwise](/images/image-editor-clockwise.png) | ![Rotate counterclockwise](/images/image-editor-counter-clockwise.png) |

### How to run
 
1. Open the `mp-a-template` folder in IntelliJ IDEA (JDK 21).
2. Make sure the libraries in `lib/` are on the classpath (the project is already configured for this).
3. Run `ImageEditorRunner.java`.
4. Choose **File > Open** and select `mp-a-template/pm.ppm` (a sample image included in the project).
5. Try the options under **Tools**. Use **Edit > Undo/Redo** to step through changes.

---

## 🌦️ Weather Forecast
 
A command-line program that prints a 7-day temperature forecast in 3-hour intervals, using the free [Open-Meteo API](https://open-meteo.com/). **It needs an internet connection to run.**
 
**How it works:**
 
1. Builds a request URL from latitude, longitude, temperature unit, and time zone.
2. Sends an HTTP GET request with `HttpURLConnection` and checks for a `200` response (otherwise it throws an `IOException`).
3. Reads the response into a `StringBuilder`.
4. Parses the JSON with [Gson](https://github.com/google/gson) to get the time and temperature arrays.
5. Prints the forecast grouped by date.

### Usage
 
By default it prints the forecast for Bloomington, IN in Fahrenheit. You can override that with flags:
 
| Flag | Description | Example |
| --- | --- | --- |
| `--latitude` | Latitude of the location | `36.0689` |
| `--longitude` | Longitude of the location | `-79.8102` |
| `--unit` | `F` (Fahrenheit) or `C` (Celsius) | `C` |
 
**In IntelliJ:** open *Run > Edit Configurations* for `WeatherForecast` and put the flags in *Program arguments*, for example:
 
```
--latitude 36.0689 --longitude -79.8102 --unit C
```
 
**From the command line** (from inside `mp-a-template`):
 
```bash
# compile (use ; instead of : on Windows)
javac -cp "lib/*" -d out src/*.java
 
# run
java -cp "out:lib/*" WeatherForecast --latitude 36.0689 --longitude -79.8102 --unit C
```
 
### Sample output
 
```
7-Day Forecast in Fahrenheit:
Forecast for 2026-09-28:
00:00: 52.0°F
03:00: 49.0°F
06:00: 47.6°F
09:00: 61.9°F
12:00: 74.5°F
15:00: 76.2°F
18:00: 72.7°F
21:00: 63.6°F
Forecast for 2026-09-29:
00:00: 58.8°F
...
Forecast for 2026-10-04:
00:00: 55.1°F
03:00: 53.1°F
06:00: 50.7°F
09:00: 60.4°F
12:00: 70.8°F
15:00: 73.9°F
18:00: 64.2°F
21:00: 56.1°F
```
 
*(Run with no flags, so this defaults to Bloomington, IN in Fahrenheit. Dates shown are relative to when the program is run.)*
 
---
 
## 📁 Project Structure
 
```
mp-a-template/
├── src/
│   ├── ImageOperations.java     # image transformations (my work)
│   ├── WeatherForecast.java     # weather API program (my work)
│   ├── ImageEditor.java         # editor logic and PPM I/O
│   ├── ImageEditorRunner.java   # launches the GUI
│   └── *MenuItem.java, ...      # menu and UI classes (course-provided)
├── lib/                         # Gson library
└── pm.ppm                       # sample image
```
 
## 🧠 What I Learned
 
This was my first time working with an API. I learned how to make an HTTP request, check the response, and extract data from JSON. On the image side, mirroring took the most work, because it means thinking carefully about pixel coordinates.
 
## ⚙️ Tech
 
Java 21 · Swing/AWT · Gson 2.13.1 · IntelliJ IDEA

*This README was written with the help of Claude (Anthropic).*