package takiviko.guidegenerator.plugin.html;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;

public class CustomHtmlRenderer {

    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();

    /**
     * Renders the markdown string to HTML.
     *
     * @param markdown the markdown string to be rendered
     * @return the rendered HTML string
     */
    public String render(String markdown) {
        Node document = parser.parse(markdown);
        return htmlRenderer.render(document);
    }
}
