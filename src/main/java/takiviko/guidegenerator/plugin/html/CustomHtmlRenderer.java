package takiviko.guidegenerator.plugin.html;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * Custom renderer for rendering HTML from markdown, with a custom style.
 */
@Slf4j
public class CustomHtmlRenderer {

    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();

    /**
     * Renders the markdown string to HTML.
     *
     * @param markdown  the markdown string to be rendered
     * @param stylePath the path of the CSS style to be applied to the HTML
     * @return the rendered HTML string
     */
    public String render(String markdown, String stylePath) {
        log.debug("Rendering markdown to HTML. Bytes: " + markdown.getBytes(StandardCharsets.UTF_8).length);

        Node document = parser.parse(markdown);
        String rawHtml = htmlRenderer.render(document);

        return styledHtml(rawHtml, stylePath);
    }

    private String styledHtml(String rawHtml, String stylePath) {
        boolean customStyle = new File(stylePath).exists();
        return "<html>\n" + getHtmlHead(customStyle, stylePath) + getHtmlBody(rawHtml) + "</html>";
    }

    private String getHtmlHead(boolean customStyle, String stylePath) {
        if (customStyle) {
            log.info("Using custom CSS for rendering");
            return "<head>\n" +
                "   <link rel=\"stylesheet\" type=\"text/css\" href=\"file:///" + stylePath + "\">\n" +
                "</head>\n";
        }

        log.info("""
            Custom CSS not found, using default style.
            To customize the css style, please add a guide-generator-style.css file to your project's resources folder.
            """);
        return "";
    }

    private String getHtmlBody(String rawHtml) {
        return "<body>\n" + rawHtml + "</body>\n";
    }
}
