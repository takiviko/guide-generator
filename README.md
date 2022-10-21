# Guide Generator plugin

Currently, the plugin is still in development, and it is yet not published to the gradle plugins repository.

## Usage

1. Build the project on your machine
2. Import the shadowed jar to your own project
3. Annotate your package-info.java files with the ```@Documentation``` annotation and write some documentation in Markdown format
4. Run the ```generateGuide``` gradle task
5. You can find the generated pdf file in the */build/guide* directory

*build.gradle*

```
buildscript {
    dependencies {
        //...
        PATH_TO_PLUGIN_JAR
    }
}

apply plugin: GuideGeneratorPlugin

dependencies {
    //...
    implementation files('PATH_TO_PLUGIN_JAR')
}

guideGenerator {
    setBasePackage(BASE_PACKAGE_OF_YOUR_PROJECT)
}
```