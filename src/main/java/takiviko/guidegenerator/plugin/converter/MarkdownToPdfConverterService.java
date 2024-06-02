package takiviko.guidegenerator.plugin.converter;

import lombok.extern.slf4j.Slf4j;
import takiviko.guidegenerator.plugin.html.CustomHtmlRenderer;
import takiviko.guidegenerator.plugin.pdf.CustomPdfExporter;

import java.util.List;

/**
 * Service for converting markdown strings to PDF.
 */
@Slf4j
public class MarkdownToPdfConverterService {

    private static final String CSS_FILE_NAME = "guide-generator-style.css";
    private static final String RESOURCES_PATH = "/src/main/resources/";
    private static final String DEFAULT_STYLE_PATH = RESOURCES_PATH + "/" + CSS_FILE_NAME;

    private final CustomHtmlRenderer customHtmlRenderer = new CustomHtmlRenderer();
    private final CustomPdfExporter customPdfExporter = new CustomPdfExporter();

    /**
     * Creates a new markdown to PDF converter service.
     * @return a new markdown to PDF converter service instance
     */
    public static MarkdownToPdfConverterService newService() {
        return new MarkdownToPdfConverterService();
    }

    /**
     * Assembles the markdown strings into a PDF file.
     *
     * @param projectPath     the project directory path
     * @param buildDirPath    the build directory path
     * @param markdownStrings the markdown strings to be converted
     */
    public void assemble(String projectPath, String buildDirPath, List<String> markdownStrings) {
        String markdownString = String.join("\n", markdownStrings);
        String htmlString = customHtmlRenderer.render(markdownString, projectPath + DEFAULT_STYLE_PATH);
        customPdfExporter.export(htmlString, buildDirPath);
    }
}
