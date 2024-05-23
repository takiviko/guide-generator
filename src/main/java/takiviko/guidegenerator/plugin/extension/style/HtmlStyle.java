package takiviko.guidegenerator.plugin.extension.style;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static takiviko.guidegenerator.plugin.extension.style.DefaultStyle.DEFAULT_BACKGROUND_COLOR;
import static takiviko.guidegenerator.plugin.extension.style.DefaultStyle.DEFAULT_COLOR;
import static takiviko.guidegenerator.plugin.extension.style.DefaultStyle.DEFAULT_FONT;
import static takiviko.guidegenerator.plugin.extension.style.DefaultStyle.DEFAULT_FONT_SIZE;

@Setter
@Getter
@Builder
public class HtmlStyle {

    @Builder.Default
    private String font = DEFAULT_FONT;

    @Builder.Default
    private String color = DEFAULT_COLOR;

    @Builder.Default
    private String backgroundColor = DEFAULT_BACKGROUND_COLOR;

    @Builder.Default
    private Integer fontSize = DEFAULT_FONT_SIZE;

    @Builder.Default
    private String textAlign = "left";

}
