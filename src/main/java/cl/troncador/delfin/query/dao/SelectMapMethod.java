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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.query.constraint.PrepareQuery;
import cl.troncador.delfin.query.constraint.PrepareQueryAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class SelectMapMethod.
 *
 * @param <T> the generic type
 * @param <PK> the generic type
 */
class SelectMapMethod<T,PK> {
  
  /** The Constant log. */
  private static final Logger log = LoggerFactory.getLogger(SelectMapMethod.class);
  
  /** The tclass. */
  private Class< T > tclass ;
  
  /** The entity manager. */
  private EntityManager entityManager;
  
  /** The pk. */
  private SingularAttribute<? super T, PK> PK;
  
  /**
   * Instantiates a new select map method.
   *
   * @param tclass the tclass
   * @param PK the pk
   * @param entityManager the entity manager
   */
  SelectMapMethod(Class<T> tclass,SingularAttribute<? super T, PK> PK, EntityManager entityManager){
    this.tclass = tclass;
    this.entityManager = entityManager;
    this.PK = PK;
  }
  
  /**
   * Select map.
   *
   * @param <O> the generic type
   * @param column the column
   * @param oclass the oclass
   * @return the map
   * @throws QueryException the query exception
   */
  <O> Map<PK,O> selectMap(SingularAttribute<? super T, O> column,
      Class<O> oclass) throws QueryException{
    return  selectMap(new PrepareQueryAdapter<T>(),column,oclass);
  }
  
  
  /**
   * Select map.
   *
   * @param <O> the generic type
   * @param prepareQuery the prepare query
   * @param column the column
   * @param oclass the oclass
   * @return the map
   * @throws QueryException the query exception
   */
  <O> Map<PK, O> selectMap(PrepareQuery<T> prepareQuery, 
      SingularAttribute<? super T, O> column,Class<O> oclass) 
          throws QueryException{
    try {
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
      Root<T> root = criteriaQuery.from(tclass);
      
      Path<O> columnPath = root.get(column);
      Path<PK> pkPath = root.get(PK);
      
      criteriaQuery.multiselect(pkPath,columnPath);
      
      List<Tuple> tupleList = selectMapCore(prepareQuery,root,criteriaQuery,
          criteriaBuilder,entityManager);
      
      Map<PK,O> map = new HashMap<PK,O>(tupleList.size());
      for (Tuple tuple: tupleList){
        PK pkValue = (PK) tuple.get(pkPath);
        O columnValue = (O) tuple.get(columnPath);
        map.put(pkValue, columnValue);
      }
      return map;
  }  catch (Exception e) {
      throw new QueryException(e.getMessage(), e);
    }
  }
 
  /**
   * Select map core.
   *
   * @param prepareQuery the prepare query
   * @param root the root
   * @param criteriaQuery the criteria query
   * @param criteriaBuilder the criteria builder
   * @param entityManager the entity manager
   * @return the list
   */
  private List<Tuple> selectMapCore(PrepareQuery<T> prepareQuery,Root<T> root,
      CriteriaQuery<Tuple> criteriaQuery,CriteriaBuilder criteriaBuilder,
      EntityManager entityManager){
    if(prepareQuery!=null){
      
      Order[] orderArray = prepareQuery.getOrderArray(root, criteriaBuilder);
      if(orderArray != null){
        criteriaQuery.orderBy(orderArray);
      }
      
      Predicate predicate = prepareQuery.getPredicate(root, criteriaBuilder);
      
      if(predicate != null){
        criteriaQuery.where(predicate);
      }
      
      TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
      new CommonMethod<T,PK>(PK).setLimits(prepareQuery, query);
      return query.getResultList();
    }else {
      TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
      return query.getResultList();
    }
  }
}
