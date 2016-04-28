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

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.query.constraint.PreparePredicate;
import cl.troncador.delfin.query.constraint.PrepareQuery;

// TODO: Auto-generated Javadoc
/**
 * The Class CountMethod.
 *
 * @param <T> the generic type
 */
class CountMethod<T> {
  
  /** The Constant log. */
  private static final Logger log = LoggerFactory.getLogger(CountMethod.class);
  
  /** The tclass. */
  private Class< T > tclass ;
  
  /** The entity manager. */
  private EntityManager entityManager;
  
  /**
   * Instantiates a new count method.
   *
   * @param tclass the tclass
   * @param entityManager the entity manager
   */
  CountMethod(Class<T> tclass, EntityManager entityManager){
    this.tclass = tclass;
    this.entityManager = entityManager;
  }
  
  /**
   * Count.
   *
   * @return the long
   * @throws QueryException the query exception
   */
  long count() throws QueryException {
    PrepareQuery<T> prepareQuery=null;
    return count(prepareQuery);
  }
  
  /**
   * Count.
   *
   * @param prepareQuery the prepare query
   * @return the long
   * @throws QueryException the query exception
   */
  long count(PreparePredicate<T> prepareQuery) throws QueryException {
    try {
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tclass);
      Root<T> root = criteriaQuery.from(tclass);
      Expression<Long> expression = criteriaBuilder.count(root);
      CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
      cq.select( expression);
      if(prepareQuery!=null){
        Predicate predicate = prepareQuery.getPredicate(root, criteriaBuilder);
        if(predicate!=null){
          cq.where(predicate);
        }
      }
      TypedQuery<Long> typedQuery = entityManager.createQuery(cq);
      return typedQuery.getSingleResult();
    }catch (Exception e) {
      throw new QueryException(e.getMessage(), e);
    }
  }
}
