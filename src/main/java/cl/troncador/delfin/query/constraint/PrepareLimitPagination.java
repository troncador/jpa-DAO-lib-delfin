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

// TODO: Auto-generated Javadoc
/**
 * The Class PrepareLimitPagination.
 */
public class PrepareLimitPagination implements PrepareLimit{
	
	/** The page size. */
	private int pageSize;
	
	/** The page number. */
	private int pageNumber;
	
	/**
	 * Instantiates a new prepare limit pagination.
	 *
	 * @param pageSize the page size
	 */
	public PrepareLimitPagination(int pageSize){
		this.pageSize = pageSize;
	}
	
	/**
	 * Instantiates a new prepare limit pagination.
	 *
	 * @param pageSize the page size
	 * @param pageNumber the page number
	 */
	public PrepareLimitPagination(Integer pageSize, Integer pageNumber){
		this.pageSize = pageSize;
		this.pageNumber = ((pageNumber==null)?0:pageNumber);
	}
	
	
	/**
	 * Sets the page number.
	 *
	 * @param pageNumber the new page number
	 */
	public void setPageNumber(int pageNumber){
		this.pageNumber = pageNumber;
	}
	
	/* (non-Javadoc)
	 * @see cl.troncador.delfin.query.constraint.PrepareLimit#getMaxResults()
	 */
	public Integer getMaxResults(){
		return pageSize;
	}
	
	/* (non-Javadoc)
	 * @see cl.troncador.delfin.query.constraint.PrepareLimit#getFirstResult()
	 */
	public Integer getFirstResult(){
		return pageSize*pageNumber;
		
	}
}
