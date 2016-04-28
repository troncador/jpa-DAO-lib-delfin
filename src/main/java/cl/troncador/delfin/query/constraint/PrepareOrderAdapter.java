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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;


// TODO: Auto-generated Javadoc
/**
 * The Class PrepareOrderAdapter.
 *
 * @param <T> the generic type
 */
public  class PrepareOrderAdapter<T> implements PrepareOrder<T> {
	
	/**
	 * The Enum Direction.
	 */
	public enum Direction{
/** The asc. */
ASC,
/** The des. */
DES}
	
	/** The directioned column list. */
	private List<Pair<SingularAttribute<T, ?>, Direction>> directionedColumnList =
			new ArrayList<Pair<SingularAttribute<T, ?>, Direction>>();
	
	/**
	 * Instantiates a new prepare order adapter.
	 */
	public PrepareOrderAdapter(){}
	
	/**
	 * Instantiates a new prepare order adapter.
	 *
	 * @param column the column
	 */
	public PrepareOrderAdapter(SingularAttribute<T,?> column){
		add(column);
	}
	
	/**
	 * Instantiates a new prepare order adapter.
	 *
	 * @param column the column
	 * @param order the order
	 */
	public PrepareOrderAdapter(SingularAttribute<T,?> column, Direction order){
		add(column,order);
	}
	
	/**
	 * Adds the.
	 *
	 * @param column the column
	 * @param direction the direction
	 */
	public void add(SingularAttribute<T,?> column, Direction direction){
		Pair<SingularAttribute<T, ?>, Direction> pair = 
				new ImmutablePair<SingularAttribute<T, ?>, Direction>(column,direction);
		directionedColumnList.add(pair);
	}
	
	/**
	 * Adds the.
	 *
	 * @param column the column
	 */
	public void add(SingularAttribute<T,?> column){
		Pair<SingularAttribute<T, ?>, Direction> pair = 
				new ImmutablePair<SingularAttribute<T, ?>, Direction>(column,Direction.ASC);
		directionedColumnList.add(pair);
	}
	
	/* (non-Javadoc)
	 * @see cl.troncador.delfin.query.constraint.PrepareOrder#getOrderArray(javax.persistence.criteria.Root, javax.persistence.criteria.CriteriaBuilder)
	 */
	public Order[] getOrderArray(Root<T> root,CriteriaBuilder criteriaBuilder){
		List<Order> orderList = new ArrayList<Order>();
		
		for(Pair<SingularAttribute<T, ?>, Direction> directionedColumn : directionedColumnList){
			Direction direction = directionedColumn.getRight();
			SingularAttribute<T, ?> column = directionedColumn.getLeft();
			Path<?> path = root.get(column);
			Order order = null;
			switch (direction){
				case ASC:
					order = criteriaBuilder.asc(path);
					break;
				case DES:
					order = criteriaBuilder.desc(path);
					break;
			}
			orderList.add(order);
		}
		return orderList.toArray(new Order[orderList.size()]);
	}
}
