package takiviko.guidegenerator.annotation;

import lombok.Builder;
import lombok.Getter;

/**
 * Entity class for documentation.
 */
@Getter
@Builder
public class DocumentationEntity {

    /**
     * Documentation written in Markdown format.
     */
    private String documentation;

    /**
     * The order of the specific section of documentation in the final document.
     * <p>
     * It should only be used if the intent of the order is to place items
     * in the very beginning or end of the document as the ordering may be
     * difficult to maintain when adding/removing items.
     */
    private int order;

}
