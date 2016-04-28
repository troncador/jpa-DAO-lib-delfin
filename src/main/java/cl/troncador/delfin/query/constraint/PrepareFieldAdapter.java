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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class PrepareFieldAdapter.
 *
 * @param <T> the generic type
 */
public class PrepareFieldAdapter<T> implements PrepareField<T> {
	
	/** The log. */
	static Logger log = LoggerFactory.getLogger(PrepareFieldAdapter.class);
	
	/** The fiel set. */
	public Set<SingularAttribute<? super T,?>> fielSet = new HashSet<SingularAttribute<? super T,?>>();
	
	/** The fiel string set. */
	public Set<String> fielStringSet = new HashSet<String>();
	
	/**
	 * Instantiates a new prepare field adapter.
	 */
	public PrepareFieldAdapter(){
		
	}
	
	/**
	 * Instantiates a new prepare field adapter.
	 *
	 * @param fielSet the fiel set
	 */
	public PrepareFieldAdapter(Set<SingularAttribute<? super T,?>> fielSet){
		this.fielSet = fielSet;
	}
	
	/**
	 * Adds the.
	 *
	 * @param field the field
	 */
	public void add(SingularAttribute<? super T,?> field){
		fielSet.add(field);
	}
	
	/**
	 * Adds the.
	 *
	 * @param field the field
	 */
	public void add(String field){
		fielStringSet.add(field);
	}
	
	/* (non-Javadoc)
	 * @see cl.troncador.delfin.query.constraint.PrepareField#getSelection(javax.persistence.criteria.Root)
	 */
	public Selection<?>[] getSelection(Root<T> root) {
		Set<Selection<?>> set = new HashSet<Selection<?>>();
		
		for (SingularAttribute<? super T,?> attribute : fielSet){
			if(attribute == null){
				log.info("attr == null");
			}
			Path<?> path = root.get(attribute);
			set.add(path);
		}
		for (String attribute : fielStringSet){
			Path<?> path = root.get(attribute);
			set.add(path);
		}
		return set.toArray(new Selection<?>[set.size()]);
	}
}
