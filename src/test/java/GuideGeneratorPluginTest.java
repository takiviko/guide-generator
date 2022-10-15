import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GuideGeneratorPluginTest {

    @Test
    void testPlugin() {
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("guide-generator");
        Assertions.assertNotNull(project.getTasks().getByName("generate-guide"));
    }

}
