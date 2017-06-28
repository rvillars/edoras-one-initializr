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

import com.edorasware.one.initializr.metadata.DefaultMetadataElement;
import com.edorasware.one.initializr.metadata.InitializrMetadata;
import com.edorasware.one.initializr.metadata.InitializrMetadataBuilder;
import com.edorasware.one.initializr.metadata.InitializrMetadataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * A default {@link InitializrMetadataProvider} that is able to refresh
 * the metadata with the status of the main spring.io site.
 *
 * @author Stephane Nicoll
 */
public class DefaultInitializrMetadataProvider implements InitializrMetadataProvider {

	private static final Logger log = LoggerFactory
			.getLogger(DefaultInitializrMetadataProvider.class);

	private final InitializrMetadata metadata;
	private final RestTemplate restTemplate;

	private final InitializrMetadataBuilder builder = InitializrMetadataBuilder.create();

	public DefaultInitializrMetadataProvider(InitializrMetadata metadata,
			RestTemplate restTemplate) {
		this.metadata = metadata;
		this.restTemplate = restTemplate;
	}

	@Override
	@Cacheable(value = "initializr", key = "'metadata'")
	public InitializrMetadata get() {
		updateInitializrMetadata(metadata);
		return metadata;
	}

	protected void updateInitializrMetadata(InitializrMetadata metadata) {
		List<DefaultMetadataElement> edorasoneVersions = fetchEdorasoneVersions();
		if (edorasoneVersions != null && !edorasoneVersions.isEmpty()) {
			if (edorasoneVersions.stream().noneMatch(DefaultMetadataElement::isDefault)) {
				// No default specified
				edorasoneVersions.get(0).setDefault(true);
			}
			metadata.updateEdorasoneVersions(edorasoneVersions);
		}
	}

	protected List<DefaultMetadataElement> fetchEdorasoneVersions() {
//		String url = metadata.getConfiguration().getEnv().getEdorasoneMetadataUrl();
//		if (StringUtils.hasText(url)) {
//			try {
//				log.info("Fetching boot metadata from {}", url);
//				return new SpringBootMetadataReader(restTemplate, url).getEdorasoneVersions();
//			}
//			catch (Exception e) {
//				log.warn("Failed to fetch spring boot metadata", e);
//			}
//		}

		List<DefaultMetadataElement> edorasoneVersions = new ArrayList<>();

//		DefaultMetadataElement element1 = new DefaultMetadataElement();
//		element1.setId("1.6.5");
//		element1.setName("1.6.5");
//		element1.setDefault(false);
//		edorasoneVersions.add(element1);
//
//		DefaultMetadataElement element2 = new DefaultMetadataElement();
//		element2.setId("2.0.0-M5");
//		element2.setName("2.0.0-M5");
//		element2.setDefault(true);
//		edorasoneVersions.add(element2);

		return edorasoneVersions;
	}
}
