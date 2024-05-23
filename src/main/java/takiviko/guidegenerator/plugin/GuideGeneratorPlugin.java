package takiviko.guidegenerator.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.internal.classloader.VisitableURLClassLoader;
import takiviko.guidegenerator.plugin.converter.MarkdownToPdfConverterService;
import takiviko.guidegenerator.plugin.extension.GuideGeneratorPluginExtension;

/**
 * Implementation class for the Guide Generator plugin.
 */
@Slf4j
@SuppressWarnings("unused")
public class GuideGeneratorPlugin implements Plugin<Project> {

    private final GuideGeneratorService guideGeneratorService = new GuideGeneratorService();
    private final MarkdownToPdfConverterService markdownToPdfConverterService = MarkdownToPdfConverterService.newService();

    /**
     * Entry point of the Guide Generator plugin.
     *
     * @param project the target project
     */
    @Override
    public void apply(Project project) {

        takiviko.guidegenerator.plugin.extension.GuideGeneratorPluginExtension guideGeneratorPluginExtension = project.getExtensions()
            .create("guideGenerator", takiviko.guidegenerator.plugin.extension.GuideGeneratorPluginExtension.class);

        project.task("generateGuide")
            .dependsOn(project.getTasks().getByName("compileJava"))
            .doLast(task -> generateGuide(project, guideGeneratorPluginExtension));
    }

    private void generateGuide(Project project, GuideGeneratorPluginExtension extension) {
        URL[] urls = getProjectFileUrls(project);

        ClassLoader classLoader = new URLClassLoader(
            "guideGeneratorClassLoader",
            urls,
            VisitableURLClassLoader.getSystemClassLoader()
        );

        List<String> markdownStrings = guideGeneratorService.getMarkdownStrings(extension.getBasePackage(), classLoader);
         markdownToPdfConverterService.assemble(project.getBuildDir().getPath(), markdownStrings, extension.getHtmlStyle());
    }

    private URL[] getProjectFileUrls(Project project) {
        @SuppressWarnings("deprecation")
        SourceSetContainer sourceSetContainer =
            project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets();

        // Target project's files
        Set<File> sourceFiles = sourceSetContainer
            .getByName("main")
            .getOutput()
            .getClassesDirs()
            .getFiles();

        // This project's files
        // Needed for using @Documentation type
        Set<File> runtimeFiles = project.getConfigurations()
            .getByName("runtimeClasspath")
            .filter(file -> file.getPath().contains("io.github.takiviko.guide-generator"))
            .getFiles();

        sourceFiles.addAll(runtimeFiles);
        sourceFiles.add(project.getProjectDir());

        return getFileURLs(sourceFiles);
    }

    private URL[] getFileURLs(Set<File> files) {
        return files.stream()
            .map(file -> getURLFromURI(file.toURI()))
            .toArray(URL[]::new);
    }

    private URL getURLFromURI(URI uri) {
        try {
            return uri.toURL();
        } catch (MalformedURLException e) {
            log.error("Something went wrong with uri {}", uri);
            throw new RuntimeException(e);
        }
    }
}
