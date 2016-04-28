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
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.QueryExceptionKind;
import cl.troncador.delfin.query.constraint.PreparePredicate;

// TODO: Auto-generated Javadoc
/**
 * The Class SelectOneMethod.
 *
 * @param <T> the generic type
 * @param <PK> the generic type
 */
class SelectOneMethod<T, PK> {
  
  /** The tclass. */
  private Class<T> tclass;
  
  /** The entity manager. */
  private EntityManager entityManager;
  
  /** The pk. */
  private SingularAttribute<? super T, PK> PK;
  
  /**
   * Instantiates a new select one method.
   *
   * @param tclass the tclass
   * @param PK the pk
   * @param entityManager the entity manager
   */
  SelectOneMethod(Class<T> tclass,SingularAttribute<? super T, PK> PK, EntityManager entityManager) {
    this.tclass = tclass;
    this.entityManager = entityManager;
    this.PK = PK;
  }

  /**
   * Select one.
   *
   * @param id the id
   * @return the t
   * @throws QueryException the query exception
   */
  T selectOne(final PK id) throws QueryException {
    PreparePredicate<T> preparePredicate = new CommonMethod<T,PK>(PK).getPreparePredicateByPK(id);
    return this.selectOne(preparePredicate);
  }

  /**
   * Select one.
   *
   * @param preparePredicate the prepare predicate
   * @return the t
   * @throws QueryException the query exception
   */
  T selectOne(PreparePredicate<T> preparePredicate) throws QueryException {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tclass);
    Root<T> root = criteriaQuery.from(tclass);
    return selectOneCore(preparePredicate, root, criteriaQuery, criteriaBuilder,
        entityManager);
  }

  /**
   * Select one column.
   *
   * @param <O> the generic type
   * @param id the id
   * @param column the column
   * @param oclass the oclass
   * @return the o
   * @throws QueryException the query exception
   */
  <O> O selectOneColumn(final PK id, SingularAttribute<? super T, O> column,
      Class<O> oclass) throws QueryException {
    
    PreparePredicate<T> preparePredicate = new CommonMethod<T,PK>(PK).getPreparePredicateByPK(id);
    return selectOneColumn(preparePredicate, column, oclass);

  }

  /**
   * Select one column.
   *
   * @param <O> the generic type
   * @param preparePredicate the prepare predicate
   * @param column the column
   * @param oclass the oclass
   * @return the o
   * @throws QueryException the query exception
   */
  <O> O selectOneColumn(PreparePredicate<T> preparePredicate,
      SingularAttribute<? super T, O> column, Class<O> oclass)
          throws QueryException {

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<O> criteriaQuery = criteriaBuilder.createQuery(oclass);
    Root<T> root = criteriaQuery.from(tclass);

    Path<O> columnPath = root.get(column);
    criteriaQuery.select(columnPath);
    return selectOneCore(preparePredicate, root, criteriaQuery, criteriaBuilder,
        entityManager);
  }

  /**
   * Select one core.
   *
   * @param <O> the generic type
   * @param prepareQuery the prepare query
   * @param root the root
   * @param criteriaQuery the criteria query
   * @param criteriaBuilder the criteria builder
   * @param entityManager the entity manager
   * @return the o
   * @throws QueryException the query exception
   */
  private <O> O selectOneCore(PreparePredicate<T> prepareQuery,Root<T> root,
      CriteriaQuery<O> criteriaQuery,CriteriaBuilder criteriaBuilder,EntityManager entityManager) 
          throws QueryException{
    try {
      if(entityManager==null){
        throw new QueryException("entityManager==null");
      }
      TypedQuery<O> query;
      if(prepareQuery!=null){
        
        Predicate predicate = prepareQuery.getPredicate(root, criteriaBuilder);
        
        if(predicate != null){
          criteriaQuery.where(predicate);
        }
        query = entityManager.createQuery(criteriaQuery);
      }else {
        query = entityManager.createQuery(criteriaQuery);
        
      }
      if(query==null){
        throw new QueryException("TypedQuery==null");
      }
      return query.getSingleResult();
    } catch (NoResultException e) {
      String msg = String.format("Can't  find in database: %s", tclass.getName());
      throw new QueryException(QueryExceptionKind.NO_RESULT, msg);
    } catch (NonUniqueResultException e) {
      String msg = String.format("More than %s entity in database", tclass.getName());
      throw new QueryException(QueryExceptionKind.NON_UNIQUE_RESULT, msg);
    } catch (Exception e) {
      throw new QueryException(e.getMessage(), e);
    }
  }
  
}
