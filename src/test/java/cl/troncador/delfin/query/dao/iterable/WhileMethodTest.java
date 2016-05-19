/*******************************************************************************
 *  Copyright 2016 Benjamin Almarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package cl.troncador.delfin.query.dao.iterable;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.db.query.OfficeCityQuery;
import cl.troncador.delfin.db.table.OfficeCity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class WhileMethodTest {
  private final static int NUMBER_ELEMENTS = 10;
  
  @Test
  public void whileMethod() throws QueryException {
    EntityManagerFactory entityManagerFactory = null;
    EntityManager entityManager = null;
    try {
      PodamFactory factory = new PodamFactoryImpl();

      entityManagerFactory = Persistence
          .createEntityManagerFactory("eclipselink");
      entityManager = entityManagerFactory.createEntityManager();

      OfficeCityQuery officeCityQuery = new OfficeCityQuery(entityManager);

      List<OfficeCity> officeList = new ArrayList<OfficeCity>();

      //TODO

      officeCityQuery.deleteAll();


      assertEquals(officeCityQuery.count(), 0);
    } finally {
      if (entityManager != null) {
        entityManager.close();
      }
      if (entityManagerFactory != null) {
        entityManagerFactory.close();
      }
    }
  }

}
