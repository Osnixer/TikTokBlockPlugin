import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

group = "dev.piotrulla.tiktokblock"
version = "1.0.0"
val mainPackage = "dev.piotrulla.tiktokblock"

repositories {
    gradlePluginPortal()
    mavenCentral()
    mavenLocal()

    maven { url = uri("https://papermc.io/repo/repository/maven-public/")}
    maven { url = uri("https://repo.codemc.io/repository/maven-public/") }
    maven { url = uri("https://repo.panda-lang.org/releases") }
    maven { url = uri("https://repo.eternalcode.pl/releases") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // Engine
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")

    // Configs
    implementation("net.dzikoysk:cdn:1.14.4") {
        exclude("kotlin")
    }

    // command framework
    implementation("dev.rollczi.litecommands:bukkit:2.8.8")

    // holograms
    compileOnly("com.github.decentsoftware-eu:decentholograms:2.8.3")
    compileOnly("me.filoghost.holographicdisplays:holographicdisplays-api:3.0.0")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

bukkit {
    main = "$mainPackage.TikTokBlockPlugin"
    apiVersion = "1.13"
    prefix = "TikTokBlock"
    author = "Piotrulla"
    name = "TikTokBlock"
    softDepend = listOf("HolographicDisplays", "DecentHolograms")
    version = "${project.version}"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(16))
}

tasks.withType<JavaCompile> {
    options.compilerArgs = listOf("-Xlint:deprecation", "-parameters")
    options.encoding = "UTF-8"
}

tasks.withType<ShadowJar> {
    archiveFileName.set("TikTokBlock v${project.version}.jar")

    exclude(
        "org/intellij/lang/annotations/**",
        "org/jetbrains/annotations/**",
        "org/checkerframework/**",
        "com/google/**",
        "META-INF/**",
        "javax/**"
    )

    mergeServiceFiles()
    minimize()

    val prefix = "$mainPackage.libs"

    listOf(
        "panda",
        "org.panda_lang",
        "org.bstats",
        "net.dzikoysk",
        "net.kyori",
        "com.j256",
        "dev.triumphteam"
    ).forEach { pack ->
        relocate(pack, "$prefix.$pack")
    }
}