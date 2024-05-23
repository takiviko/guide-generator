package takiviko.guidegenerator;

import takiviko.guidegenerator.plugin.GuideGeneratorService;
import takiviko.guidegenerator.plugin.converter.MarkdownToPdfConverterService;
import takiviko.guidegenerator.plugin.extension.style.HtmlStyle;

public class Main {

    private static final HtmlStyle GLOBAL_HTML_STYLE = HtmlStyle.builder()
        .color("black")
        .font("helvetica")
        .backgroundColor("white")
        .fontSize(14)
        .textAlign("justify")
        .build();

    /**
     * Main method for the application.
     * Used for debugging and testing purposes.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        var markdownStrings = new GuideGeneratorService().getMarkdownStrings(
            "takiviko",
            ClassLoader.getSystemClassLoader()
        );
        MarkdownToPdfConverterService.newService().assemble(System.getProperty("user.dir") + "/build", markdownStrings, GLOBAL_HTML_STYLE);
    }
}