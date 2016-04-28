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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.troncador.delfin.QueryException;

// TODO: Auto-generated Javadoc
/**
 * The Class SaveMethod.
 *
 * @param <T> the generic type
 */
class SaveMethod<T> {
  
  /** The Constant log. */
  private static final Logger log = LoggerFactory.getLogger(SaveMethod.class);
  
  /** The tclass. */
  private Class< T > tclass ;
  
  /** The entity manager. */
  private EntityManager entityManager;
  
  /**
   * Instantiates a new save method.
   *
   * @param tclass the tclass
   * @param entityManager the entity manager
   */
  SaveMethod(Class<T> tclass, EntityManager entityManager){
    this.tclass = tclass;
    this.entityManager = entityManager;
  }
  
  /**
   * Save.
   *
   * @param entity the entity
   * @return the t
   * @throws QueryException the query exception
   */
  T save(T entity) throws QueryException{
    try {
      EntityTransaction entityTransaction =  entityManager.getTransaction();
      entityTransaction.begin();
      entityManager.persist(entity);
      entityTransaction.commit(); 
      return entity;
    } catch (PersistenceException e) {
      throw new QueryException(e.getMessage(), e);
    } catch (Exception e) {
      throw new QueryException(e.getMessage(), e);
    }
  }
  
  /**
   * Save.
   *
   * @param entityList the entity list
   * @return the list
   * @throws QueryException the query exception
   */
  List<T> save(List<T> entityList) throws QueryException{
    try {
      EntityTransaction entityTransaction =  entityManager.getTransaction();
      entityTransaction.begin();
      for(T entity: entityList){
        entityManager.persist(entity);
      }
      entityTransaction.commit();
      return entityList;
    } catch (PersistenceException e) {
      log.info(e.getMessage(),e);
      throw new QueryException(e.getMessage(), e);
    } catch (Exception e) {
      throw new QueryException(e.getMessage(), e);
    }
  }
}
