package com.ruchi.hibernate.model.DAO;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by Thamayanthy on 12/22/2014.
 */

@Entity
@Table(name = "foods_init", uniqueConstraints = {@UniqueConstraint(columnNames = {"food_id"})})
public class FoodInitDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "food_id", nullable = false, unique = true, length = 50)
    private String food_id;

    @Column(name = "food_name", nullable = false, unique = false, length = 50)
    private String food_name;

    @Column(name = "food_type", nullable = true, unique = false, length = 20)
    private String food_type;

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_type() {
        return food_type;
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
    }
}
