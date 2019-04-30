package com.davi.batch.entity.informix;

import java.math.BigDecimal;
import java.util.Date;

public class EntityLegacyDb02Example {

	private Integer code;
	private Short value;
	private BigDecimal decimal;
	private String text;
	private Date exampleDate;
	
	public EntityLegacyDb02Example() {
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Short getValue() {
		return value;
	}

	public void setValue(Short value) {
		this.value = value;
	}

	public BigDecimal getDecimal() {
		return decimal;
	}

	public void setDecimal(BigDecimal decimal) {
		this.decimal = decimal;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getExampleDate() {
		return exampleDate;
	}

	public void setExampleDate(Date exampleDate) {
		this.exampleDate = exampleDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityLegacyDb02Example other = (EntityLegacyDb02Example) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

}
