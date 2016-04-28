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


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cl.troncador.delfin.model.table.BaseTable;
import cl.troncador.delfin.model.table.SimpleTable;


/**
 * The persistent class for the office_city database table.
 * 
 */
@Entity
@Table(name="office_city")
@Cacheable(value=false)
public class OfficeCity extends SimpleTable implements BaseTable<Integer> , Comparable<OfficeCity>{
	private static final long serialVersionUID = 1L;
	
  @Id
  public Integer getId() {
    return this.id;
  }
  
  @Column(name="attendant_mail")
  public String getAttendantMail() {
    return this.attendantMail;
  }
  
  @Column(name="attendant_name")
  public String getAttendantName() {
    return this.attendantName;
  }
  @OneToMany(mappedBy="officeCity", cascade = CascadeType.PERSIST)
  public List<Office> getOffices() {
    return this.offices;
  }
  
	private String attendantMail;


	private String attendantName;

	private String name;


	private List<Office> offices = new ArrayList<Office>();

	public OfficeCity() {
	}



	public void setAttendantMail(String attendantMail) {
		this.attendantMail = attendantMail;
	}



	public void setAttendantName(String attendantName) {
		this.attendantName = attendantName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

  public boolean equals(Object object){
    if(object instanceof OfficeCity) {
      OfficeCity officeCity2 = (OfficeCity) object;
      return getId().equals(officeCity2.getId()) ||
             getName().equals(officeCity2.getName()) ||
             getAttendantName().equals(officeCity2.getAttendantName()) ||
             getAttendantMail().equals(officeCity2.getAttendantMail());
    } else {
      return false;
    }
  }
  
  public int hashCode() {
    int hash = 1;
    hash = hash * 17 + id;
    hash = hash * 31 + ((name == null)?0:name.hashCode());
    hash = hash * 13 + ((attendantName == null)?0:attendantName.hashCode());
    hash = hash * 41 + ((attendantMail == null)?0:attendantMail.hashCode());
    return hash;
  }

	public void setOffices(List<Office> offices) {
		this.offices = offices;
	}

	public Office addOffice(Office office) {
		getOffices().add(office);
		office.setOfficeCity(this);

		return office;
	}

	public Office removeOffice(Office office) {
		getOffices().remove(office);
		office.setOfficeCity(null);

		return office;
	}

  @Override
  public int compareTo(OfficeCity o) {
    if(getId() == o.getId()) {  
      return 0;  
    } else if(getId() > o.getId()) { 
      return 1;  
    } else  {
      return -1;
    }
  }
}