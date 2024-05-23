package takiviko.guidegenerator.plugin.extension;

import lombok.Getter;
import lombok.Setter;
import takiviko.guidegenerator.plugin.extension.style.HtmlStyle;

/**
 * Extension for the guide generator plugin.
 * Holds information that is configurable for the user.
 */
@Getter
@Setter
public class GuideGeneratorPluginExtension {

    /**
     * Base (root) package of the project.
     * Only packages within this one will be added to the generated document.
     * <br>
     * Example: <i>takiviko.guidegenerator.plugin</i>
     */
    private String basePackage;

    private HtmlStyle htmlStyle;

}
