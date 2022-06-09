import java.io.File
import java.io.FileInputStream
import java.util.Properties

plugins {
    java
    checkstyle
    distribution
    id("org.omegat.gradle") version "1.5.7"
    id("com.github.spotbugs") version "5.0.7"
    id("com.diffplug.spotless") version "6.7.0"
    id("com.palantir.git-version") version "0.13.0" apply false
}

fun getProps(f: File): Properties {
    val props = Properties()
    props.load(FileInputStream(f))
    return props
}

// we handle cases without .git directory
val props = project.file("src/main/resources/version.properties")
val dotgit = project.file(".git")
if (dotgit.exists()) {
    apply(plugin = "com.palantir.git-version")
    val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
    val details = versionDetails()
    val baseVersion = details.lastTag.substring(1)
    if (details.isCleanTag) {  // release version
        version = baseVersion
    } else {  // snapshot version
        version = baseVersion + "-" + details.commitDistance + "-" + details.gitHash + "-SNAPSHOT"
    }
} else if (props.exists()) { // when version.properties already exist, just use it.
    version = getProps(props).getProperty("version")
}

tasks.register("writeVersionFile") {
    val folder = project.file("src/main/resources")
    if (!folder.exists()) {
        folder.mkdirs()
    }
    props.delete()
    props.appendText("version=" + project.version)
}

tasks.getByName("jar") {
    dependsOn("writeVersionFile")
}

omegat {
    version = "5.6.0"
    pluginClass = "tokyo.northside.omegat.PDic"
}

repositories {
    mavenCentral()
}
dependencies {
    packIntoJar("io.github.eb4j:pdic4j:0.3.3")
}

checkstyle {
    isIgnoreFailures = true
    toolVersion = "7.1"
}

distributions {
    main {
        contents {
            from(tasks["jar"], "README.md", "COPYING", "CHANGELOG.md")
        }
    }
}

val jar by tasks.getting(Jar::class) {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
