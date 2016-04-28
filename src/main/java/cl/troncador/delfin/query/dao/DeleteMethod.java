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
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.QueryExceptionKind;
import cl.troncador.delfin.query.constraint.PreparePredicate;
import cl.troncador.delfin.query.constraint.PrepareQuery;

// TODO: Auto-generated Javadoc
/**
 * The Class DeleteMethod.
 *
 * @param <T> the generic type
 * @param <PK> the generic type
 */
class DeleteMethod<T,PK> {
  
  /** The Constant log. */
  private static final Logger log = LoggerFactory.getLogger(DeleteMethod.class);
  
  /** The tclass. */
  private Class< T > tclass ;
  
  /** The entity manager. */
  private EntityManager entityManager;
  
  /** The pk. */
  private SingularAttribute<? super T, PK> pk;

  /**
   * Instantiates a new delete method.
   *
   * @param tclass the tclass
   * @param entityManager the entity manager
   */
  DeleteMethod(Class<T> tclass, SingularAttribute<? super T, PK> pk, EntityManager entityManager){
    this.tclass = tclass;
    this.entityManager = entityManager;
    this.pk = pk;
  }
  
  
  /**
   * Delete.
   *
   * @param entity the entity
   * @throws QueryException the query exception
   */
  void delete(T entity) throws QueryException {
    try{
      EntityTransaction entityTransaction = entityManager.getTransaction();
      entityTransaction.begin();
      T entityAttached = entityManager.merge(entity);
      entityManager.remove(entityAttached);
      entityTransaction.commit();
    } catch (NoResultException e) {
      String msg = String.format("Can't  find in database: %s", tclass.getName());
      throw new QueryException(QueryExceptionKind.NO_RESULT, msg);
    } catch (Exception e) {
      throw new QueryException(e.getMessage(),e);
    }
  }

  
  /**
   * Delete.
   *
   * @param entityList the entity list
   * @throws QueryException the query exception
   */
  void delete(List<T> entityList) throws QueryException {
    try{
      EntityTransaction entityTransaction = entityManager.getTransaction();
      entityTransaction.begin();
      for(T entity: entityList){
        T entityAttached = entityManager.merge(entity);
        entityManager.remove(entityAttached);
      }
      entityTransaction.commit();
    } catch (NoResultException e) {
      String msg = String.format("Can't  find in database: %s", tclass.getName());
      throw new QueryException(QueryExceptionKind.NO_RESULT, msg);
    } catch (Exception e) {
      throw new QueryException(e.getMessage(),e);
    }
  }
  
  /**
   * Delete by pk.
   *
   * @param id the id
   * @throws QueryException the query exception
   */
  void deleteByPK(PK id) throws QueryException {
    try {
      T entity = entityManager.find(tclass,id);
      if(entity != null){
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.remove(entity);
        entityTransaction.commit();  
      }else{
        String msg = String.format("Can't  find in database: %s", tclass.getName());
        throw new QueryException(QueryExceptionKind.NO_RESULT, msg);
      }
    } catch (IllegalStateException e) {
      throw new QueryException(e.getMessage(), e);
    } catch (IllegalArgumentException e) {
      throw new QueryException(e.getMessage(), e);
    }
  }
  
  
  /**
   * Delete all.
   *
   * @throws QueryException the query exception
   */
  void deleteAll () throws QueryException {
    PreparePredicate<T> preparePredicate = null;
    this.delete(preparePredicate);
  }
  
  void delete(PreparePredicate<T> preparePredicate) throws QueryException {
    try {
      EntityTransaction entityTransaction = entityManager.getTransaction();
      entityTransaction.begin();
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      CriteriaDelete<T> criteriaDelete = criteriaBuilder
          .createCriteriaDelete(tclass);
      Root<T> root = criteriaDelete.from(tclass);
      
      if (preparePredicate != null){
        Predicate predicate = preparePredicate
            .getPredicate(root, criteriaBuilder);
        criteriaDelete.where(predicate);
      }

     
      Query query = entityManager.createQuery(criteriaDelete);
      query.executeUpdate();
      entityTransaction.commit();
    } catch (Exception e) {
      throw new QueryException(e.getMessage(), e);
    }
  }
}
