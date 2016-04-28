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
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.SingularAttribute;

import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.query.constraint.PreparePredicate;
import cl.troncador.delfin.query.constraint.PrepareQuery;


// TODO: Auto-generated Javadoc
/**
 * The Interface EntityDao.
 *
 * @param <T> the generic type
 * @param <PK> the generic type
 */
public interface EntityDao<T, PK>{
	/*
	 * TODO:
	 *	List<T> findParcial(List<SingularAttribute<? super T, ?>> columnList, Class<O> oclass) throws QueryException;
	 */
	
	/**
	 * Select one.
	 *
	 * @param id the id
	 * @return the t
	 * @throws QueryException the query exception
	 */
	/*
	 * SELECTONE
	 */
	T selectOne(PK id) throws QueryException;
	
	/**
	 * Select one.
	 *
	 * @param prepareQuery the prepare query
	 * @return the t
	 * @throws QueryException the query exception
	 */
	T selectOne(PreparePredicate<T> prepareQuery) throws QueryException;
	
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
	<O> O selectOneColumn(PreparePredicate<T> preparePredicate, SingularAttribute<? super T, O> column,Class<O> oclass) throws QueryException;
	
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
	<O> O selectOneColumn(PK id, SingularAttribute<? super T, O> column,Class<O> oclass) throws QueryException;
	
	/**
	 * Exist.
	 *
	 * @param id the id
	 * @return true, if successful
	 * @throws QueryException the query exception
	 */
	/*
	 * EXIST
	 */
	boolean exist(PK id) throws QueryException;
	
	/**
	 * Exist.
	 *
	 * @param prepareQuery the prepare query
	 * @return true, if successful
	 * @throws QueryException the query exception
	 */
	boolean exist(PreparePredicate<T> prepareQuery) throws QueryException;
	
	/**
	 * Exist.
	 *
	 * @param <P> the generic type
	 * @param value the value
	 * @param field the field
	 * @return true, if successful
	 * @throws QueryException the query exception
	 */
	<P> boolean exist(final P value,final SingularAttribute<T,P> field) throws QueryException;

	/**
	 * Select.
	 *
	 * @return the list
	 * @throws QueryException the query exception
	 */
	/*
	 * SELECT
	 */
	List<T> select() throws QueryException;
	
	/**
	 * Select.
	 *
	 * @param prepareQuery the prepare query
	 * @return the list
	 * @throws QueryException the query exception
	 */
	List<T> select(PrepareQuery<T> prepareQuery) throws QueryException;
	
	/**
	 * Select pk.
	 *
	 * @param classPK the class pk
	 * @return the list
	 * @throws QueryException the query exception
	 */
	List<PK> selectPK(Class<PK> classPK) throws QueryException;
	
	/**
	 * Select pk.
	 *
	 * @param prepareQuery the prepare query
	 * @param classPK the class pk
	 * @return the list
	 * @throws QueryException the query exception
	 */
	List<PK> selectPK(PrepareQuery<T> prepareQuery,Class<PK> classPK) throws QueryException;
	
	/**
	 * Select column.
	 *
	 * @param <O> the generic type
	 * @param column the column
	 * @param oclass the oclass
	 * @return the list
	 * @throws QueryException the query exception
	 */
	/*
	 * SELECTCOLUMN
	 */
	<O> List<O> selectColumn(SingularAttribute<? super T, O> column, Class<O> oclass) throws QueryException;
	
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
	<O> List<O> selectColumn(PrepareQuery<T> prepareQuery, SingularAttribute<? super T, O> column,Class<O> oclass) throws QueryException;
	
	/**
	 * Select map.
	 *
	 * @param <O> the generic type
	 * @param column the column
	 * @param oclass the oclass
	 * @return the map
	 * @throws QueryException the query exception
	 */
	/*
	 * SELECTMAP
	 */
	<O> Map<PK, O> selectMap(SingularAttribute<? super T, O> column, Class<O> oclass) throws QueryException;
	
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
	<O> Map<PK, O> selectMap(PrepareQuery<T> prepareQuery, SingularAttribute<? super T, O> column, Class<O> oclass) throws QueryException;

	/**
	 * Count.
	 *
	 * @return the long
	 * @throws QueryException the query exception
	 */
	/*
	 * COUNT
	 */
	long count() throws QueryException;
	
	/**
	 * Count.
	 *
	 * @param preparePredicate the prepare predicate
	 * @return the long
	 * @throws QueryException the query exception
	 */
	long count(PreparePredicate<T> preparePredicate) throws QueryException;

	/**
	 * Update.
	 *
	 * @param tList the t list
	 * @return the list
	 * @throws QueryException the query exception
	 */
	/*
	 * UPDATE
	 */
	List<T> update(List<T> tList)	throws QueryException;
	
	/**
	 * Update.
	 *
	 * @param e the e
	 * @return the t
	 * @throws QueryException the query exception
	 */
	T update(T e) 			throws QueryException;
	
	/**
	 * Save.
	 *
	 * @param e the e
	 * @return the t
	 * @throws QueryException the query exception
	 */
	/*
	 * SAVE
	 */
	T save(T e) 			throws QueryException;
	
	/**
	 * Save.
	 *
	 * @param e the e
	 * @return the list
	 * @throws QueryException the query exception
	 */
	List<T> save(List<T> e) 	throws QueryException;
	
	/**
	 * Delete.
	 *
	 * @param e the e
	 * @throws QueryException the query exception
	 */
	/*
	 * DELETE
	 */
	void delete(T e) 		throws QueryException;
	
	/**
	 * Delete by pk.
	 *
	 * @param e the e
	 * @throws QueryException the query exception
	 */
	void deleteByPK(PK e) 	throws QueryException;
	
	/**
	 * Delete all.
	 *
	 * @throws QueryException the query exception
	 */
	void deleteAll() 		throws QueryException;
	
	/**
	 * Delete.
	 *
	 * @param entityList the entity list
	 * @throws QueryException the query exception
	 */
	void delete(List<T> entityList) throws QueryException;
  /**
   * Delete.
   *
   * @param e the e
   * @throws QueryException the query exception
   */
  /*
   * DELETE
   */
  void delete(PreparePredicate<T> preparePredicate)    throws QueryException;
  
}
