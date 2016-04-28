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
package cl.troncador.delfin.query.constraint;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

// TODO: Auto-generated Javadoc
/**
 * The Class PrepareQueryAdapter.
 *
 * @param <T> the generic type
 */
public class PrepareQueryAdapter<T> implements PrepareQuery<T> {
	
	/** The prepare predicate. */
	private PreparePredicate<T> preparePredicate;
	
	/** The prepare limit. */
	private PrepareLimit prepareLimit;
	
	/** The prepare order. */
	private PrepareOrder<T> prepareOrder;
	
	/** The prepare field. */
	private PrepareField<T> prepareField;
	
	/**
	 * Instantiates a new prepare query adapter.
	 */
	public PrepareQueryAdapter(){
	}
	
	/**
	 * Instantiates a new prepare query adapter.
	 *
	 * @param limit the limit
	 */
	public PrepareQueryAdapter(PrepareLimit limit){
		this.prepareLimit = limit;
	}
	
	/**
	 * Instantiates a new prepare query adapter.
	 *
	 * @param predicate the predicate
	 */
	public PrepareQueryAdapter(PreparePredicate<T> predicate){
		this.preparePredicate = predicate;
	}
	
	/**
	 * Instantiates a new prepare query adapter.
	 *
	 * @param prepareField the prepare field
	 */
	public PrepareQueryAdapter(PrepareField<T> prepareField){
		this.prepareField = prepareField;
	}
	
	/**
	 * Instantiates a new prepare query adapter.
	 *
	 * @param order the order
	 */
	public PrepareQueryAdapter(PrepareOrder<T> order){
		this.prepareOrder = order;
	}
	
	/**
	 * Instantiates a new prepare query adapter.
	 *
	 * @param order the order
	 * @param limit the limit
	 */
	public PrepareQueryAdapter(PrepareOrder<T> order, PrepareLimit limit){
		this.prepareLimit = limit;
		this.prepareOrder = order;
	}
	
	/**
	 * Instantiates a new prepare query adapter.
	 *
	 * @param predicate the predicate
	 * @param limit the limit
	 */
	public PrepareQueryAdapter(PreparePredicate<T> predicate, PrepareLimit limit){
		this.prepareLimit = limit;
		this.preparePredicate = predicate;
	}
	
	/**
	 * Instantiates a new prepare query adapter.
	 *
	 * @param predicate the predicate
	 * @param order the order
	 */
	public PrepareQueryAdapter(PreparePredicate<T> predicate, PrepareOrder<T> order){
		this.prepareOrder = order;
		this.preparePredicate = predicate;
	}
	
	/**
	 * Instantiates a new prepare query adapter.
	 *
	 * @param predicate the predicate
	 * @param order the order
	 * @param limit the limit
	 */
	public PrepareQueryAdapter(PreparePredicate<T> predicate, PrepareOrder<T> order,  PrepareLimit limit){
		this.prepareOrder = order;
		this.preparePredicate = predicate;
		this.prepareLimit = limit;
	}
	
	/* (non-Javadoc)
	 * @see cl.troncador.delfin.query.constraint.PreparePredicate#getPredicate(javax.persistence.criteria.Root, javax.persistence.criteria.CriteriaBuilder)
	 */
	public Predicate getPredicate(Root<T> root, CriteriaBuilder criteriaBuilder) {
		if(preparePredicate!=null){
			return preparePredicate.getPredicate(root, criteriaBuilder);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cl.troncador.delfin.query.constraint.PrepareOrder#getOrderArray(javax.persistence.criteria.Root, javax.persistence.criteria.CriteriaBuilder)
	 */
	public Order[] getOrderArray(Root<T> root,
			CriteriaBuilder criteriaBuilder) {
		if(prepareOrder!=null){
			return prepareOrder.getOrderArray(root, criteriaBuilder);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cl.troncador.delfin.query.constraint.PrepareLimit#getMaxResults()
	 */
	public Integer getMaxResults() {
		if(prepareLimit!=null){
			return prepareLimit.getMaxResults();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cl.troncador.delfin.query.constraint.PrepareLimit#getFirstResult()
	 */
	public Integer getFirstResult() {
		if(prepareLimit!=null){
			return prepareLimit.getFirstResult();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cl.troncador.delfin.query.constraint.PrepareField#getSelection(javax.persistence.criteria.Root)
	 */
	public Selection<?>[] getSelection(Root<T> root) {
		if(prepareField!=null){
			return prepareField.getSelection(root);
		}
		return null;
	}

}
