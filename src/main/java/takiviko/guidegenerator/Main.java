package takiviko.guidegenerator;

import takiviko.guidegenerator.plugin.GuideGeneratorService;
import takiviko.guidegenerator.plugin.converter.MarkdownToPdfConverterService;

import java.util.List;

public class Main {

    /**
     * Main method for the application.
     * Used for debugging and testing purposes.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        List<String> markdownStrings =
            new GuideGeneratorService().getMarkdownStrings("takiviko", ClassLoader.getSystemClassLoader());

        String projectPath = System.getProperty("user.dir").replace("\\", "/");
        String buildPath = projectPath + "/build";

        MarkdownToPdfConverterService.newService().assemble(projectPath, buildPath, markdownStrings);
    }
}