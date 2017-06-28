/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.edorasware.one.initializr.web.support;

import com.edorasware.one.initializr.metadata.*;
import com.edorasware.one.initializr.metadata.InitializrConfiguration.Env.Maven.ParentPom;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 * Easily create a {@link InitializrMetadata} instance for testing purposes.
 *
 * @author Stephane Nicoll
 */
public class OfflineInitializrMetadataBuilder {

    private final InitializrMetadataBuilder builder = InitializrMetadataBuilder.create();

    public static OfflineInitializrMetadataBuilder withDefaults() {
        return new OfflineInitializrMetadataBuilder().addDefaults();
    }

    public InitializrMetadata build() {
        return builder.build();
    }

    public OfflineInitializrMetadataBuilder addDefaults() {
        return addDefaultTypes()
                .addDefaultPackagings()
                .addDefaultJavaVersions()
                .addDefaultLanguages()
                .addDefaultEdorasoneVersions()
                .addDefaultBoms()
                .addDefaultDependencyGroups()
                .setGradleEnv("0.5.1.RELEASE")
                .setKotlinEnv("1.1.1");
    }

    public Dependency addDependency(String id, String name, String description, String groupId, String artifactId, String version) {
        Dependency dependency = new Dependency();
        dependency.setId(id);
        dependency.setName(name);
        dependency.setDescription(description);
        dependency.setGroupId(groupId);
        dependency.setArtifactId(artifactId);
        dependency.setVersion(version);
        dependency.setStarter(false);
        return dependency;
    }

    public OfflineInitializrMetadataBuilder addDefaultDependencyGroups() {
        return addDependencyGroup("Search",
                    addDependency("index", "Index", "Index Addon for elasticsearch index configuration", "com.edorasware.addons", "edoras-addon-index", "2.0.0")
                )
                .addDependencyGroup("Operation",
                        addDependency("dashboard", "Operator Dashboard", "Operator Dashboard for edoras one",  "com.edorasware.addons", "edoras-addon-operator-dashboard", "2.0.0"),
                        addDependency("logback-plugin", "Logback Plugin", "A logback plugin for the operator dashboard",  "com.edorasware.addons", "edoras-addon-operator-dashboard-logback", "2.0.0")
                )
                ;
    }

    public OfflineInitializrMetadataBuilder addDependencyGroup(String name, String... ids) {
        builder.withCustomizer(it -> {
            DependencyGroup group = new DependencyGroup();
            group.setName(name);
            for (String id : ids) {
                Dependency dependency = new Dependency();
                dependency.setId(id);
                group.getContent().add(dependency);
            }
            it.getDependencies().getContent().add(group);
        });
        return this;
    }

    public OfflineInitializrMetadataBuilder addDependencyGroup(String name,
                                                               Dependency... dependencies) {
        builder.withCustomizer(it -> {
            DependencyGroup group = new DependencyGroup();
            group.setName(name);
            group.getContent().addAll(Arrays.asList(dependencies));
            it.getDependencies().getContent().add(group);
        });
        return this;
    }

    public OfflineInitializrMetadataBuilder addDefaultTypes() {
        return addType("maven-project", "Maven Project", true, "/starter.zip", "maven", "project")
//				.addType("maven-build", "Maven Build", false, "/pom.xml", "maven", "build")
//				.addType("gradle-build", "Gradle Build", false, "/build.gradle", "gradle", "build")
				.addType("gradle-project", "Gradle Project", false, "/starter.zip", "gradle", "project")
                ;
    }

    public OfflineInitializrMetadataBuilder addType(String id, String name, boolean defaultValue,
                                                    String action, String build, String format) {
        Type type = new Type();
        type.setId(id);
        type.setName(name);
        type.setDefault(defaultValue);
        type.setAction(action);
        if (StringUtils.hasText(build)) {
            type.getTags().put("build", build);
        }
        if (StringUtils.hasText(format)) {
            type.getTags().put("format", format);
        }
        return addType(type);
    }

    public OfflineInitializrMetadataBuilder addType(Type type) {
        builder.withCustomizer(it -> it.getTypes().getContent().add(type));
        return this;
    }

    public OfflineInitializrMetadataBuilder addDefaultPackagings() {
        return addPackaging("jar", "Jar", false)
                .addPackaging("war", "War", true)
                ;
    }

