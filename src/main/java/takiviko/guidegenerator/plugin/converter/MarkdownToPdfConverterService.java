package takiviko.guidegenerator.plugin.converter;

import lombok.extern.slf4j.Slf4j;
import takiviko.guidegenerator.plugin.extension.style.HtmlStyle;
import takiviko.guidegenerator.plugin.html.CustomHtmlRenderer;
import takiviko.guidegenerator.plugin.pdf.CustomPdfExporter;

import java.util.List;

/**
 * Service for converting markdown strings to PDF.
 */
@Slf4j
public class MarkdownToPdfConverterService {

    private final CustomHtmlRenderer htmlRenderer = new CustomHtmlRenderer();
    private final CustomPdfExporter pdfExporter = new CustomPdfExporter();

    public static MarkdownToPdfConverterService newService() {
        return new MarkdownToPdfConverterService();
    }

    /**
     * Assembles the markdown strings into a PDF file.
     *
     * @param buildPath       the path to the build directory
     * @param markdownStrings the markdown strings to be converted
     * @param htmlStyle       the HTML style to be applied to the document
     */
    public void assemble(
        String buildPath,
        List<String> markdownStrings,
        HtmlStyle htmlStyle
    ) {
        String markdownString = String.join("\n", markdownStrings);

        log.info("Started converting markdown string");

        String htmlString = htmlRenderer.render(markdownString, htmlStyle);
        pdfExporter.export(htmlString, buildPath);

        log.info("Finished converting markdown string");
    }
}
