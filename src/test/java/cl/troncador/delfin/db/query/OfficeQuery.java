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
package cl.troncador.delfin.db.query;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.troncador.delfin.ConvertException;
import cl.troncador.delfin.Existance;
import cl.troncador.delfin.QueryException;
import cl.troncador.delfin.db.table.Office;
import cl.troncador.delfin.db.table.Office_;
import cl.troncador.delfin.query.constraint.PrepareOrder;
import cl.troncador.delfin.query.constraint.PrepareQueryAdapter;
import cl.troncador.delfin.query.dao.EntityDaoCriteria;


public class OfficeQuery extends EntityDaoCriteria<Office, Integer> implements Existance<Office,Integer> {
	static Logger log =LoggerFactory.getLogger(OfficeQuery.class);
  private EntityManager entityManager;
  
	public OfficeQuery(EntityManager entityManager) {
		super(Office.class, Office_.id);
    this.entityManager = entityManager;
	}

	@Override
	public Integer convert(String id) throws ConvertException {
		try{
			return Integer.parseInt(id);
		} catch(NumberFormatException e){
			throw new ConvertException();
		}
	}
	
	public List<Office> findOrdered() throws QueryException{
		PrepareOrder<Office> prepareOrder = new PrepareOrder<Office>() {
			
			public Order[] getOrderArray(Root<Office> root,CriteriaBuilder criteriaBuilder){
				Order name = criteriaBuilder.asc(root.get(Office_.name));
				Order[] orderArray = {name};
				return orderArray;
			}
		};
		return super.select(new PrepareQueryAdapter<Office>(prepareOrder));
	}
	
	public  List<Integer> findPK() throws QueryException {
		return this.selectColumn(
				new PrepareQueryAdapter<Office>(),
				Office_.id,
				Integer.class);
	}
  @Override
  public EntityManager getEntityManager() {
    return this.entityManager;
  }
}
