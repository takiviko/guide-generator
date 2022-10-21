package takiviko.guidegenerator.annotation;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DocumentationEntity {

    private String documentation;
    private int order;

}
