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
package cl.troncador.delfin;


@SuppressWarnings("serial")
public class QueryException extends Exception{
	
	private QueryExceptionKind exceptionKind = QueryExceptionKind.UNDEFINED; 
	
	public QueryException(){
	}
	
	public QueryException(QueryExceptionKind exceptionKind){
		this.exceptionKind = exceptionKind;
	}
	
	public QueryException(QueryExceptionKind exceptionKind, String msg){
		super(msg);
		this.exceptionKind = exceptionKind;
	}
	
	public QueryException(QueryExceptionKind exceptionKind, Throwable e){
		super(e);
		this.exceptionKind = exceptionKind;
	}
	
	public QueryException(String msg){
		super(msg);
	}
	
	public QueryException(Throwable e){
		super(e);
	}
	
	public QueryException(String msg,Throwable e){
		super(msg, e);
	}
	
	public QueryExceptionKind getExceptionKind(){
		return this.exceptionKind;
	}

}
