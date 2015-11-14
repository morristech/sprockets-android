Sprockets for Android [![Maven Central][5]][6] [![Android Arsenal][3]][4]
=========================================================================

Extend base components, use widgets, and call utility methods.  Includes [Sprockets for Java][7].

* [Features](#features)
* [Install](#install)
* [Notes](#notes)
* [Javadoc][1]

Features
--------

Below is a sample of the available classes.  See the [Javadoc][1] for the complete reference.

app |     |
:-- | --- |
[VersionedApplication][100] | Implement onVersionChanged to be notified when the app runs with a new version for the first time.
[PanesActivity][103] | Manages two fragment panes that are either displayed next to each other or in a ViewPager, depending on screen size.
[SprocketsPreferenceFragment][104] | Sets preference values as their summary.

content |     |
:------ | --- |
[DbContentProvider][200] | ContentProvider with a SQLite database back end that implements all common database operations and notifies observers of changes.
[GooglePlacesLoader][201] | Loader that sends requests to the Google Places API and provides the responses.
[LocalCursorLoader][202] | Provides the current location to implementations before performing the cursor query.

database.sqlite |     |
:-------------- | --- |
[DbOpenHelper][300] | Executes raw resource SQL scripts to create and upgrade the database.

location |     |
:------- | --- |
[Locations][400] | Get the current location in one method call.

preference |     |
:--------- | --- |
[Prefs][500] | Get and set SharedPreferences values in one method call.

view |     |
:--- | --- |
[TranslateImagePageChangeListener][700] | Translates a "cropped" image when a ViewPager is scrolled to reveal the whole image. ([demo][701])

widget |     |
:----- | --- |
[GooglePlaceAutoComplete][600] | AutoCompleteTextView that provides local suggestions from the Google Places API. ([demo][604])
[FadingActionBarScrollListener][601] | Fades the ActionBar title and background from transparent to opaque while scrolling down the list. ([demo][605])
[FloatingHeaderScrollListener][602] | Slides a View that floats above your list header(s) up and down along with the scrolling of the list. ([demo][606])
[ParallaxViewScrollListener][603] | Synchronises the scrolling of a View with a ListView, at a speed relative to the list scrolling speed. ([demo][607])

Install
-------

1\. Add dependency.  Requires *Android Support Repository* and *Google Repository* in SDK Manager.

```groovy
    compile 'net.sf.sprockets:sprockets-android:3.0.0'
```

2\. *(Optional)* Add `minifyEnabled true` to all `buildTypes`.  Requires Android plug-in for Gradle 1.3.0+.

3\. *(Optional)* Download [sprockets.xml][11] to `{app}/src/main/resources/` and add your [Google API key][12].

Notes
-----

### minSdkVersion < 16

Many features will work with lower API levels, though you should carefully test them to ensure that no APIs above your minimum level are accessed.  Tell the build system to allow higher API levels with the `tools:overrideLibrary` attribute in `AndroidManifest.xml`.

```xml
<manifest
    package="your.package.name"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <uses-sdk tools:overrideLibrary="android.support.v13, net.sf.sprockets"/>
```

[1]: https://pushbit.github.io/sprockets-android/apidocs/
[3]: https://img.shields.io/badge/Android%20Arsenal-Sprockets-brightgreen.svg?style=flat
[4]: https://android-arsenal.com/details/1/1243
[5]: https://img.shields.io/maven-central/v/net.sf.sprockets/sprockets-android.svg
[6]: https://search.maven.org/#search|ga|1|g%3Anet.sf.sprockets%20a%3Asprockets-android
[7]: https://github.com/pushbit/sprockets

[11]: https://raw.githubusercontent.com/pushbit/sprockets/master/src/main/resources/net/sf/sprockets/sprockets.xml
[12]: https://console.developers.google.com/

[100]: https://pushbit.github.io/sprockets-android/apidocs/index.html?net/sf/sprockets/app/VersionedApplication.html
[103]: https://pushbit.github.io/sprockets-android/apidocs/index.html?net/sf/sprockets/app/ui/PanesActivity.html
[104]: https://pushbit.github.io/sprockets-android/apidocs/index.html?net/sf/sprockets/app/ui/SprocketsPreferenceFragment.html

[200]: https://pushbit.github.io/sprockets-android/apidocs/index.html?net/sf/sprockets/content/DbContentProvider.html
[201]: https://pushbit.github.io/sprockets-android/apidocs/index.html?net/sf/sprockets/content/GooglePlacesLoader.html
[202]: https://pushbit.github.io/sprockets-android/apidocs/index.html?net/sf/sprockets/content/LocalCursorLoader.html

[300]: https://pushbit.github.io/sprockets-android/apidocs/index.html?net/sf/sprockets/database/sqlite/DbOpenHelper.html

[400]: https://pushbit.github.io/sprockets-android/apidocs/index.html?net/sf/sprockets/gms/location/Locations.html

[500]: https://pushbit.github.io/sprockets-android/apidocs/index.html?net/sf/sprockets/preference/Prefs.html

[600]: https://pushbit.github.io/sprockets-android/apidocs/index.html?net/sf/sprockets/widget/GooglePlaceAutoComplete.html
[601]: https://pushbit.github.io/sprockets-android/apidocs/index.html?net/sf/sprockets/widget/FadingActionBarScrollListener.html
[602]: https://pushbit.github.io/sprockets-android/apidocs/index.html?net/sf/sprockets/widget/FloatingHeaderScrollListener.html
[603]: https://pushbit.github.io/sprockets-android/apidocs/index.html?net/sf/sprockets/widget/ParallaxViewScrollListener.html
[604]: samples/images/GooglePlaceAutoComplete.gif
[605]: samples/images/FadingActionBarScrollListener.gif
[606]: samples/images/FloatingHeaderScrollListener.gif
[607]: samples/images/ParallaxViewScrollListener.gif

[700]: https://pushbit.github.io/sprockets-android/apidocs/index.html?net/sf/sprockets/view/TranslateImagePageChangeListener.html
[701]: samples/images/TranslateImagePageChangeListener.gif
