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
package cl.troncador.delfin.db.table;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;

import cl.troncador.delfin.model.table.BaseTable;
import cl.troncador.delfin.model.table.SimpleTable;


/**
 * The persistent class for the office database table.
 * 
 */
@Entity
@Table(name="office")
@Cacheable(value=false)
public class Office extends SimpleTable implements BaseTable<Integer>, Comparable<Office>{
	private static final long serialVersionUID = 1L;

	@Id
	public Integer getId() {
		return this.id;
	}

  @Column(name="attendant_mail")
  public String getAttendantMail() {
    return attendantMail;
  }
  
  //bi-directional many-to-one association to OfficeCity
  @ManyToOne
  @JoinColumn(name="city_id")
  public OfficeCity getOfficeCity() {
    return this.officeCity;
  }

  @Column(name="attendant_name")
  public String getAttendantName() {
    return attendantName;
  }
  
	private String name;


	private String attendantName;
	

	private String attendantMail;
	


	private OfficeCity officeCity;

	public Office() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public void setOfficeCity(OfficeCity officeCity) {
		this.officeCity = officeCity;
	}


	public void setAttendantName(String attendantName) {
		this.attendantName = attendantName;
	}

	public void setAttendantMail(String attendantMail) {
		this.attendantMail = attendantMail;
	}

	public boolean equals(Object object){
	  if(object instanceof Office) {
	    Office office2 = (Office) object;
	    boolean b= ObjectUtils.notEqual(getId(), office2.getId()) ||
	           ObjectUtils.notEqual(getName(), office2.getName()) ||
	           ObjectUtils.notEqual(getAttendantName(),office2.getAttendantName()) ||
	           ObjectUtils.notEqual(getAttendantMail(),office2.getAttendantMail());
	    return !b;
	  } else {
	    return false;
	  }
	}
	
	public int hashCode() {
    int hash = 1;
    hash = hash * 17 + id;
    hash = hash * 31 + name.hashCode();
    hash = hash * 13 + attendantName.hashCode();
    hash = hash * 41 + attendantMail.hashCode();
    return hash;
	}
	
	public String toString(){
	  return String.format("{name:%s,attendantName:%s,attendantMail:%s}", 
	      this.name, this.attendantName, this.attendantMail);
	}

  @Override
  public int compareTo(Office o) {
    if(getId() == o.getId()) {  
      return 0;  
    } else if(getId() > o.getId()) { 
      return 1;  
    } else  {
      return -1;
    }
  }
	
}