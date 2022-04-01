/*
 * Copyright (C) 2020-2022  Gagan Thind

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package in.gagan.base.framework.dto.location;

import java.util.Objects;

/**
 * This DTO captures the country specific data from csv file.
 * 
 * @author gaganthind
 *
 */
public class CountryInputDTO {

	private String city;
	
	private String state;
	
	private String country;
	
	private long zipcode;
	
	public CountryInputDTO() { }

	public CountryInputDTO(String city, String state, String country, long zipcode) {
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode = zipcode;
	}

	public long getZipcode() {
		return zipcode;
	}

	public void setZipcode(long zipcode) {
		this.zipcode = zipcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CountryInputDTO that = (CountryInputDTO) o;
		return zipcode == that.zipcode && city.equals(that.city) && state.equals(that.state) && country.equals(that.country);
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, state, country, zipcode);
	}
}
