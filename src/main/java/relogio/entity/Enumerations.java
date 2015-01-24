/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogio.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amori
 */
@Entity
@Table(name = "enumerations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Enumerations.findAll", query = "SELECT e FROM Enumerations e"),
    @NamedQuery(name = "Enumerations.findById", query = "SELECT e FROM Enumerations e WHERE e.id = :id"),
    @NamedQuery(name = "Enumerations.findByName", query = "SELECT e FROM Enumerations e WHERE e.name = :name"),
    @NamedQuery(name = "Enumerations.findByPosition", query = "SELECT e FROM Enumerations e WHERE e.position = :position"),
    @NamedQuery(name = "Enumerations.findByIsDefault", query = "SELECT e FROM Enumerations e WHERE e.isDefault = :isDefault"),
    @NamedQuery(name = "Enumerations.findByType", query = "SELECT e FROM Enumerations e WHERE e.type = :type"),
    @NamedQuery(name = "Enumerations.findByActive", query = "SELECT e FROM Enumerations e WHERE e.active = :active"),
    @NamedQuery(name = "Enumerations.findByProjectId", query = "SELECT e FROM Enumerations e WHERE e.projectId = :projectId"),
    @NamedQuery(name = "Enumerations.findByParentId", query = "SELECT e FROM Enumerations e WHERE e.parentId = :parentId"),
    @NamedQuery(name = "Enumerations.findByTiposAtivos", query = "SELECT e FROM Enumerations e WHERE e.type = 'TimeEntryActivity' and e.active = true")})
public class Enumerations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "position")
    private Integer position;
    @Basic(optional = false)
    @Column(name = "is_default")
    private boolean isDefault;
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @Column(name = "project_id")
    private Integer projectId;
    @Column(name = "parent_id")
    private Integer parentId;

    public Enumerations() {
    }

    public Enumerations(Integer id) {
        this.id = id;
    }

    public Enumerations(Integer id, String name, boolean isDefault, boolean active) {
        this.id = id;
        this.name = name;
        this.isDefault = isDefault;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Enumerations)) {
            return false;
        }
        Enumerations other = (Enumerations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
