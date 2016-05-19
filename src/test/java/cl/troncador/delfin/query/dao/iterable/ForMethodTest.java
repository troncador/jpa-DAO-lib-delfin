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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;

import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.db.query.OfficeCityQuery;
import cl.troncador.delfin.db.table.Office;
import cl.troncador.delfin.db.table.OfficeCity;
import cl.troncador.delfin.db.table.OfficeCity_;
import cl.troncador.delfin.db.table.Office_;
import cl.troncador.delfin.model.table.SimpleQuery;
import cl.troncador.delfin.query.constraint.PreparePredicate;
import cl.troncador.delfin.query.dao.iterate.TableIterable;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class ForMethodTest {
  private final static int NUMBER_ELEMENTS = 10;
  
  @Test
  public void forMethod() throws QueryException {
    EntityManagerFactory entityManagerFactory = null;
    EntityManager entityManager = null;
    try {
      PodamFactory factory = new PodamFactoryImpl();

      entityManagerFactory = Persistence
          .createEntityManagerFactory("eclipselink");
      entityManager = entityManagerFactory.createEntityManager();

      SimpleQuery<Office> query = new SimpleQuery<Office>(Office.class, entityManager);
      
      PreparePredicate<Office> preparePredicate = new PreparePredicate<Office>() {

        @Override
        public Predicate getPredicate(Root<Office> root,
            CriteriaBuilder criteriaBuilder) {
          Path<String> cityName = root.join(Office_.officeCity).get(OfficeCity_.name);
          Path<Integer> officeId = root.get(Office_.id);
          Path<String> officeName = root.get(Office_.name);
          return criteriaBuilder.and(
              criteriaBuilder.equal(cityName, "London"),
              criteriaBuilder.greaterThan(officeId, 2),
              criteriaBuilder.notEqual(officeName, 2)
           );
        }
      };
      Iterable<Office> iterable = new TableIterable<Office>(
          query, 
          preparePredicate, 
          10,
          TableIterable.Mode.WHILE);
      for(Office office: iterable){
        
      }
//      for(Office office: new TableIterable<Enterprise>(query, preparePredicate,10,Mode.WHILE)){
//        
//        
//      }
      //TODO

      query.deleteAll();

      assertEquals(query.count(), 0);
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
