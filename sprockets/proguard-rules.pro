-dontobfuscate

# butterknife
-dontwarn butterknife.internal.**
-keep class butterknife.** { *; }
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }
-keepclasseswithmembernames class * { @butterknife.* <methods>; }

# commons-configuration
-dontwarn java.applet.**
-dontwarn java.awt.**
-dontwarn java.beans.**
-dontwarn javax.naming.**
-dontwarn javax.servlet.**
-dontwarn org.apache.commons.beanutils.**
-dontwarn org.apache.commons.codec.**
-dontwarn org.apache.commons.configuration.resolver.**
-dontwarn org.apache.commons.digester.**
-dontwarn org.apache.commons.jexl2.**
-dontwarn org.apache.commons.jxpath.**
-dontwarn org.apache.commons.logging.**
-dontwarn org.apache.commons.vfs2.**

# commons-logging
-dontwarn org.apache.avalon.**
-dontwarn org.apache.log.**
-dontwarn org.apache.log4j.**

# google-play-services (issues in 8.1.0, hopefully fixed in a later version)
-dontwarn android.net.http.AndroidHttpClient
-dontwarn com.google.android.gms.**
-keep class com.google.android.gms.** { *; }

# guava
-dontwarn javax.annotation.**
-dontwarn sun.misc.**

# icepick
-dontwarn icepick.processor.**
-keep class **$$Icicle { *; }
-keepnames class * { @icepick.Icicle *; }

# immutables
-dontwarn com.sun.tools.javac.code.**
-dontwarn javax.lang.model.**
-dontwarn javax.tools.**
-dontwarn org.eclipse.jdt.**
-dontwarn org.immutables.value.internal.**

# okhttp
-dontwarn com.squareup.okhttp.internal.**

# okio
-dontwarn java.nio.file.**
-dontwarn org.codehaus.mojo.animal_sniffer.**

# retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions
