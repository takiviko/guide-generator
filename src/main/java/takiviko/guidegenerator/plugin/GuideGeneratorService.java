package takiviko.guidegenerator.plugin;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import takiviko.guidegenerator.annotation.Documentation;
import takiviko.guidegenerator.annotation.DocumentationEntity;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Service for indexing and extracting documentation from a target project.
 */
public class GuideGeneratorService {

    /**
     * Returns a list of markdown strings from the target project.
     *
     * @param basePackage the base package of the target project
     * @param classLoader the classloader of the target project
     * @return a list of markdown strings extracted from the target project
     */
    public List<String> getMarkdownStrings(String basePackage, ClassLoader classLoader) {
        Reflections reflections = new Reflections(
            ClasspathHelper.forPackage(basePackage, classLoader),
            ClasspathHelper.forPackage(Documentation.class.getPackageName(), classLoader)
        );

        List<? extends Class<?>> classes = getClassList(reflections, classLoader);

        Class<?> documentationClass = classes.stream()
            .filter(Class::isAnnotation)
            .filter((aClass) -> aClass.getPackageName().equals(Documentation.class.getPackageName()))
            .filter((aClass) -> aClass.getName().equals(Documentation.class.getName()))
            .findFirst()
            .orElseThrow();

        Annotation internalAnnotationType = classes.stream()
            .flatMap((aClass) -> Arrays.stream(aClass.getAnnotations()))
            .filter((annotation) -> annotation.annotationType().getName().equals(Documentation.class.getName()))
            .findFirst()
            .orElseThrow();

        return classes.stream()
            .filter((aClass) -> aClass.getPackageName().startsWith(basePackage))
            .sorted(Comparator.comparing(Class::getPackageName))
            .map((aClass) -> aClass.getAnnotation(internalAnnotationType.annotationType()))
            .filter(Objects::nonNull)
            .map((annotation) -> this.getDocumentation(documentationClass, annotation))
            .sorted(Comparator.comparingInt(DocumentationEntity::getOrder))
            .map(DocumentationEntity::getDocumentation)
            .toList();
    }

    /**
     * Returns the list of all classes from the target project.
     *
     * @param classLoader the classloader instance
     * @param reflections reflections instance
     * @return the list of all classes from the target project
     */
    private List<? extends Class<?>> getClassList(Reflections reflections, ClassLoader classLoader) {
        return reflections.getAll(Scanners.TypesAnnotated).stream()
            .map((className) -> reflections.forClass(className, classLoader))
            .filter(Objects::nonNull)
            .toList();
    }

    private DocumentationEntity getDocumentation(Class<?> documentationClass, Annotation annotation) {
        try {
            return DocumentationEntity.builder().documentation(documentationClass.getMethod("documentation").invoke(annotation).toString()).order(Integer.parseInt(documentationClass.getMethod("order").invoke(annotation).toString())).build();
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException var4) {
            throw new RuntimeException(var4);
        }
    }
}