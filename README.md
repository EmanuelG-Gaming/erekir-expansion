# Erekir Expansion
__(Development closed, as of 8th of June 2023)__

A Java mod meant to add new things to the current Erekir's progression.

~~Notice that I will slow down the development of the mod. I was working on a JS Mindustry mod, and newly, a C++ SDL2 game.~~

Fun Fact: I used my private JS mod version, Flar Expansion v1.0 to remove some of the Erekir content and place it in this standalone mod, so it'll not harm the previous mod's reputation.

# Content
The mod adds a medium amount of content, mostly grouped in three factions that may have been an extent to Erekir's turrets. This mod also has two maps that introduce you to the first two factions, though the mod is mostly just units.

## Factions 
The factions that got mentioned in the last paragraph were:

### Beryllium
- Based on the `Breach`.
- Units that tend to slowly shoot more powerful bullets.

### Tungsten
- Based on the `Disperse`.
- Units that tend to spread weaker bullets.

### Ozone
- Based on the `Sublimate`.
- `<No further details yet>.`

# Links

> Barrier's (an old mod of mine, not working on v7) discord server:

https://discord.gg/34QU3a9J

## Building for Desktop Testing

1. Install JDK **17**.
2. Run `gradlew jar` [1].
3. Your mod jar will be in the `build/libs` directory. **Only use this version for testing on desktop. It will not work with Android.**
To build an Android-compatible version, you need the Android SDK. You can either let Github Actions handle this, or set it up yourself. See steps below.

## Building through Github Actions

This repository is set up with Github Actions CI to automatically build the mod for you every commit. This requires a Github repository, for obvious reasons.
To get a jar file that works for every platform, do the following:
1. Make a Github repository with your mod name, and upload the contents of this repo to it. Perform any modifications necessary, then commit and push. 
2. Check the "Actions" tab on your repository page. Select the most recent commit in the list. If it completed successfully, there should be a download link under the "Artifacts" section. 
3. Click the download link (should be the name of your repo). This will download a **zipped jar** - **not** the jar file itself [2]! Unzip this file and import the jar contained within in Mindustry. This version should work both on Android and Desktop.

## Building Locally

Building locally takes more time to set up, but shouldn't be a problem if you've done Android development before.
1. Download the Android SDK, unzip it and set the `ANDROID_HOME` environment variable to its location.
2. Make sure you have API level 30 installed, as well as any recent version of build tools (e.g. 30.0.1)
3. Add a build-tools folder to your PATH. For example, if you have `30.0.1` installed, that would be `$ANDROID_HOME/build-tools/30.0.1`.
4. Run `gradlew deploy`. If you did everything correctlly, this will create a jar file in the `build/libs` directory that can be run on both Android and desktop. 

## Adding Dependencies

Please note that all dependencies on Mindustry, Arc or its submodules **must be declared as compileOnly in Gradle**. Never use `implementation` for core Mindustry or Arc dependencies. 

- `implementation` **places the entire dependency in the jar**, which is, in most mod dependencies, very undesirable. You do not want the entirety of the Mindustry API included with your mod.
- `compileOnly` means that the dependency is only around at compile time, and not included in the jar.

Only use `implementation` if you want to package another Java library *with your mod*, and that library is not present in Mindustry already.

--- 

*[1]* *On Linux/Mac it's `./gradlew`, but if you're using Linux I assume you know how to run executables properly anyway.*  
*[2]: Yes, I know this is stupid. It's a Github UI limitation - while the jar itself is uploaded unzipped, there is currently no way to download it as a single file.*
