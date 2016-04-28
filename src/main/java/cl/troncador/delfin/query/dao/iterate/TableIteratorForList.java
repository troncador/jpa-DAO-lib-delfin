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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.query.constraint.PrepareLimit;
import cl.troncador.delfin.query.constraint.PrepareLimitPagination;
import cl.troncador.delfin.query.constraint.PreparePredicate;
import cl.troncador.delfin.query.constraint.PrepareQuery;
import cl.troncador.delfin.query.constraint.PrepareQueryAdapter;
import cl.troncador.delfin.query.dao.EntityDao;

// TODO: Auto-generated Javadoc
/**
 * The Class TableIteratorForList.
 *
 * @param <T> the generic type
 */
public class TableIteratorForList<T> implements Iterator<List<T>> {
	
	/** The log. */
	static Logger log = LoggerFactory.getLogger(TableIteratorForList.class);
	
	/** The page. */
	private int page = 0;

	/** The page size. */
	private int pageSize;
	
	/** The entity dao. */
	private EntityDao<T, ?> entityDao;
	
	/** The prepare predicate. */
	private PreparePredicate<T> preparePredicate;

	
	/**
	 * Instantiates a new table iterator for list.
	 *
	 * @param entityDao the entity dao
	 * @param preparePredicate the prepare predicate
	 * @param pageSize the page size
	 * @throws QueryException the query exception
	 */
	public TableIteratorForList(
			EntityDao<T, ?> entityDao,
			PreparePredicate<T> preparePredicate,
			int pageSize
			) throws QueryException{
		
		this.entityDao = entityDao;
		this.pageSize = pageSize;
		this.preparePredicate = preparePredicate;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext(){
		try {
      return entityDao.count(preparePredicate) != 0;
    } catch (QueryException e) {
       throw new RuntimeException("Unexpected Exception");
    }
	}
	
	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public List<T> next() {
    PrepareLimit prepareLimit = new PrepareLimitPagination(pageSize, page);
    PrepareQuery<T> prepareQuery = 
        new PrepareQueryAdapter<T>(preparePredicate, prepareLimit);
    try {
      return entityDao.select(prepareQuery);
    } catch (QueryException e) {
      throw new RuntimeException("Unexpected Exception");
    }
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException();
		
	}
}
