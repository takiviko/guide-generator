package takiviko.guidegenerator.plugin.pdf;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.vladsch.flexmark.pdf.converter.PdfConverterExtension;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class CustomPdfExporter {

    /**
     * Exports the given HTML to a PDF file at the specified build path.
     *
     * @param html      the HTML content to export
     * @param buildPath the path where the PDF file should be created
     */
    public void export(String html, String buildPath) {
        try {
            log.info("Exporting PDF to path: " + buildPath);
            FileOutputStream fileOutputStream = createFile(buildPath);
            PdfConverterExtension.exportToPdf(fileOutputStream, html, "", BaseRendererBuilder.TextDirection.LTR);
            log.info("Successfully exported PDF to path: " + buildPath);
        } catch (IOException e) {
            log.error("Failed to export PDF", e);
        }
    }

    /**
     * Creates a new file at the specified build path and returns a FileOutputStream to write to it.
     *
     * @param buildPath the path where the file should be created
     * @return a FileOutputStream to write to the newly created file
     * @throws IOException if an I/O error occurs when creating the file
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private FileOutputStream createFile(String buildPath) throws IOException {
        File file = new File(buildPath + "/guide/generated-guide.pdf");
        file.getParentFile().mkdirs();
        file.createNewFile();
        return new FileOutputStream(file);
    }
}
