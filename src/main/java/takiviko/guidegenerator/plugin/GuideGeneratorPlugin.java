package takiviko.guidegenerator.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.internal.classloader.VisitableURLClassLoader;
import takiviko.guidegenerator.service.GuideGeneratorService;
import takiviko.guidegenerator.service.converter.MarkdownToPdfConverterService;

/**
 * Implementation class for the Guide Generator plugin.
 */
@Slf4j
public class GuideGeneratorPlugin implements Plugin<Project> {

    private final GuideGeneratorService guideGeneratorService = GuideGeneratorService.newService();
    private final MarkdownToPdfConverterService markdownToPdfConverterService = MarkdownToPdfConverterService.newService();

    /**
     * Entry point of the Guide Generator plugin.
     *
     * @param project the target project
     */
    @Override
    public void apply(Project project) {

        GuideGeneratorPluginExtension guideGeneratorPluginExtension = project.getExtensions()
            .create("guideGenerator", GuideGeneratorPluginExtension.class);

        project.task("generateGuide")
            .dependsOn(project.getTasks().getByName("compileJava"))
            .doLast(task -> generateGuide(project, guideGeneratorPluginExtension.getBasePackage()));
    }

    private void generateGuide(Project project, String basePackage) {
        URL[] urls = getProjectFileUrls(project);

        ClassLoader classLoader = new URLClassLoader(
            "myUrlClassLoader",
            urls,
            VisitableURLClassLoader.getSystemClassLoader()
        );

         var markdownStrings = guideGeneratorService.getMarkdownStrings(basePackage, classLoader);
         markdownToPdfConverterService.assemble(project.getBuildDir().getPath(), markdownStrings);
    }

    private URL[] getProjectFileUrls(Project project) {
        SourceSetContainer sourceSetContainer =
            project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets();

        Set<File> sourceFiles = sourceSetContainer
            .getByName("main")
            .getOutput()
            .getClassesDirs()
            .getFiles();

        Set<File> runtimeFiles = project.getConfigurations()
            .getByName("runtimeClasspath")
            .getFiles();

        runtimeFiles.addAll(sourceFiles);
        runtimeFiles.add(project.getProjectDir());

        return getFileURLs(runtimeFiles);
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
