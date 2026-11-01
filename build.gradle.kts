plugins {
    `java-library`
    id("com.gtnewhorizons.retrofuturagradle") version "1.4.9"
}
group = "com.zenovka"
version = "0.4.1"
base { archivesName.set("PassableFoliage") }
java { toolchain.languageVersion.set(JavaLanguageVersion.of(8)) }
minecraft {
    mcVersion.set("1.7.10")
    username.set("Developer")
}
repositories {
    mavenCentral()
    maven("https://nexus.gtnewhorizons.com/repository/public/")
}
tasks.withType<JavaCompile>().configureEach { options.encoding = "UTF-8" }
tasks.jar {
    manifest.attributes(mapOf(
        "FMLCorePlugin" to "com.zenovka.passablefoliage.FoliageCorePlugin",
        "FMLCorePluginContainsFMLMod" to "true"
    ))
}