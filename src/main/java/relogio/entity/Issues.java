/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogio.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amori
 */
@Entity
@Table(name = "issues")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Issues.findAll", query = "SELECT i FROM Issues i"),
    @NamedQuery(name = "Issues.findById", query = "SELECT i FROM Issues i WHERE i.id = :id"),
    @NamedQuery(name = "Issues.findByTrackerId", query = "SELECT i FROM Issues i WHERE i.trackerId = :trackerId"),
    @NamedQuery(name = "Issues.findByProjectId", query = "SELECT i FROM Issues i WHERE i.projectId = :projectId"),
    @NamedQuery(name = "Issues.findBySubject", query = "SELECT i FROM Issues i WHERE i.subject = :subject"),
    @NamedQuery(name = "Issues.findByDueDate", query = "SELECT i FROM Issues i WHERE i.dueDate = :dueDate"),
    @NamedQuery(name = "Issues.findByCategoryId", query = "SELECT i FROM Issues i WHERE i.categoryId = :categoryId"),
    @NamedQuery(name = "Issues.findByStatusId", query = "SELECT i FROM Issues i WHERE i.statusId = :statusId"),
    @NamedQuery(name = "Issues.findByAssignedToId", query = "SELECT i FROM Issues i WHERE i.assignedToId = :assignedToId"),
    @NamedQuery(name = "Issues.findByPriorityId", query = "SELECT i FROM Issues i WHERE i.priorityId = :priorityId"),
    @NamedQuery(name = "Issues.findByFixedVersionId", query = "SELECT i FROM Issues i WHERE i.fixedVersionId = :fixedVersionId"),
    @NamedQuery(name = "Issues.findByAuthorId", query = "SELECT i FROM Issues i WHERE i.authorId = :authorId"),
    @NamedQuery(name = "Issues.findByLockVersion", query = "SELECT i FROM Issues i WHERE i.lockVersion = :lockVersion"),
    @NamedQuery(name = "Issues.findByCreatedOn", query = "SELECT i FROM Issues i WHERE i.createdOn = :createdOn"),
    @NamedQuery(name = "Issues.findByUpdatedOn", query = "SELECT i FROM Issues i WHERE i.updatedOn = :updatedOn"),
    @NamedQuery(name = "Issues.findByStartDate", query = "SELECT i FROM Issues i WHERE i.startDate = :startDate"),
    @NamedQuery(name = "Issues.findByDoneRatio", query = "SELECT i FROM Issues i WHERE i.doneRatio = :doneRatio"),
    @NamedQuery(name = "Issues.findByEstimatedHours", query = "SELECT i FROM Issues i WHERE i.estimatedHours = :estimatedHours"),
    @NamedQuery(name = "Issues.findByParentId", query = "SELECT i FROM Issues i WHERE i.parentId = :parentId"),
    @NamedQuery(name = "Issues.findByRootId", query = "SELECT i FROM Issues i WHERE i.rootId = :rootId"),
    @NamedQuery(name = "Issues.findByLft", query = "SELECT i FROM Issues i WHERE i.lft = :lft"),
    @NamedQuery(name = "Issues.findByRgt", query = "SELECT i FROM Issues i WHERE i.rgt = :rgt"),
    @NamedQuery(name = "Issues.findByIsPrivate", query = "SELECT i FROM Issues i WHERE i.isPrivate = :isPrivate")})
public class Issues implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "tracker_id")
    private int trackerId;
    @Basic(optional = false)
    @Column(name = "project_id")
    private int projectId;
    @Basic(optional = false)
    @Column(name = "subject")
    private String subject;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @Column(name = "category_id")
    private Integer categoryId;
    @Basic(optional = false)
    @Column(name = "status_id")
    private int statusId;
    @Column(name = "assigned_to_id")
    private Integer assignedToId;
    @Basic(optional = false)
    @Column(name = "priority_id")
    private int priorityId;
    @Column(name = "fixed_version_id")
    private Integer fixedVersionId;
    @Basic(optional = false)
    @Column(name = "author_id")
    private int authorId;
    @Basic(optional = false)
    @Column(name = "lock_version")
    private int lockVersion;
    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @Column(name = "done_ratio")
    private int doneRatio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "estimated_hours")
    private Float estimatedHours;
    @Column(name = "parent_id")
    private Integer parentId;
    @Column(name = "root_id")
    private Integer rootId;
    @Column(name = "lft")
    private Integer lft;
    @Column(name = "rgt")
    private Integer rgt;
    @Basic(optional = false)
    @Column(name = "is_private")
    private boolean isPrivate;

    public Issues() {
    }

    public Issues(Integer id) {
        this.id = id;
    }

    public Issues(Integer id, int trackerId, int projectId, String subject, int statusId, int priorityId, int authorId, int lockVersion, int doneRatio, boolean isPrivate) {
        this.id = id;
        this.trackerId = trackerId;
        this.projectId = projectId;
        this.subject = subject;
        this.statusId = statusId;
        this.priorityId = priorityId;
        this.authorId = authorId;
        this.lockVersion = lockVersion;
        this.doneRatio = doneRatio;
        this.isPrivate = isPrivate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(int trackerId) {
        this.trackerId = trackerId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Integer getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Integer assignedToId) {
        this.assignedToId = assignedToId;
    }

    public int getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    public Integer getFixedVersionId() {
        return fixedVersionId;
    }

    public void setFixedVersionId(Integer fixedVersionId) {
        this.fixedVersionId = fixedVersionId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getLockVersion() {
        return lockVersion;
    }

    public void setLockVersion(int lockVersion) {
        this.lockVersion = lockVersion;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDoneRatio() {
        return doneRatio;
    }

    public void setDoneRatio(int doneRatio) {
        this.doneRatio = doneRatio;
    }

    public Float getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Float estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public Integer getLft() {
        return lft;
    }

    public void setLft(Integer lft) {
        this.lft = lft;
    }

    public Integer getRgt() {
        return rgt;
    }

    public void setRgt(Integer rgt) {
        this.rgt = rgt;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
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
        if (!(object instanceof Issues)) {
            return false;
        }
        Issues other = (Issues) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "relogio.entity.Issues[ id=" + id + " ]";
    }
    
}
