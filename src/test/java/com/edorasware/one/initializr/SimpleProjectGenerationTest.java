package com.edorasware.one.initializr;

import com.edorasware.one.initializr.generator.ProjectGenerator;
import com.edorasware.one.initializr.generator.ProjectRequest;
import com.edorasware.one.initializr.generator.ProjectRequestResolver;
import com.edorasware.one.initializr.metadata.Dependency;
import com.edorasware.one.initializr.metadata.InitializrMetadata;
import com.edorasware.one.initializr.metadata.SimpleInitializrMetadataProvider;
import com.edorasware.one.initializr.test.metadata.InitializrMetadataTestBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.ZipFileSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.context.ApplicationEventPublisher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;

/**
 * Created by rovi on 21.06.17.
 */
public class SimpleProjectGenerationTest {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    protected final ProjectGenerator projectGenerator = new ProjectGenerator();

    private final ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);

    @Before
    public void setup() throws IOException {
        Dependency web = Dependency.withId("web");
        web.getFacets().add("web");
        InitializrMetadata metadata = InitializrMetadataTestBuilder.withDefaults().build();
        projectGenerator.setMetadataProvider(new SimpleInitializrMetadataProvider(metadata));
        projectGenerator.setEventPublisher(eventPublisher);
        projectGenerator
                .setRequestResolver(new ProjectRequestResolver(new ArrayList<>()));
        projectGenerator.setTmpdir(folder.newFolder().getAbsolutePath());
    }

    @Test
    public void testStandardWar() throws IOException {
        ProjectRequest request = new ProjectRequest();
        request.initialize(projectGenerator.getMetadataProvider().get());
        request.setType("maven-project");
        request.setPackaging("war");
        request.setLanguage("java");
//        request.setGroupId("com.edorasware.one");
//        request.setPackageName("com.edorasware.one.initializr");
//        request.setArtifactId("edoras-one-initializr");
//        request.setShortName("initializr");
        request.setCreateSampleTest(true);
        File dir = projectGenerator.generateProjectStructure(request);

        System.out.print(printDirectoryTree(dir));

        File outputDir = new File("/Users/rovi/Desktop/Project/edorasone");
        FileUtils.deleteDirectory(outputDir);
        FileUtils.moveDirectory(dir, outputDir);

//        File zip = projectGenerator.createDistributionFile(dir, ".zip");
//        createZipFile(dir, zip);
//        Assert.assertTrue(zip.exists());
    }

    private void createZipFile(File dir, File zip) throws IOException {
        dir.setExecutable(true);
        Zip zipper = new Zip();
        zipper.setProject(new Project());
        zipper.setDefaultexcludes(false);
        ZipFileSet set = new ZipFileSet();
        set.setDir(dir);
        set.setFileMode("755");
        set.setDefaultexcludes(false);
        zipper.addFileset(set);
        set = new ZipFileSet();
        set.setDir(dir);
        set.setIncludes("**,");
        set.setDefaultexcludes(false);
        zipper.addFileset(set);
        zipper.setDestFile(zip.getCanonicalFile());
        zipper.execute();
    }

    public static String printDirectoryTree(File folder) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        int indent = 0;
        StringBuilder sb = new StringBuilder();
        printDirectoryTree(folder, indent, sb);
        return sb.toString();
    }

    private static void printDirectoryTree(File folder, int indent,
                                           StringBuilder sb) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(folder.getName());
        sb.append("/");
        sb.append("\n");
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                printDirectoryTree(file, indent + 1, sb);
            } else {
                printFile(file, indent + 1, sb);
            }
        }

    }

    private static void printFile(File file, int indent, StringBuilder sb) {
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(file.getName());
        sb.append("\n");
    }

    private static String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("|  ");
        }
        return sb.toString();
    }
}
