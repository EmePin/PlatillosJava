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
import Modelo.Ingrediente;
import Modelo.TipoIngrediente;
import accesodatos.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aimee
 */
public class TipoIngredienteJpaController implements Serializable {

    public TipoIngredienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoIngrediente tipoIngrediente) {
        if (tipoIngrediente.getIngredienteList() == null) {
            tipoIngrediente.setIngredienteList(new ArrayList<Ingrediente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ingrediente> attachedIngredienteList = new ArrayList<Ingrediente>();
            for (Ingrediente ingredienteListIngredienteToAttach : tipoIngrediente.getIngredienteList()) {
                ingredienteListIngredienteToAttach = em.getReference(ingredienteListIngredienteToAttach.getClass(), ingredienteListIngredienteToAttach.getIding());
                attachedIngredienteList.add(ingredienteListIngredienteToAttach);
            }
            tipoIngrediente.setIngredienteList(attachedIngredienteList);
            em.persist(tipoIngrediente);
            for (Ingrediente ingredienteListIngrediente : tipoIngrediente.getIngredienteList()) {
                TipoIngrediente oldTipoingOfIngredienteListIngrediente = ingredienteListIngrediente.getTipoing();
                ingredienteListIngrediente.setTipoing(tipoIngrediente);
                ingredienteListIngrediente = em.merge(ingredienteListIngrediente);
                if (oldTipoingOfIngredienteListIngrediente != null) {
                    oldTipoingOfIngredienteListIngrediente.getIngredienteList().remove(ingredienteListIngrediente);
                    oldTipoingOfIngredienteListIngrediente = em.merge(oldTipoingOfIngredienteListIngrediente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoIngrediente tipoIngrediente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoIngrediente persistentTipoIngrediente = em.find(TipoIngrediente.class, tipoIngrediente.getIding());
            List<Ingrediente> ingredienteListOld = persistentTipoIngrediente.getIngredienteList();
            List<Ingrediente> ingredienteListNew = tipoIngrediente.getIngredienteList();
            List<Ingrediente> attachedIngredienteListNew = new ArrayList<Ingrediente>();
            for (Ingrediente ingredienteListNewIngredienteToAttach : ingredienteListNew) {
                ingredienteListNewIngredienteToAttach = em.getReference(ingredienteListNewIngredienteToAttach.getClass(), ingredienteListNewIngredienteToAttach.getIding());
                attachedIngredienteListNew.add(ingredienteListNewIngredienteToAttach);
            }
            ingredienteListNew = attachedIngredienteListNew;
            tipoIngrediente.setIngredienteList(ingredienteListNew);
            tipoIngrediente = em.merge(tipoIngrediente);
            for (Ingrediente ingredienteListOldIngrediente : ingredienteListOld) {
                if (!ingredienteListNew.contains(ingredienteListOldIngrediente)) {
                    ingredienteListOldIngrediente.setTipoing(null);
                    ingredienteListOldIngrediente = em.merge(ingredienteListOldIngrediente);
                }
            }
            for (Ingrediente ingredienteListNewIngrediente : ingredienteListNew) {
                if (!ingredienteListOld.contains(ingredienteListNewIngrediente)) {
                    TipoIngrediente oldTipoingOfIngredienteListNewIngrediente = ingredienteListNewIngrediente.getTipoing();
                    ingredienteListNewIngrediente.setTipoing(tipoIngrediente);
                    ingredienteListNewIngrediente = em.merge(ingredienteListNewIngrediente);
                    if (oldTipoingOfIngredienteListNewIngrediente != null && !oldTipoingOfIngredienteListNewIngrediente.equals(tipoIngrediente)) {
                        oldTipoingOfIngredienteListNewIngrediente.getIngredienteList().remove(ingredienteListNewIngrediente);
                        oldTipoingOfIngredienteListNewIngrediente = em.merge(oldTipoingOfIngredienteListNewIngrediente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoIngrediente.getIding();
                if (findTipoIngrediente(id) == null) {
                    throw new NonexistentEntityException("The tipoIngrediente with id " + id + " no longer exists.");
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
            TipoIngrediente tipoIngrediente;
            try {
                tipoIngrediente = em.getReference(TipoIngrediente.class, id);
                tipoIngrediente.getIding();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoIngrediente with id " + id + " no longer exists.", enfe);
            }
            List<Ingrediente> ingredienteList = tipoIngrediente.getIngredienteList();
            for (Ingrediente ingredienteListIngrediente : ingredienteList) {
                ingredienteListIngrediente.setTipoing(null);
                ingredienteListIngrediente = em.merge(ingredienteListIngrediente);
            }
            em.remove(tipoIngrediente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoIngrediente> findTipoIngredienteEntities() {
        return findTipoIngredienteEntities(true, -1, -1);
    }

    public List<TipoIngrediente> findTipoIngredienteEntities(int maxResults, int firstResult) {
        return findTipoIngredienteEntities(false, maxResults, firstResult);
    }

    private List<TipoIngrediente> findTipoIngredienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoIngrediente.class));
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

    public TipoIngrediente findTipoIngrediente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoIngrediente.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoIngredienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoIngrediente> rt = cq.from(TipoIngrediente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
