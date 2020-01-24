/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polsl.jium.kszerlag.dao;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity represented by History table in database.
 * Table based on this entity contains calculated expressions in past.
 *
 * @author Kamil Szerląg
 * @version 1.0
 */
@Entity
@Table(name = "history")
public class History implements Serializable {

    /**
     * For serialization purpose
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Entity id.
     * Primary key for History table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * Calculated expression.
     * Example: 1/2+2/3
     */
    private String expression;
    
    /**
     * Result of calculation.
     * Example: 7/6
     */
    private String result;

    /**
     * Default constructor.
     * Required for JPA Entity
     */
    public History() {
    }

    /**
     * Non default constructor. 
     * 
     * @param expression Calculated expression.
     * @param result Result of calculation.
     */
    public History(String expression, String result) {
        this.expression = expression;
        this.result = result;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof History)) {
            return false;
        }
        History other = (History) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "polsl.jium.kszerlag.dao.ExpressionDao[ id=" + id + " ]";
    }
    
}
