/*
 * Copyright 2011-2013 the original author or authors.
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
package org.springframework.data.jpa.repository.cdi;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.Bean;

import org.apache.webbeans.cditest.CdiTestContainer;
import org.apache.webbeans.cditest.CdiTestContainerLoader;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Integration tests for Spring Data JPA CDI extension.
 * 
 * @author Dirk Mahler
 * @author Oliver Gierke
 */
public class CdiExtensionIntegrationTests {

	static CdiTestContainer container;

	@BeforeClass
	public static void setUp() throws Exception {
		container = CdiTestContainerLoader.getCdiContainer();
		container.bootContainer();
	}

	/**
	 * @see DATAJPA-319
	 */
	@Test
	@SuppressWarnings("rawtypes")
	public void foo() {

		Set<Bean<?>> beans = container.getBeanManager().getBeans(PersonRepository.class);

		assertThat(beans, hasSize(1));
		assertThat(beans.iterator().next().getScope(), is(equalTo((Class) ApplicationScoped.class)));
	}

	@Test
	public void saveAndFindAll() {

		RepositoryConsumer repositoryConsumer = container.getInstance(RepositoryConsumer.class);

		Person person = new Person();
		repositoryConsumer.save(person);
		repositoryConsumer.findAll();
	}
}
