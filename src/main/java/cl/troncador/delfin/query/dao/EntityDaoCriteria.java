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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.troncador.delfin.Existance;
import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.model.table.BaseTable;
import cl.troncador.delfin.query.constraint.PreparePredicate;
import cl.troncador.delfin.query.constraint.PrepareQuery;


// TODO: Auto-generated Javadoc
/**
 * The Class EntityDaoCriteria.
 *
 * @param <T> the generic type
 * @param <PK> the generic type
 */
public abstract class EntityDaoCriteria <T extends BaseTable<PK>,PK extends Serializable> 
    implements EntityDao<T, PK>,  Existance<T,PK> {

  /** The Constant log. */
  private static final Logger log = LoggerFactory.getLogger(EntityDaoCriteria.class);
	
	/** The tclass. */
	protected Class< T > tclass ;
  
  /** The pk. */
  private SingularAttribute<? super T, PK> pk;
	
	/* (non-Javadoc)
	 * @see cl.troncador.delfin.server.EntityManagerInserter#getEntityManager()
	 */
	//[start] OTHER
	abstract public EntityManager getEntityManager();
	
	/**
	 * Instantiates a new entity dao criteria.
	 *
	 * @param tclass the tclass
	 * @param pk the pk
	 * @throws ClassCastException the class cast exception
	 */
	public EntityDaoCriteria(Class<T> tclass, SingularAttribute<? super T, PK> pk) throws ClassCastException{
		this.tclass = tclass;
		this.pk = pk;
	}

  /* (non-Javadoc)
   * @see cl.troncador.delfin.Existance#get(java.lang.Object)
   */
  @Override
  public T get(PK pk) throws Exception {
    return selectOne(pk);
  }
	
	/* (non-Javadoc)
	 * @see cl.troncador.delfin.query.dao.EntityDao#selectOne(java.lang.Object)
	 */
	/*
	 * SELECTONE
	 */
	public T selectOne(PK id) throws QueryException{
	  return new SelectOneMethod<T,PK>(tclass, pk, getEntityManager()).selectOne(id);
  }
	
	/* (non-Javadoc)
	 * @see cl.troncador.delfin.query.dao.EntityDao#selectOne(cl.troncador.delfin.query.constraint.PreparePredicate)
	 */
	public  T selectOne(PreparePredicate<T> prepareQuery) throws QueryException {
	  return new SelectOneMethod<T,PK>(tclass, pk, getEntityManager())
	      .selectOne(prepareQuery);
  }
	
	/* (non-Javadoc)
	 * @see cl.troncador.delfin.query.dao.EntityDao#selectOneColumn(cl.troncador.delfin.query.constraint.PreparePredicate, javax.persistence.metamodel.SingularAttribute, java.lang.Class)
	 */
	public <O> O selectOneColumn(PreparePredicate<T> preparePredicate, 
	    SingularAttribute<? super T, O> column, Class<O> oclass) 
	        throws QueryException {
	   return new SelectOneMethod<T,PK>(tclass, pk, getEntityManager())
	        .selectOneColumn(preparePredicate, column, oclass);
	}
	
	/* (non-Javadoc)
	 * @see cl.troncador.delfin.query.dao.EntityDao#selectOneColumn(java.lang.Object, javax.persistence.metamodel.SingularAttribute, java.lang.Class)
	 */
	public <O> O selectOneColumn(PK id, SingularAttribute<? super T, O> column,
	    Class<O> oclass) throws QueryException {
    return new SelectOneMethod<T,PK>(tclass, pk, getEntityManager())
        .selectOneColumn(id, column, oclass);
	}

  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#exist(java.lang.Object)
   */
  /*
   * EXIST
   */
	public boolean exist(PK id) throws QueryException {
	  return new ExistMethod<T, PK>(tclass, pk, getEntityManager()).exist(id);
	}
	
	/* (non-Javadoc)
	 * @see cl.troncador.delfin.query.dao.EntityDao#exist(cl.troncador.delfin.query.constraint.PreparePredicate)
	 */
	public  boolean exist(PreparePredicate<T> prepareQuery) throws QueryException {
	  return new ExistMethod<T, PK>(tclass, pk, getEntityManager()).exist(prepareQuery);
	}
	
	/* (non-Javadoc)
	 * @see cl.troncador.delfin.query.dao.EntityDao#exist(java.lang.Object, javax.persistence.metamodel.SingularAttribute)
	 */
	public  <P> boolean exist(final P value,final SingularAttribute<T,P> field) throws QueryException {
	  return new ExistMethod<T, PK>(tclass, pk, getEntityManager()).exist(value, field);
	}
	
	 /* (non-Javadoc)
 	 * @see cl.troncador.delfin.query.dao.EntityDao#select()
 	 */
 	/*
   * SELECT
   */
  public List<T> select() throws QueryException {
    return new SelectMethod<T,PK>(tclass, pk, getEntityManager()).select();
  }
  
  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#select(cl.troncador.delfin.query.constraint.PrepareQuery)
   */
  public List<T> select(PrepareQuery<T> prepareQuery) throws QueryException {
    return new SelectMethod<T,PK>(tclass, pk, getEntityManager()).select(prepareQuery);  
  }
  
  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#selectPK(java.lang.Class)
   */
  public List<PK> selectPK(Class<PK> classPK) throws QueryException {
    return new SelectMethod<T,PK>(tclass, pk, getEntityManager()).selectPK(classPK);  
  }
  
  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#selectPK(cl.troncador.delfin.query.constraint.PrepareQuery, java.lang.Class)
   */
  public List<PK> selectPK(PrepareQuery<T> prepareQuery,Class<PK> classPK) throws QueryException{
    return new SelectMethod<T,PK>(tclass, pk, getEntityManager()).selectPK(prepareQuery, classPK);  
  }
  
  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#selectColumn(javax.persistence.metamodel.SingularAttribute, java.lang.Class)
   */
  /*
   * SELECTCOLUMN
   */
  public <O> List<O> selectColumn(SingularAttribute<? super T, O> column, Class<O> oclass) throws QueryException {
    return new SelectMethod<T,PK>(tclass, pk, getEntityManager()).selectColumn(column, oclass);
  }
  
  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#selectColumn(cl.troncador.delfin.query.constraint.PrepareQuery, javax.persistence.metamodel.SingularAttribute, java.lang.Class)
   */
  public <O> List<O> selectColumn(PrepareQuery<T> prepareQuery, SingularAttribute<? super T, O> column,Class<O> oclass) throws QueryException {
    return new SelectMethod<T,PK>(tclass, pk, getEntityManager()).selectColumn(prepareQuery, column, oclass);
  }
  
  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#selectMap(javax.persistence.metamodel.SingularAttribute, java.lang.Class)
   */
  /*
  * SELECTMAP
  */
  public <O> Map<PK, O> selectMap(SingularAttribute<? super T, O> column, Class<O> oclass) throws QueryException {
    return new SelectMapMethod<T,PK>(tclass, pk, getEntityManager()).selectMap(column, oclass);
  }
  
  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#selectMap(cl.troncador.delfin.query.constraint.PrepareQuery, javax.persistence.metamodel.SingularAttribute, java.lang.Class)
   */
  public <O> Map<PK, O> selectMap(PrepareQuery<T> prepareQuery, SingularAttribute<? super T, O> column, Class<O> oclass) throws QueryException {
    return new SelectMapMethod<T,PK>(tclass, pk, getEntityManager()).selectMap(prepareQuery, column, oclass);
  }

	 /* (non-Javadoc)
 	 * @see cl.troncador.delfin.query.dao.EntityDao#count()
 	 */
 	/*
   * COUNT
   */
	@Override
  public long count() throws QueryException{
    return new CountMethod<T>(tclass, getEntityManager()).count();
  }
  
  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#count(cl.troncador.delfin.query.constraint.PreparePredicate)
   */
  @Override
  public long count(PreparePredicate<T> preparePredicate) throws QueryException {
    return new CountMethod<T>(tclass, getEntityManager()).count(preparePredicate);
  }

  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#update(java.util.List)
   */
  /*
   * UPDATE
   */
  @Override
  public List<T> update(List<T> tList) throws QueryException { 
    return new UpdateMethod<T>(tclass, getEntityManager()).update(tList);
  }
  
  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#update(java.lang.Object)
   */
  @Override
  public T update(T t) throws QueryException { 
    return new UpdateMethod<T>(tclass, getEntityManager()).update(t);
  }

  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#save(java.lang.Object)
   */
  /*
   * SAVE
   */
  @Override
  public T save(T t) throws QueryException { 
    return new SaveMethod<T>(tclass, getEntityManager()).save(t);
  }
  
  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#save(java.util.List)
   */
  @Override
  public List<T> save(List<T> list) throws QueryException { 
    return new SaveMethod<T>(tclass, getEntityManager()).save(list);
  }

  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#delete(java.lang.Object)
   */
  /*
   * delete
   */
  @Override
  public void delete(T e)    throws QueryException {
    new DeleteMethod<T,PK>(tclass, pk, getEntityManager()).delete(e);
  }
  
  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#deleteByPK(java.lang.Object)
   */
  @Override
  public void deleteByPK(PK e)   throws QueryException { 
    new DeleteMethod<T,PK>(tclass, pk, getEntityManager()).deleteByPK(e);
  }
  
  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#deleteAll()
   */
  @Override
  public void deleteAll()    throws QueryException {
    new DeleteMethod<T,PK>(tclass, pk, getEntityManager()).deleteAll();
  }
  
  /* (non-Javadoc)
   * @see cl.troncador.delfin.query.dao.EntityDao#delete(java.util.List)
   */
  @Override
  public void delete(List<T> entityList) throws QueryException{ 
    new DeleteMethod<T,PK>(tclass, pk, getEntityManager()).delete(entityList);
  }
  
  public void delete(PreparePredicate<T> preparePredicate) throws QueryException {
    new DeleteMethod<T,PK>(tclass, pk, getEntityManager()).delete(preparePredicate);
  }
//	
//	public <O> List<Tuple> findTuple(SingularAttribute<? super T, O> column,Class<O> oclass) throws QueryException {
//		return findTuple(new PrepareQueryAdapter<T>(),column, oclass);
//	}
//	
//	
//	public <O> List<Tuple> findTuple(PrepareQuery<T> prepareQuery, SingularAttribute<? super T, O> column,Class<O> oclass) throws QueryException {
//		EntityManager entityManager = getEntityManager();
//		
//		try {
//			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//			CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
//			Root<T> root = criteriaQuery.from(tclass);
//			
//			Path<O> columnPath = root.get(column);
//			SingularAttribute<? super T, PK> pk = getPK();
//			Path<PK> pkPath = root.get(pk);
//			
//			criteriaQuery.multiselect(pkPath,columnPath);
//			
//			return findCoreTuple(prepareQuery,root,criteriaQuery,criteriaBuilder,entityManager);
//	}  catch (Exception e) {
//			throw new QueryException(e.getMessage(), e);
//		}
//	}
	
}