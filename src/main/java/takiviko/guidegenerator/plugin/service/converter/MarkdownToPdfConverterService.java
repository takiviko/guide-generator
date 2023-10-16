package takiviko.guidegenerator.plugin.service.converter;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.pdf.converter.PdfConverterExtension;
import com.vladsch.flexmark.util.ast.Node;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
public class MarkdownToPdfConverterService {
    public static MarkdownToPdfConverterService newService() {
        return new MarkdownToPdfConverterService();
    }

    public void assemble(
        String buildPath,
        List<String> markdownStrings
    ) {
        String markdownString = String.join("\n", markdownStrings);

        log.info("Started converting markdown string");

        Parser parser = Parser.builder().build();
        HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
        Node document = parser.parse(markdownString);
        String htmlString = htmlRenderer.render(document);

        try {
            File exportFile = new File(buildPath + "/guide/generated-guide.pdf");
            exportFile.getParentFile().mkdirs();
            exportFile.createNewFile();
            PdfConverterExtension.exportToPdf(
                new FileOutputStream(exportFile),
                htmlString,
                "",
                BaseRendererBuilder.TextDirection.LTR
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
