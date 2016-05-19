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
package cl.troncador.delfin.query.dao.iterate;

import java.util.Iterator;

import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.query.constraint.PreparePredicate;
import cl.troncador.delfin.query.constraint.PrepareQueryAdapter;
import cl.troncador.delfin.query.dao.EntityDao;

// TODO: Auto-generated Javadoc
/**
 * The Class TableIterable.
 *
 * @param <T> the generic type
 */
public class TableIterable<T> implements Iterable<T>{

	/**
	 * The Enum Mode.
	 */
	public static enum Mode {/** The for. */
FOR,/** The while. */
WHILE}
	
	/** The page size. */
	private int pageSize;
	
	/** The entity dao. */
	private EntityDao<T, ?> entityDao;
	
	/** The prepare predicate. */
	private PreparePredicate<T> preparePredicate;
	
	/** The mode. */
	private Mode mode;

	
	/**
	 * Instantiates a new table iterable.
	 *
	 * @param entityDao the entity dao
	 * @param pageSize the page size
	 * @param mode the mode
	 */
	public TableIterable(
			EntityDao<T, ?> entityDao,
			int pageSize,
			Mode mode
			){
		this(entityDao,new PrepareQueryAdapter<T>(),pageSize,mode);
	}
	
	

	/**
	 * Instantiates a new table iterable.
	 *
	 * @param entityDao the entity dao
	 * @param preparePredicate the prepare predicate
	 * @param pageSize the page size
	 * @param mode the mode
	 */
	public TableIterable(
			EntityDao<T, ?> entityDao,
			PreparePredicate<T> preparePredicate,
			int pageSize,
			Mode mode){
		
		this.pageSize = pageSize;
		this.entityDao = entityDao;
		this.preparePredicate = preparePredicate;
		this.mode = mode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<T> iterator() {
		try {
			switch (mode) {
			case FOR:
				return new TableIteratorFor<T>(entityDao, preparePredicate, pageSize);
			case WHILE:
				return new TableIteratorWhile<T>(entityDao, preparePredicate, pageSize);
			default:
				return new TableIteratorFor<T>(entityDao, preparePredicate, pageSize);
			}
		
		} catch (QueryException e) {
			throw new TableIterationException(e);
		}
		
	}
	
	
}
