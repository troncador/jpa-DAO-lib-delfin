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
package cl.troncador.delfin.query.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.db.query.OfficeCityQuery;
import cl.troncador.delfin.db.table.Office;
import cl.troncador.delfin.db.table.OfficeCity;
import cl.troncador.delfin.db.table.OfficeCity_;
import cl.troncador.delfin.db.table.Office_;
import cl.troncador.delfin.model.table.SimpleQuery;
import cl.troncador.delfin.query.constraint.PreparePredicate;
import cl.troncador.delfin.query.constraint.PrepareQueryAdapter;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class SaveMethodTest {
  static Logger log = LoggerFactory.getLogger(SaveMethodTest.class);
  private final static int NUMBER_ELEMENTS = 10;
  
  @Test
  public void save() throws QueryException {
    EntityManagerFactory entityManagerFactory = null;
    EntityManager entityManager = null;
    try {
      PodamFactory factory = new PodamFactoryImpl(); 
      
      entityManagerFactory=Persistence.createEntityManagerFactory("eclipselink");
      entityManager = entityManagerFactory.createEntityManager();
      
      
      
      SimpleQuery<Office> officeQuery = new SimpleQuery<Office>(Office.class, entityManager);
      
      // the where condition
//      PreparePredicate<Office> preparePredicate = new PreparePredicate<Office>() {
//        @Override
//        public Predicate getPredicate(Root<Office> root,
//            CriteriaBuilder cb) {
//          Path<String> cityName = root.join(Office_.officeCity).get(OfficeCity_.name);
//          Path<Integer> officeId = root.get(Office_.id);
//          Path<String> officeName = root.get(Office_.name);
//          return cb.and(
//             cb.equal(cityName, "London"),
//             cb.greaterThan(officeId, 2),
//             cb.notEqual(officeName, 2)
//          );
//        }
//      };
//      
//      PrepareQueryAdapter<Office> prepareQueryAdapter = new PrepareQueryAdapter<Office>(preparePredicate);
//      
//      List<Office> listQuery = officeQuery.select(prepareQueryAdapter);
//      
//      long count = officeQuery.count(prepareQueryAdapter);
//      
//      long count = officeQuery.(prepareQueryAdapter);
      
      
      List<OfficeCity> officeList = new ArrayList<OfficeCity>();
      
      for (int i=0; i < NUMBER_ELEMENTS; i++) {
        OfficeCity officeCity = factory.manufacturePojo(OfficeCity.class);
        officeList.add(officeCity);
      }

      
      List<Office> officeList2 = officeQuery.select();
      
      Collections.sort(officeList);
      Collections.sort(officeList2);
      
      for (int i =0; i < NUMBER_ELEMENTS ; i++) {
        assertEquals(officeList2.get(i),officeList.get(i));
      }
      officeQuery.deleteAll();
    } finally {
      if (entityManager != null) {
        entityManager.close();
      }
      if (entityManagerFactory != null) {
        entityManagerFactory.close();
      }
    }
  }
  @Test
  public void saveList() throws Exception {
    EntityManagerFactory entityManagerFactory = null;
    EntityManager entityManager = null;
    try {
    	PodamFactory factory = new PodamFactoryImpl();	
    	
      entityManagerFactory=Persistence.createEntityManagerFactory("eclipselink");
      entityManager = entityManagerFactory.createEntityManager();
      
      SimpleQuery<OfficeCity> officeQuery = 
          new SimpleQuery<OfficeCity>(OfficeCity.class, entityManager);
      
      List<OfficeCity> officeList = new ArrayList<OfficeCity>();
      
      for (int i=0; i<100; i++) {
        OfficeCity officeCity = factory.manufacturePojo(OfficeCity.class);
    	  officeList.add(officeCity);
      }
      
      officeQuery.save(officeList);

      officeQuery.select();
      
      officeQuery.deleteAll();
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
