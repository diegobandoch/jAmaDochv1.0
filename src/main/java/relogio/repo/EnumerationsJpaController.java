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
import relogio.entity.Enumerations;
import relogio.repo.exceptions.NonexistentEntityException;


/**
 *
 * @author amori
 */
public class EnumerationsJpaController implements Serializable {

    public EnumerationsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Enumerations enumerations) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(enumerations);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Enumerations enumerations) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            enumerations = em.merge(enumerations);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = enumerations.getId();
                if (findEnumerations(id) == null) {
                    throw new NonexistentEntityException("The enumerations with id " + id + " no longer exists.");
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
            Enumerations enumerations;
            try {
                enumerations = em.getReference(Enumerations.class, id);
                enumerations.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The enumerations with id " + id + " no longer exists.", enfe);
            }
            em.remove(enumerations);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Enumerations> findEnumerationsEntities() {
        return findEnumerationsEntities(true, -1, -1);
    }
    
    public List<Enumerations> findByTiposAtivos() {
        return emf.createEntityManager().createNamedQuery("Enumerations.findByTiposAtivos").getResultList();
    }

    public List<Enumerations> findEnumerationsEntities(int maxResults, int firstResult) {
        return findEnumerationsEntities(false, maxResults, firstResult);
    }

    private List<Enumerations> findEnumerationsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Enumerations.class));
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

    public Enumerations findEnumerations(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Enumerations.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnumerationsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Enumerations> rt = cq.from(Enumerations.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
