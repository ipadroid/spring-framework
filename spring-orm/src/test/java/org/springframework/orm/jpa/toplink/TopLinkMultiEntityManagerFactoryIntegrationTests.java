/*
 * Copyright 2002-2012 the original author or authors.
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

package org.springframework.orm.jpa.toplink;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Ignore;
import org.springframework.orm.jpa.AbstractContainerEntityManagerFactoryIntegrationTests;

/**
 * Toplink-specific JPA tests with multiple EntityManagerFactory instances.
 *
 * @author Costin Leau
 */
// TODO SPR-8116 this test causes gradle to hang. See OJEMFWAJWIT.
@Ignore("this test causes gradle to hang. See OJEMFWAJWIT.")
public class TopLinkMultiEntityManagerFactoryIntegrationTests extends
		AbstractContainerEntityManagerFactoryIntegrationTests {

	private EntityManagerFactory entityManagerFactory2;


	@SuppressWarnings("deprecation")
	public TopLinkMultiEntityManagerFactoryIntegrationTests() {
		setAutowireMode(AUTOWIRE_BY_NAME);
	}

	public void setEntityManagerFactory2(EntityManagerFactory entityManagerFactory2) {
		this.entityManagerFactory2 = entityManagerFactory2;
	}

	protected String[] getConfigLocations() {
		return new String[] {
			"/org/springframework/orm/jpa/toplink/toplink-manager-multi.xml",
			"/org/springframework/orm/jpa/memdb.xml"
		};
	}


	public void testEntityManagerFactory2() {
		EntityManager em = this.entityManagerFactory2.createEntityManager();
		try {
			em.createQuery("select tb from TestBean");
			fail("Should have thrown IllegalArgumentException");
		}
		catch (IllegalArgumentException ex) {
			// expected
		}
		finally {
			em.close();
		}
	}

}