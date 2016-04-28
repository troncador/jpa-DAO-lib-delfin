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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.QueryExceptionKind;
import cl.troncador.delfin.query.constraint.PreparePredicate;

// TODO: Auto-generated Javadoc
/**
 * The Class UpdateMethod.
 *
 * @param <T> the generic type
 */
class UpdateMethod<T> {
  
  /** The log. */
  static Logger log = LoggerFactory.getLogger(UpdateMethod.class);
  
  /** The tclass. */
  private Class< T > tclass ;
  
  /** The entity manager. */
  private EntityManager entityManager;
  
  /**
   * Instantiates a new update method.
   *
   * @param tclass the tclass
   * @param entityManager the entity manager
   */
  UpdateMethod(Class<T> tclass, EntityManager entityManager){
    this.tclass = tclass;
    this.entityManager = entityManager;
  }
  
  /**
   * Update.
   *
   * @param entityList the entity list
   * @return the list
   * @throws QueryException the query exception
   */
  List<T>  update(List<T> entityList) throws QueryException{
    List<T>  entityCommitedList = new ArrayList<T>();
    
    try{
      EntityTransaction entityTransaction = entityManager.getTransaction();
      entityTransaction.begin();
      for( T entity: entityList){
        T entityCommited = entityManager.merge(entity);
        entityCommitedList.add(entityCommited);
      }
      entityTransaction.commit();
      return entityCommitedList;
    } catch (NoResultException e) {
      String msg = String.format("Can't  find in database: %s", tclass.getName());
      throw new QueryException(QueryExceptionKind.NO_RESULT, msg);
    } catch (Exception e) {
      throw new QueryException(e.getMessage(), e);
    }
  }
  
  
  /**
   * Update.
   *
   * @param entity the entity
   * @return the t
   * @throws QueryException the query exception
   */
  T update(T entity) throws QueryException{
    try{
      EntityTransaction entityTransaction = entityManager.getTransaction();
      entityTransaction.begin();
      T entityCommited = entityManager.merge(entity);
      entityTransaction.commit();
      return entityCommited;  
    } catch (NoResultException e) {
      String msg = String.format("Can't  find in database: %s", tclass.getName());
      throw new QueryException(QueryExceptionKind.NO_RESULT, msg);
    } catch (Exception e) {
      throw new QueryException(e.getMessage(), e);
    }
  }
  
}
