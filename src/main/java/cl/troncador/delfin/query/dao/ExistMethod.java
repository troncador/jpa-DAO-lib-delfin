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
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.criteria.CriteriaBuilder.Case;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.query.constraint.PreparePredicate;

// TODO: Auto-generated Javadoc
/**
 * The Class ExistMethod.
 *
 * @param <T> the generic type
 * @param <PK> the generic type
 */
class ExistMethod<T, PK> {
  
  /** The Constant log. */
  private static final Logger log = LoggerFactory.getLogger(ExistMethod.class);
  
  /** The tclass. */
  private Class< T > tclass ;
  
  /** The entity manager. */
  private EntityManager entityManager;
  
  /** The pk. */
  private SingularAttribute<? super T, PK> PK;
  
  /**
   * Instantiates a new exist method.
   *
   * @param tclass the tclass
   * @param PK the pk
   * @param entityManager the entity manager
   */
  public ExistMethod(Class<T> tclass,SingularAttribute<? super T, PK> PK, EntityManager entityManager){
    this.tclass = tclass;
    this.entityManager = entityManager;
    this.PK = PK;
  }
  /*
   * EXIST
   */
  
  /**
   * Exist.
   *
   * @param id the id
   * @return true, if successful
   * @throws QueryException the query exception
   */
  boolean exist(final PK id) throws QueryException{
    PreparePredicate<T> preparePredicate = new CommonMethod<T,PK>(PK).getPreparePredicateByPK(id);
    return this.exist(preparePredicate);
  }
  
  
  /**
   * Exist.
   *
   * @param <P> the generic type
   * @param value the value
   * @param field the field
   * @return true, if successful
   * @throws QueryException the query exception
   */
  <P> boolean exist(final P value,final SingularAttribute<T,P> field) throws QueryException{
    PreparePredicate<T> preparePredicate = new CommonMethod<T,PK>(PK).getPreparePredicateByField(value,field);
    return this.exist(preparePredicate);
  }

  
  /**
   * Exist.
   *
   * @param prepareQuery the prepare query
   * @return true, if successful
   * @throws QueryException the query exception
   */
  boolean exist(PreparePredicate<T> prepareQuery) throws QueryException{
    try {
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

      criteriaQuery.from(tclass);
      
      Subquery<T> subquery = criteriaQuery.subquery(tclass);
      Root<T> subroot = subquery.from(tclass);
      subquery.select(subroot);
      
      Predicate predicate = prepareQuery.getPredicate(subroot, criteriaBuilder);
      if(predicate != null){
        subquery.where(predicate);
      }
      
      Predicate predicateExists = criteriaBuilder.exists(subquery);
      
      Case<Long> booleancase = criteriaBuilder.<Long>selectCase();
      Expression<Long> booleanExpression = 
          booleancase.when(predicateExists,1l)
                .otherwise(0l);

      criteriaQuery.select(booleanExpression);
      TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery).setMaxResults(1);
      return typedQuery.getSingleResult()!=0;
    } catch (Exception e) {
      //Curiosamente cuando no hay filas en la tabla exist falla
      CountMethod<T> countMethod = new CountMethod<T>(tclass, entityManager);
      if(countMethod.count()==0){
        return false;
      }
      throw new QueryException(e.getMessage(), e);
    }
  }
  
  
}
