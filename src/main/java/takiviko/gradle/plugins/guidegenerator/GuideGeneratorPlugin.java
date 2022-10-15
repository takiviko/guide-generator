package takiviko.gradle.plugins.guidegenerator;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GuideGeneratorPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.task("generate-guide")
            .doLast(task -> System.out.println("Hello"));
    }
}
