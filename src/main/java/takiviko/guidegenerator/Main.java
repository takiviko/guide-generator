package takiviko.guidegenerator;

import takiviko.guidegenerator.service.GuideGeneratorService;
import takiviko.guidegenerator.service.converter.MarkdownToPdfConverterService;

public class Main {

    /**
     * Main method for the application.
     * Used for debugging and testing purposes.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        var markdownStrings = GuideGeneratorService.newService().getMarkdownStrings(
            "takiviko",
            ClassLoader.getSystemClassLoader()
        );
        MarkdownToPdfConverterService.newService().assemble(System.getProperty("user.dir") + "/build", markdownStrings);
    }
}