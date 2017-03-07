# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:/Work/android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-printmapping build/outputs/mapping/release/mapping.txt
# Remove logs
-assumenosideeffects class android.util.Log {
   public static boolean isLoggable(java.lang.String, int);
   public static int v(...);
   public static int i(...);
   public static int w(...);
   public static int d(...);
   public static int e(...);
}
-dontwarn lombok.**
-dontwarn android.test.**
-dontwarn org.junit.rules.DisableOnDebug**
-dontwarn org.junit.internal.runners.statements**
-dontwarn java.beans.ConstructorProperties**