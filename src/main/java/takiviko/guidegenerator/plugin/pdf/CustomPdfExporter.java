package takiviko.guidegenerator.plugin.pdf;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.vladsch.flexmark.pdf.converter.PdfConverterExtension;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

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
            FileOutputStream fileOutputStream = createFile(buildPath);
            log.info("current path: " + Paths.get("").toAbsolutePath());
            PdfConverterExtension.exportToPdf(fileOutputStream, html, "", BaseRendererBuilder.TextDirection.LTR);
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
