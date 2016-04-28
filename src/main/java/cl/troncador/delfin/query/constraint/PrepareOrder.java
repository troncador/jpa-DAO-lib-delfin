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
import javax.persistence.criteria.Root;


// TODO: Auto-generated Javadoc
/**
 * The Interface PrepareOrder.
 *
 * @param <T> the generic type
 */
public interface PrepareOrder<T> {
	
	/**
	 * Gets the order array.
	 *
	 * @param root the root
	 * @param criteriaBuilder the criteria builder
	 * @return the order array
	 */
	public Order[] getOrderArray(Root<T> root,CriteriaBuilder criteriaBuilder);

}
