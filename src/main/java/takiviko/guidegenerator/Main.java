package takiviko.guidegenerator;

import takiviko.guidegenerator.service.GuideGeneratorService;
import takiviko.guidegenerator.service.converter.MarkdownToPdfConverterService;

public class Main {
    public static void main(String[] args) {
        var markdownStrings = GuideGeneratorService.newService().getMarkdownStrings(
            "takiviko",
            ClassLoader.getSystemClassLoader()
        );
        MarkdownToPdfConverterService.newService().assemble("/home/viktor/IdeaProjects/Thesis/guide-generator/build", markdownStrings);
    }
}