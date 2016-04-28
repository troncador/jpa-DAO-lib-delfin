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
package cl.troncador.delfin.model.table;

import javax.persistence.EntityManager;

import cl.troncador.delfin.ConvertException;
import cl.troncador.delfin.query.dao.EntityDaoCriteria;


public class SimpleQuery <T extends SimpleTable>
	extends EntityDaoCriteria<T, Integer> {

	private EntityManager entityManager;

  public SimpleQuery(Class<T> tclass, EntityManager entityManager) {
		super(tclass, SimpleTable_.id);
		this.entityManager = entityManager;
	}

	public Integer convert(String id) throws ConvertException {
		try{
			return Integer.parseInt(id);
		} catch(NumberFormatException e){
			throw new ConvertException();
		}
	}


  @Override
  public EntityManager getEntityManager() {
    return entityManager;
  }
}
