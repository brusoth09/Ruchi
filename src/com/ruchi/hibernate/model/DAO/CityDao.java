package com.ruchi.hibernate.model.DAO;

import javax.persistence.*;

/**
 * Created by Thamayanthy on 12/22/2014.
 */

@Entity
@Table(name = "city", uniqueConstraints = {@UniqueConstraint(columnNames = {"city_id"})})
public class CityDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "city_id", nullable = false, unique = true, length = 20)
    private String city_id;

    @Column(name = "city", nullable = false, unique = false, length = 20)
    private String city;

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

   
}
