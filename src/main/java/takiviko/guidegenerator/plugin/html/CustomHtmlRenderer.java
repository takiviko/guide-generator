package takiviko.guidegenerator.plugin.html;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import lombok.extern.slf4j.Slf4j;

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
        Node document = parser.parse(markdown);
        String rawHtml = htmlRenderer.render(document);

        return "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "   <link rel=\"stylesheet\" type=\"text/css\" href=\"file:///" + stylePath + "\">\n" +
            "</head>\n" +
            "<body>\n" +
            rawHtml +
            "</body>\n" +
            "</html>";
    }
}
