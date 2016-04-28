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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.db.query.OfficeCityQuery;
import cl.troncador.delfin.db.table.OfficeCity;
import cl.troncador.delfin.model.table.SimpleQuery;
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
      
      OfficeCityQuery officeCityQuery = new OfficeCityQuery(entityManager);
      
      List<OfficeCity> officeList = new ArrayList<OfficeCity>();
      
      for (int i=0; i < NUMBER_ELEMENTS; i++) {
        OfficeCity officeCity = factory.manufacturePojo(OfficeCity.class);
        officeList.add(officeCity);
      }

      
      List<OfficeCity> officeList2 = officeCityQuery.select();
      
      Collections.sort(officeList);
      Collections.sort(officeList2);
      
      for (int i =0; i < NUMBER_ELEMENTS ; i++) {
        assertEquals(officeList2.get(i),officeList.get(i));
      }
      officeCityQuery.deleteAll();
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
