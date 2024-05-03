package takiviko.guidegenerator.plugin.pdf;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.vladsch.flexmark.pdf.converter.PdfConverterExtension;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@AllArgsConstructor
public class CustomPdfExporter {

    public void export(String html, String buildPath) {
        try {
            FileOutputStream fileOutputStream = createFile(buildPath);
            PdfConverterExtension.exportToPdf(fileOutputStream, html, "", BaseRendererBuilder.TextDirection.LTR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private FileOutputStream createFile(String buildPath) throws IOException {
        File file = new File(buildPath + "/guide/generated-guide.pdf");
        file.getParentFile().mkdirs();
        file.createNewFile();
        return new FileOutputStream(file);
    }
}
