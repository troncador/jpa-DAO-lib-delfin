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

public enum QueryExceptionKind {

	//cuando se tiene que una excepcion que no se sabe que la causa
	//o no hay una regla especifica para manejarlo
	UNDEFINED,
	//Cuando se espera un unico resultado y es más de uno
	NON_UNIQUE_RESULT,
	//cuando en el DB se define que el campo debe ser único
	NON_UNIQUE_FIELD, 
	//Cuando no se obtiene un resultado
	NO_RESULT,
	//En caso de que exista la entidad
	ALREADY_EXISTS,
	//Un error critico
	FATAL_ERROR
	
}
