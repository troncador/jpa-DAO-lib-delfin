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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.query.constraint.PrepareLimit;
import cl.troncador.delfin.query.constraint.PrepareQuery;
import cl.troncador.delfin.query.constraint.PrepareQueryAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class SelectMethod.
 *
 * @param <T> the generic type
 * @param <PK> the generic type
 */
class SelectMethod<T,PK> {
  
  /** The Constant log. */
  private static final Logger log = LoggerFactory.getLogger(SelectMethod.class);
  
  /** The tclass. */
  private Class<T> tclass;
  
  /** The entity manager. */
  private EntityManager entityManager;
  
  /** The pk. */
  private SingularAttribute<? super T, PK> pk;

  /**
   * Instantiates a new select method.
   *
   * @param tclass the tclass
   * @param pk the pk
   * @param entityManager the entity manager
   */
  SelectMethod(Class<T> tclass, SingularAttribute<? super T, PK> pk, EntityManager entityManager) {
    this.tclass = tclass;
    this.entityManager = entityManager;
    this.pk = pk;
  }

  /**
   * Select.
   *
   * @return the list
   * @throws QueryException the query exception
   */
  List<T> select() throws QueryException {
    PrepareQuery<T> prepareQuery = new PrepareQueryAdapter<T>();
    return select(prepareQuery);
  }

  /**
   * Select.
   *
   * @param prepareQuery the prepare query
   * @return the list
   * @throws QueryException the query exception
   */
  List<T> select(PrepareQuery<T> prepareQuery) throws QueryException {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tclass);
    Root<T> root = criteriaQuery.from(tclass);
    boolean cache;

    Selection<?>[] selectionArray = prepareQuery.getSelection(root);
    if (selectionArray == null) {
      cache = true;
      criteriaQuery.select(root);
    } else {
      cache = false;
      criteriaQuery.multiselect(selectionArray);
    }
    return selectCore(cache, prepareQuery, root, criteriaQuery, criteriaBuilder,
        entityManager);
  }
  /*
   * findColumn
   */

  /**
   * Select column.
   *
   * @param <O> the generic type
   * @param column the column
   * @param oclass the oclass
   * @return the list
   * @throws QueryException the query exception
   */
  <O> List<O> selectColumn(SingularAttribute<? super T, O> column,
      Class<O> oclass) throws QueryException {
    PrepareQuery<T> prepareQuery = new PrepareQueryAdapter<T>();
    return selectColumn(prepareQuery, column, oclass);
  }

  /**
   * Select column.
   *
   * @param <O> the generic type
   * @param prepareQuery the prepare query
   * @param column the column
   * @param oclass the oclass
   * @return the list
   * @throws QueryException the query exception
   */
  <O> List<O> selectColumn(PrepareQuery<T> prepareQuery,
      SingularAttribute<? super T, O> column, Class<O> oclass)
          throws QueryException {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<O> criteriaQuery = criteriaBuilder.createQuery(oclass);
    Root<T> root = criteriaQuery.from(tclass);
    Path<O> columnPath = root.get(column);
    criteriaQuery.select(columnPath);
    return selectCore(false, prepareQuery, root, criteriaQuery, criteriaBuilder,
        entityManager);
  }
  
//IllegalStateException - if called for a Java Persistence query language UPDATE or DELETE statement 
//QueryTimeoutException - if the query execution exceeds the query timeout value set and only the statement is rolled back 
//TransactionRequiredException - if a lock mode has been set and there is no transaction 
//PessimisticLockException - if pessimistic locking fails and the transaction is rolled back 
//LockTimeoutException - if pessimistic locking fails and only the statement is rolled back 
/**
 * Select core.
 *
 * @param <O> the generic type
 * @param maintainCache the maintain cache
 * @param prepareQuery the prepare query
 * @param root the root
 * @param criteriaQuery the criteria query
 * @param criteriaBuilder the criteria builder
 * @param entityManager the entity manager
 * @return the list
 * @throws QueryException the query exception
 */
//PersistenceException - if the query execution exceeds the query timeout value set and the transaction is rolled back
private <O> List<O> selectCore(
    boolean maintainCache,
    PrepareQuery<T> prepareQuery, Root<T> root, CriteriaQuery<O> criteriaQuery, 
    CriteriaBuilder criteriaBuilder, EntityManager entityManager) 
        throws QueryException{
  try {
    if(prepareQuery!=null){
      
      Order[] orderArray = prepareQuery.getOrderArray(root, criteriaBuilder);
      if(orderArray != null){
        criteriaQuery.orderBy(orderArray);
      }
      
      Predicate predicate = prepareQuery.getPredicate(root, criteriaBuilder);
      
      if(predicate != null){
        criteriaQuery.where(predicate);
      }
      
      TypedQuery<O> query = entityManager.createQuery(criteriaQuery);
      if(!maintainCache){
        /**
         * TODO: hacer más estandar y no dependiente de eclipse
         */
        ((org.eclipse.persistence.jpa.JpaQuery)query).getDatabaseQuery()
          .dontMaintainCache();
      }
  
      new CommonMethod<T,PK>(pk).setLimits(prepareQuery, query);
      return query.getResultList();
    }else {
      TypedQuery<O> query = entityManager.createQuery(criteriaQuery);
      return query.getResultList();
    }
  } catch (Exception e) {
    throw new QueryException(e.getMessage(), e);
  }
}


/**
 * Select pk.
 *
 * @param prepareQuery the prepare query
 * @param classPK the class pk
 * @return the list
 * @throws QueryException the query exception
 */
List<PK> selectPK(PrepareQuery<T> prepareQuery,Class<PK> classPK) 
    throws QueryException {
  return selectColumn(prepareQuery, pk, classPK);
}

/**
 * Select pk.
 *
 * @param classPK the class pk
 * @return the list
 * @throws QueryException the query exception
 */
List<PK> selectPK(Class<PK> classPK) throws QueryException {
  return selectColumn( pk, classPK);
}
}
