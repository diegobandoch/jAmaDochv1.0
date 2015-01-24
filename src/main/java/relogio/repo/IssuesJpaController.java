/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relogio.repo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import relogio.entity.Issues;
import relogio.repo.exceptions.NonexistentEntityException;

/**
 *
 * @author amori
 */
public class IssuesJpaController implements Serializable {

    public IssuesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Issues issues) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(issues);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Issues issues) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            issues = em.merge(issues);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = issues.getId();
                if (findIssues(id) == null) {
                    throw new NonexistentEntityException("The issues with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Issues issues;
            try {
                issues = em.getReference(Issues.class, id);
                issues.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The issues with id " + id + " no longer exists.", enfe);
            }
            em.remove(issues);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Issues> findIssuesEntities() {
        return findIssuesEntities(true, -1, -1);
    }

    public List<Issues> findIssuesEntities(int maxResults, int firstResult) {
        return findIssuesEntities(false, maxResults, firstResult);
    }

    private List<Issues> findIssuesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Issues.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Issues findIssues(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Issues.class, id);
        } finally {
            em.close();
        }
    }

    public int getIssuesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Issues> rt = cq.from(Issues.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
