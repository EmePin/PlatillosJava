/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesodatos;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Ingpla;
import Modelo.Platillo;
import accesodatos.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aimee
 */
public class PlatilloJpaController implements Serializable {

    public PlatilloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Platillo platillo) {
        if (platillo.getIngplaList() == null) {
            platillo.setIngplaList(new ArrayList<Ingpla>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ingpla> attachedIngplaList = new ArrayList<Ingpla>();
            for (Ingpla ingplaListIngplaToAttach : platillo.getIngplaList()) {
                ingplaListIngplaToAttach = em.getReference(ingplaListIngplaToAttach.getClass(), ingplaListIngplaToAttach.getIdip());
                attachedIngplaList.add(ingplaListIngplaToAttach);
            }
            platillo.setIngplaList(attachedIngplaList);
            em.persist(platillo);
            for (Ingpla ingplaListIngpla : platillo.getIngplaList()) {
                Platillo oldIdplaOfIngplaListIngpla = ingplaListIngpla.getIdpla();
                ingplaListIngpla.setIdpla(platillo);
                ingplaListIngpla = em.merge(ingplaListIngpla);
                if (oldIdplaOfIngplaListIngpla != null) {
                    oldIdplaOfIngplaListIngpla.getIngplaList().remove(ingplaListIngpla);
                    oldIdplaOfIngplaListIngpla = em.merge(oldIdplaOfIngplaListIngpla);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Platillo platillo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Platillo persistentPlatillo = em.find(Platillo.class, platillo.getIdplatillo());
            List<Ingpla> ingplaListOld = persistentPlatillo.getIngplaList();
            List<Ingpla> ingplaListNew = platillo.getIngplaList();
            List<Ingpla> attachedIngplaListNew = new ArrayList<Ingpla>();
            for (Ingpla ingplaListNewIngplaToAttach : ingplaListNew) {
                ingplaListNewIngplaToAttach = em.getReference(ingplaListNewIngplaToAttach.getClass(), ingplaListNewIngplaToAttach.getIdip());
                attachedIngplaListNew.add(ingplaListNewIngplaToAttach);
            }
            ingplaListNew = attachedIngplaListNew;
            platillo.setIngplaList(ingplaListNew);
            platillo = em.merge(platillo);
            for (Ingpla ingplaListOldIngpla : ingplaListOld) {
                if (!ingplaListNew.contains(ingplaListOldIngpla)) {
                    ingplaListOldIngpla.setIdpla(null);
                    ingplaListOldIngpla = em.merge(ingplaListOldIngpla);
                }
            }
            for (Ingpla ingplaListNewIngpla : ingplaListNew) {
                if (!ingplaListOld.contains(ingplaListNewIngpla)) {
                    Platillo oldIdplaOfIngplaListNewIngpla = ingplaListNewIngpla.getIdpla();
                    ingplaListNewIngpla.setIdpla(platillo);
                    ingplaListNewIngpla = em.merge(ingplaListNewIngpla);
                    if (oldIdplaOfIngplaListNewIngpla != null && !oldIdplaOfIngplaListNewIngpla.equals(platillo)) {
                        oldIdplaOfIngplaListNewIngpla.getIngplaList().remove(ingplaListNewIngpla);
                        oldIdplaOfIngplaListNewIngpla = em.merge(oldIdplaOfIngplaListNewIngpla);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = platillo.getIdplatillo();
                if (findPlatillo(id) == null) {
                    throw new NonexistentEntityException("The platillo with id " + id + " no longer exists.");
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
            Platillo platillo;
            try {
                platillo = em.getReference(Platillo.class, id);
                platillo.getIdplatillo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The platillo with id " + id + " no longer exists.", enfe);
            }
            List<Ingpla> ingplaList = platillo.getIngplaList();
            for (Ingpla ingplaListIngpla : ingplaList) {
                ingplaListIngpla.setIdpla(null);
                ingplaListIngpla = em.merge(ingplaListIngpla);
            }
            em.remove(platillo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Platillo> findPlatilloEntities() {
        return findPlatilloEntities(true, -1, -1);
    }

    public List<Platillo> findPlatilloEntities(int maxResults, int firstResult) {
        return findPlatilloEntities(false, maxResults, firstResult);
    }

    private List<Platillo> findPlatilloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Platillo.class));
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

    public Platillo findPlatillo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Platillo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlatilloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Platillo> rt = cq.from(Platillo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
