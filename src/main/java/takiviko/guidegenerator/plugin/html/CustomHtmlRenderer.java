package takiviko.guidegenerator.plugin.html;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import org.apache.commons.lang3.StringUtils;
import takiviko.guidegenerator.plugin.extension.style.HtmlStyle;

import static takiviko.guidegenerator.plugin.extension.style.DefaultStyle.DEFAULT_BACKGROUND_COLOR;
import static takiviko.guidegenerator.plugin.extension.style.DefaultStyle.DEFAULT_COLOR;
import static takiviko.guidegenerator.plugin.extension.style.DefaultStyle.DEFAULT_FONT;
import static takiviko.guidegenerator.plugin.extension.style.DefaultStyle.DEFAULT_FONT_SIZE;

public class CustomHtmlRenderer {

    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();

    /**
     * Renders the markdown string to HTML.
     *
     * @param markdown  the markdown string to be rendered
     * @param htmlStyle the style to be applied to the HTML
     * @return the rendered HTML string
     */
    public String render(String markdown, HtmlStyle htmlStyle) {
        Node document = parser.parse(markdown);
        String rawHtml = htmlRenderer.render(document);

        return getStyledHtml(rawHtml, htmlStyle);
    }

    /**
     * Gets the styled HTML.
     *
     * @param rawHtml   the raw HTML
     * @param htmlStyle the style to be applied to the HTML
     * @return the styled HTML
     */
    private String getStyledHtml(String rawHtml, HtmlStyle htmlStyle) {
        return "<body style=\"" + getStyleCss(htmlStyle) + "\">" + rawHtml + "</body>";
    }

    /**
     * Gets the CSS style.
     *
     * @param htmlStyle the style to be applied to the HTML
     * @return the CSS style
     */
    private String getStyleCss(HtmlStyle htmlStyle) {
        return getFontCss(htmlStyle.getFont()) +
            getColorCss(htmlStyle.getColor()) +
            getBackgroundColorCss(htmlStyle.getBackgroundColor()) +
            getFontSizeCss(htmlStyle.getFontSize()) +
            getTextAlignCss(htmlStyle.getTextAlign());
    }

    /**
     * Gets the font CSS expression.
     *
     * @param font the font to be applied to the HTML
     * @return the font CSS
     */
    private String getFontCss(String font) {
        if (StringUtils.isBlank(font)) {
            return String.format("font-family: '%s', sans-serif;", DEFAULT_FONT);
        }

        return String.format("font-family: '%s', sans-serif;", font);
    }

    /**
     * Gets the color CSS expression.
     *
     * @param color the color to be applied to the HTML
     * @return the color CSS
     */
    private String getColorCss(String color) {
        if (StringUtils.isBlank(color)) {
            return String.format("color: %s;", DEFAULT_COLOR);
        }

        return String.format("color: %s;", color);
    }

    /**
     * Returns the CSS style for background color.
     *
     * @param color the background color to be applied to the HTML
     * @return the CSS style for background color
     */
    private String getBackgroundColorCss(String color) {
        if (StringUtils.isBlank(color)) {
            return String.format("background-color: %s;", DEFAULT_BACKGROUND_COLOR);
        }

        return String.format("background-color: %s;", color);
    }

    /**
     * Returns the CSS style for font size.
     *
     * @param fontSize the font size to be applied to the HTML (in pixels)
     * @return the CSS style for font size
     */
    private String getFontSizeCss(Integer fontSize) {
        if (fontSize == 0) {
            return String.format("font-size: %spx;", DEFAULT_FONT_SIZE);
        }

        return String.format("font-size: %spx;", fontSize);
    }

    /**
     * Returns the CSS style for text alignment.
     *
     * @param textAlign the text alignment to be applied to the HTML
     * @return the CSS style for text alignment
     */
    private String getTextAlignCss(String textAlign) {
        if (StringUtils.isBlank(textAlign)) {
            return String.format("text-align: %s;", "left");
        }

        return String.format("text-align: %s;", textAlign);
    }
}
