/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesodatos;

import Modelo.Ingpla;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Ingrediente;
import Modelo.Platillo;
import accesodatos.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aimee
 */
public class IngplaJpaController implements Serializable {

    public IngplaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingpla ingpla) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingrediente iding = ingpla.getIding();
            if (iding != null) {
                iding = em.getReference(iding.getClass(), iding.getIding());
                ingpla.setIding(iding);
            }
            Platillo idpla = ingpla.getIdpla();
            if (idpla != null) {
                idpla = em.getReference(idpla.getClass(), idpla.getIdplatillo());
                ingpla.setIdpla(idpla);
            }
            em.persist(ingpla);
            if (iding != null) {
                iding.getIngplaList().add(ingpla);
                iding = em.merge(iding);
            }
            if (idpla != null) {
                idpla.getIngplaList().add(ingpla);
                idpla = em.merge(idpla);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingpla ingpla) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingpla persistentIngpla = em.find(Ingpla.class, ingpla.getIdip());
            Ingrediente idingOld = persistentIngpla.getIding();
            Ingrediente idingNew = ingpla.getIding();
            Platillo idplaOld = persistentIngpla.getIdpla();
            Platillo idplaNew = ingpla.getIdpla();
            if (idingNew != null) {
                idingNew = em.getReference(idingNew.getClass(), idingNew.getIding());
                ingpla.setIding(idingNew);
            }
            if (idplaNew != null) {
                idplaNew = em.getReference(idplaNew.getClass(), idplaNew.getIdplatillo());
                ingpla.setIdpla(idplaNew);
            }
            ingpla = em.merge(ingpla);
            if (idingOld != null && !idingOld.equals(idingNew)) {
                idingOld.getIngplaList().remove(ingpla);
                idingOld = em.merge(idingOld);
            }
            if (idingNew != null && !idingNew.equals(idingOld)) {
                idingNew.getIngplaList().add(ingpla);
                idingNew = em.merge(idingNew);
            }
            if (idplaOld != null && !idplaOld.equals(idplaNew)) {
                idplaOld.getIngplaList().remove(ingpla);
                idplaOld = em.merge(idplaOld);
            }
            if (idplaNew != null && !idplaNew.equals(idplaOld)) {
                idplaNew.getIngplaList().add(ingpla);
                idplaNew = em.merge(idplaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ingpla.getIdip();
                if (findIngpla(id) == null) {
                    throw new NonexistentEntityException("The ingpla with id " + id + " no longer exists.");
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
            Ingpla ingpla;
            try {
                ingpla = em.getReference(Ingpla.class, id);
                ingpla.getIdip();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingpla with id " + id + " no longer exists.", enfe);
            }
            Ingrediente iding = ingpla.getIding();
            if (iding != null) {
                iding.getIngplaList().remove(ingpla);
                iding = em.merge(iding);
            }
            Platillo idpla = ingpla.getIdpla();
            if (idpla != null) {
                idpla.getIngplaList().remove(ingpla);
                idpla = em.merge(idpla);
            }
            em.remove(ingpla);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingpla> findIngplaEntities() {
        return findIngplaEntities(true, -1, -1);
    }

    public List<Ingpla> findIngplaEntities(int maxResults, int firstResult) {
        return findIngplaEntities(false, maxResults, firstResult);
    }

    private List<Ingpla> findIngplaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingpla.class));
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

    public Ingpla findIngpla(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingpla.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngplaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingpla> rt = cq.from(Ingpla.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
