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

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.troncador.delfin.ConvertException;
import cl.troncador.delfin.Existance;
import cl.troncador.delfin.db.table.OfficeCity;
import cl.troncador.delfin.db.table.OfficeCity_;
import cl.troncador.delfin.query.dao.EntityDaoCriteria;


public class OfficeCityQuery extends EntityDaoCriteria<OfficeCity, Integer> 
    implements Existance<OfficeCity,Integer> {
	static Logger log =LoggerFactory.getLogger(OfficeCity.class);
  private EntityManager entityManager;
	
	public OfficeCityQuery(EntityManager entityManager) {
		super(OfficeCity.class, OfficeCity_.id);
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

  @Override
  public EntityManager getEntityManager() {
    return this.entityManager;
  }
}
