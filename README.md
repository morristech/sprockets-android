Sprockets for Android [![Javadoc][javadoc-badge]][javadoc] [![Maven Central][maven-shield]][maven]
==================================================================================================

Extend base components, use widgets, and call utility methods.

Install
-------

```groovy
    compile 'net.sf.sprockets:sprockets-android:4.0.0'
```

Ensure the Data Binding library is enabled.
```groovy
    dataBinding {
        enabled true
    }
```

Set `minifyEnabled true` for all `buildTypes` (including `debug`).

If you are using Espresso and get a version conflict error for jsr305, add an exclude line.
```groovy
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.google.code.findbugs', module: 'jsr305'
    })
```

Notes
-----

### minSdkVersion < 21

Many features will work with lower API levels, though you should carefully test them to ensure that no APIs above your minimum level are accessed. Tell the build system to allow higher API levels with the `tools:overrideLibrary` attribute in `AndroidManifest.xml`.

```xml
<manifest ...>
    <uses-sdk tools:overrideLibrary="net.sf.sprockets"/>
```

[javadoc]: https://javadoc.io/doc/net.sf.sprockets/sprockets-android/
[javadoc-badge]: https://javadoc.io/badge/net.sf.sprockets/sprockets-android.svg
[maven]: https://search.maven.org/#search|ga|1|g%3Anet.sf.sprockets%20a%3Asprockets-android
[maven-shield]: https://img.shields.io/maven-central/v/net.sf.sprockets/sprockets-android.svg
