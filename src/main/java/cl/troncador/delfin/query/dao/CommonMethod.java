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
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.troncador.delfin.query.constraint.PrepareLimit;
import cl.troncador.delfin.query.constraint.PreparePredicate;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonMethod.
 *
 * @param <T> the generic type
 * @param <PK> the generic type
 */
class CommonMethod<T,PK> {
  
  /** The Constant log. */
  private static final Logger log = LoggerFactory.getLogger(CommonMethod.class);
  
  /** The pk. */
  private SingularAttribute<? super T, PK> PK;
  
  /**
   * Instantiates a new common method.
   *
   * @param PK the pk
   */
  CommonMethod(SingularAttribute<? super T, PK> PK){
    this.PK = PK;
  }
  
  /**
   * Sets the limits.
   *
   * @param <O> the generic type
   * @param limit the limit
   * @param query the query
   */
  <O> void setLimits(PrepareLimit limit,Query query){
    Integer maxResults = limit.getMaxResults();
    if(maxResults != null){
      query.setMaxResults(limit.getMaxResults());
    }
    Integer firstResults = limit.getFirstResult();
    if(firstResults != null){
      query.setFirstResult(firstResults);
    }
  }
  
  /**
   * Gets the prepare predicate by pk.
   *
   * @param id the id
   * @return the prepare predicate by pk
   */
  PreparePredicate<T> getPreparePredicateByPK(final PK id){
    return  new PreparePredicate<T>(){
      public Predicate getPredicate(Root<T> root, CriteriaBuilder criteriaBuilder){
        if(PK == null){
          throw new RuntimeException("getPK == null");
        }
        Path<PK> path = root.get(PK);
        return criteriaBuilder.equal(path,id);
      }
    };
  }

  /**
   * Gets the prepare predicate by field.
   *
   * @param <O> the generic type
   * @param value the value
   * @param field the field
   * @return the prepare predicate by field
   */
  <O> PreparePredicate<T> getPreparePredicateByField(final O value,
      final SingularAttribute<T,O> field){
    return  new PreparePredicate<T>(){
      public Predicate getPredicate(Root<T> root, 
          CriteriaBuilder criteriaBuilder){
        Path<O> path = root.get(field);
        return criteriaBuilder.equal(path,value);
      }
    };
  }
  
  /**
   * Gets the prepare predicate by field.
   *
   * @param <O> the generic type
   * @param prepareQuery the prepare query
   * @param value the value
   * @param field the field
   * @return the prepare predicate by field
   */
  <O> PreparePredicate<T> getPreparePredicateByField(
      final PreparePredicate<T> prepareQuery, final O value,
      final SingularAttribute<T,O> field){
    return  new PreparePredicate<T>(){
      public Predicate getPredicate(Root<T> root, CriteriaBuilder criteriaBuilder){
        Predicate predicate = prepareQuery.getPredicate(root, criteriaBuilder);
        Path<O> path = root.get(field);
        return criteriaBuilder.and(predicate, criteriaBuilder.equal(path,value));
      }
    };
  }
}
