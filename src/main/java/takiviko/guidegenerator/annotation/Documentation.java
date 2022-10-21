package takiviko.guidegenerator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Documentation {

        /**
         * Documentation written in Markdown format.
         */
        String documentation();

        /**
         * The order of the specific section of documentation.
         * <p>
         * It should only be used if the intent of the order is to place items
         * in the very beginning or end of the document as the ordering may be
         * difficult to maintain when adding/removing items.
         */
        int order() default 0;

}
