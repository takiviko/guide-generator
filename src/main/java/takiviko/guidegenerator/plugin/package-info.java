/**
 * Plugin classes for the Guide Generator project.
 */
@Documentation(
    documentation = """
        # Guide Generator plugin
                
        Plugin for keeping documentation as close to the codebase as possible without bloating your codebase.
                
        This tool allows you to generate a PDF version of your documentation by running a gradle task,\s
        so it is ready to be included in any sort of automated task.
                
        ## Pre-requisites
                
        1. Java 11+
        2. A Gradle project
        3. Willingness to update the project's documentation before each release
                
        ## Usage
                
        1. Add the following lines to your *build.gradle* file (it is recommended to use the latest stable version):
        ```
        buildscript {
            dependencies {
                // ...
                classpath("io.github.takiviko.guide-generator:guide-generator:1.0.0")
            }
        }
                
        plugins {
            // ...
            id "io.github.takiviko.guide-generator" version 1.0.0
        }
                
        dependencies {
            // ...
            implementation 'io.github.takiviko.guide-generator:guide-generator:1.0.0'
        }
                
        guideGenerator {
            // Base package of your project (example value provided)
            setBasePackage('takiviko.guidegenerator')
        }
        ```
        2. Add *package-info.java* files to your project's most important packages
        3. Annotate your package-info files with the *@Documentation* annotation
        4. Write your docs as a multi-line string into your annotation's *documentation* field, format it as you wish
        5. Generate your documentation PDF by running the following command:
        ```
         ./gradlew generateGuide
        ```
        6. Look for your generated PDF at *build/guide/generated-guide.pdf*
                
        ## Links
                
        ### Maven central
        https://central.sonatype.com/artifact/io.github.takiviko.guide-generator/guide-generator

        ### Gradle plugins
        https://plugins.gradle.org/plugin/io.github.takiviko.guide-generator

        ### Github
        https://github.com/takiviko/guide-generator
        """
)
package takiviko.guidegenerator.plugin;

import takiviko.guidegenerator.annotation.Documentation;