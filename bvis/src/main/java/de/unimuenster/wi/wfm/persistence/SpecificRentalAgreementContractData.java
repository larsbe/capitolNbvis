package de.unimuenster.wi.wfm.persistence;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

      
   @Entity
   public class SpecificRentalAgreementContractData extends AbstractEntity {
	   private static final long serialVersionUID = 1L;
		
		@NotNull(message="You have to enter a value for the field 'Name'" )
		protected String name;
		@NotNull(message="You have to enter a value for the field 'Detail'" )
		protected String detail;
		
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		
		public String getDetail() {
			return detail;
		}
		
		public void setDetail(String detail) {
			this.detail = detail;
		}
   }