    public OfflineInitializrMetadataBuilder addPackaging(String id, String name, boolean defaultValue) {
        builder.withCustomizer(it -> {
            DefaultMetadataElement packaging = new DefaultMetadataElement();
            packaging.setId(id);
            packaging.setName(name);
            packaging.setDefault(defaultValue);
            it.getPackagings().getContent().add(packaging);
        });
        return this;
    }

    public OfflineInitializrMetadataBuilder addDefaultJavaVersions() {
        return addJavaVersion("1.6", false)
                .addJavaVersion("1.7", false)
                .addJavaVersion("1.8", true)
                ;
    }

    public OfflineInitializrMetadataBuilder addJavaVersion(String version,
                                                           boolean defaultValue) {
        builder.withCustomizer(it -> {
            DefaultMetadataElement element = new DefaultMetadataElement();
            element.setId(version);
            element.setName(version);
            element.setDefault(defaultValue);
            it.getJavaVersions().getContent().add(element);
        });
        return this;
    }

    public OfflineInitializrMetadataBuilder addDefaultLanguages() {
        return addLanguage("java", "Java", true)
//				.addLanguage("groovy", "Groovy", false)
//				.addLanguage("kotlin", "Kotlin", false)
                ;
    }

    public OfflineInitializrMetadataBuilder addLanguage(String id, String name, boolean defaultValue) {
        builder.withCustomizer(it -> {
            DefaultMetadataElement element = new DefaultMetadataElement();
            element.setId(id);
            element.setName(name);
            element.setDefault(defaultValue);
            it.getLanguages().getContent().add(element);
        });
        return this;
    }

    public OfflineInitializrMetadataBuilder addDefaultEdorasoneVersions() {
        return addEdorasoneVersion("2.0.0-M6-SNAPSHOT", true)
				.addEdorasoneVersion("1.6.6", false)
                ;
    }

    public OfflineInitializrMetadataBuilder addEdorasoneVersion(String id, boolean defaultValue) {
        builder.withCustomizer(it -> {
            DefaultMetadataElement element = new DefaultMetadataElement();
            element.setId(id);
            element.setName(id);
            element.setDefault(defaultValue);
            it.getEdorasoneVersions().getContent().add(element);
        });
        return this;
    }

    public OfflineInitializrMetadataBuilder addDefaultBoms() {
        return addBom("index", "com.edorasware.addons.index", "edoras-addon-index", "2.0.0")
                ;
    }

    public OfflineInitializrMetadataBuilder addBom(String id, String groupId,
                                                   String artifactId, String version) {
        BillOfMaterials bom = BillOfMaterials.create(groupId, artifactId, version);
        return addBom(id, bom);
    }

    public OfflineInitializrMetadataBuilder addBom(String id, BillOfMaterials bom) {
        builder.withCustomizer(it -> it.getConfiguration().getEnv()
                .getBoms().put(id, bom));
        return this;
    }

    public OfflineInitializrMetadataBuilder setGradleEnv(String dependencyManagementPluginVersion) {
        builder.withCustomizer(it -> it.getConfiguration().getEnv().getGradle().
                setDependencyManagementPluginVersion(dependencyManagementPluginVersion));
        return this;
    }

    public OfflineInitializrMetadataBuilder setKotlinEnv(String kotlinVersion) {
        builder.withCustomizer(it -> it.getConfiguration().getEnv()
                .getKotlin().setVersion(kotlinVersion));
        return this;
    }

    public OfflineInitializrMetadataBuilder setMavenParent(String groupId, String artifactId,
                                                           String version) {
        builder.withCustomizer(it -> {
            ParentPom parent = it.getConfiguration().getEnv().getMaven().getParent();
            parent.setGroupId(groupId);
            parent.setArtifactId(artifactId);
            parent.setVersion(version);
        });
        return this;
    }

    public OfflineInitializrMetadataBuilder addRepository(String id, String name, String url,
                                                          boolean snapshotsEnabled) {
        builder.withCustomizer(it -> {
            Repository repo = new Repository();
            repo.setName(name);
            try {
                repo.setUrl(new URL(url));
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Cannot create URL", e);
            }
            repo.setSnapshotsEnabled(snapshotsEnabled);
            it.getConfiguration().getEnv().getRepositories().put(id, repo);
        });
        return this;
    }

}