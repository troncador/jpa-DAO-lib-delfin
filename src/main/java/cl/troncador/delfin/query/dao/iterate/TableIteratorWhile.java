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

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.query.constraint.PrepareLimit;
import cl.troncador.delfin.query.constraint.PrepareLimitPagination;
import cl.troncador.delfin.query.constraint.PreparePredicate;
import cl.troncador.delfin.query.constraint.PrepareQuery;
import cl.troncador.delfin.query.constraint.PrepareQueryAdapter;
import cl.troncador.delfin.query.dao.EntityDao;
import cl.troncador.delfin.query.dao.EntityDaoCriteria;

// TODO: Auto-generated Javadoc
/**
 * The Class TableIteratorWhile.
 *
 * @param <T> the generic type
 */
public class TableIteratorWhile<T> implements Iterator<T> {
	
	/** The log. */
	static Logger log = LoggerFactory.getLogger(TableIteratorWhile.class);
	
	/** The cicle. */
	private int cicle = 0;
	
	
	/** The iterator element. */
	private Iterator<T> iteratorElement;
	
	/** The page size. */
	private int pageSize;
	
	/** The entity dao. */
	private EntityDao<T, ?> entityDao;
	
	/** The prepare predicate. */
	private PreparePredicate<T> preparePredicate;
	
	/** The total number page. */
	private long totalNumberPage;
	
	/**
	 * Instantiates a new table iterator while.
	 *
	 * @param entityDao the entity dao
	 * @param preparePredicate the prepare predicate
	 * @param pageSize the page size
	 * @throws QueryException the query exception
	 */
	public TableIteratorWhile(
			EntityDao<T, ?> entityDao,
			PreparePredicate<T> preparePredicate,
			int pageSize
			) throws QueryException{
		long length = entityDao.count(preparePredicate);
		log.info(String.format("length: %d", length));
		this.totalNumberPage = length / pageSize+1;
		
		this.entityDao = entityDao;
		this.pageSize = pageSize;
		this.preparePredicate = preparePredicate;
		
		updateIterator();
	}

	
	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		if(iteratorElement.hasNext()){
			return true;
		}
		updateIterator();
		return iteratorElement.hasNext();
	}
	
	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public T next() {
		if(iteratorElement.hasNext()){
			return iteratorElement.next();
		}
		return iteratorElement.next();
	}

	/** The date. */
	private Date date;
	
	/**
	 * Log record.
	 */
	private void logRecord(){
		if(date!=null){
			Date newDate = new Date();
			log.info(String.format("%04d/%04d  %04d[mS]",cicle,
					totalNumberPage,newDate.getTime()-date.getTime()));
			date = newDate;
		} else {
			date = new Date();
			log.info(String.format(" %d/%d",cicle,totalNumberPage));
		}
	}
	
	
	/**
	 * Update iterator.
	 */
	private void updateIterator() {
		logRecord();
		try {
			PrepareLimit prepareLimit = new PrepareLimitPagination(pageSize, 0);
			PrepareQuery<T> prepareQuery = new PrepareQueryAdapter<T>(preparePredicate,prepareLimit);
			List<T> elementList = entityDao.select(prepareQuery);
			iteratorElement = elementList.iterator();
			cicle++;
		} catch (QueryException e) {
			throw new TableIterationException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException();
		
	}
}
