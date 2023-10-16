package takiviko.guidegenerator.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import takiviko.guidegenerator.annotation.Documentation;
import takiviko.guidegenerator.annotation.DocumentationEntity;

/**
 * Service for indexing and extracting documentation from a target project.
 */
public class GuideGeneratorService {

    /**
     * Constructs a new instance of the service.
     *
     * @return the new {@link GuideGeneratorService} instance
     */
    public static GuideGeneratorService newService() {
        return new GuideGeneratorService();
    }

    /**
     * Fetches all documentation in the target project accessible by the classloader.
     *
     * @param basePackage root package of the target project
     * @param classLoader class loader having access to the target project
     * @return list of all markdown documentation in the target project inside {@link Documentation} annotations
     */
    public List<String> getMarkdownStrings(String basePackage, ClassLoader classLoader) {

        Reflections reflections = new Reflections(
            ClasspathHelper.forPackage(basePackage, classLoader),
            ClasspathHelper.forPackage(Documentation.class.getPackageName(), classLoader)
        );

        var classNames = reflections.getAll(Scanners.TypesAnnotated);

        var classes = classNames.stream()
            .map(className -> reflections.forClass(className, classLoader))
            .filter(Objects::nonNull)
            .toList();

        var documentationClass = classes.stream()
            .filter(Class::isAnnotation)
            .filter(aClass -> aClass.getPackageName().equals(Documentation.class.getPackageName()))
            .filter(aClass -> aClass.getName().equals(Documentation.class.getName()))
            .findFirst()
            .orElseThrow();

        Annotation internalAnnotationType = classes.stream()
            .flatMap(aClass -> Arrays.stream(aClass.getAnnotations()))
            .filter(annotation -> annotation.annotationType().getName().equals(Documentation.class.getName()))
            .findFirst()
            .orElseThrow();

        return classes.stream()
            .filter(aClass -> aClass.getPackageName().startsWith(basePackage))
            .sorted(Comparator.comparing(Class::getPackageName))
            .map(aClass -> aClass.getAnnotation(internalAnnotationType.annotationType()))
            .filter(Objects::nonNull)
            .map(annotation -> getDocumentation(documentationClass, annotation))
            .sorted(Comparator.comparingInt(DocumentationEntity::getOrder))
            .map(DocumentationEntity::getDocumentation)
            .toList();
    }

    private DocumentationEntity getDocumentation(Class<?> documentationClass, Annotation annotation) {
        try {
            return DocumentationEntity.builder()
                .documentation(documentationClass.getMethod("documentation").invoke(annotation).toString())
                .order(Integer.parseInt(documentationClass.getMethod("order").invoke(annotation).toString()))
                .build();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}