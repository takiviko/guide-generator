package takiviko.guidegenerator.plugin;

import lombok.AllArgsConstructor;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import takiviko.guidegenerator.annotation.Documentation;
import takiviko.guidegenerator.annotation.DocumentationEntity;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Service for indexing and extracting documentation from a target project.
 */
@AllArgsConstructor
public class GuideGeneratorService {

    /**
     * Creates a new instance of the service.
     *
     * @return a new instance of the service
     */
    public List<String> getMarkdownStrings(String basePackage, ClassLoader classLoader) {
        Reflections reflections = new Reflections(ClasspathHelper.forPackage(basePackage, classLoader));
        List<Class<?>> classes = reflections.getTypesAnnotatedWith(Documentation.class).stream()
            .filter(Objects::nonNull)
            .toList();

        return classes.stream()
            .filter(clazz -> clazz.getPackageName().startsWith(basePackage))
            .sorted(Comparator.comparing(Class::getPackageName))
            .map(clazz -> clazz.getAnnotation(Documentation.class))
            .filter(Objects::nonNull)
            .map(this::getDocumentation)
            .sorted(Comparator.comparingInt(DocumentationEntity::getOrder))
            .map(DocumentationEntity::getDocumentation)
            .toList();
    }

    private DocumentationEntity getDocumentation(Documentation annotation) {
        return DocumentationEntity.builder()
            .documentation(annotation.documentation())
            .order(annotation.order())
            .build();
    }
}