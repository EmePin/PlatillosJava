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
import Modelo.TipoIngrediente;
import Modelo.Ingpla;
import Modelo.Ingrediente;
import accesodatos.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aimee
 */
public class IngredienteJpaController implements Serializable {

    public IngredienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingrediente ingrediente) {
        if (ingrediente.getIngplaList() == null) {
            ingrediente.setIngplaList(new ArrayList<Ingpla>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoIngrediente tipoing = ingrediente.getTipoing();
            if (tipoing != null) {
                tipoing = em.getReference(tipoing.getClass(), tipoing.getIding());
                ingrediente.setTipoing(tipoing);
            }
            List<Ingpla> attachedIngplaList = new ArrayList<Ingpla>();
            for (Ingpla ingplaListIngplaToAttach : ingrediente.getIngplaList()) {
                ingplaListIngplaToAttach = em.getReference(ingplaListIngplaToAttach.getClass(), ingplaListIngplaToAttach.getIdip());
                attachedIngplaList.add(ingplaListIngplaToAttach);
            }
            ingrediente.setIngplaList(attachedIngplaList);
            em.persist(ingrediente);
            if (tipoing != null) {
                tipoing.getIngredienteList().add(ingrediente);
                tipoing = em.merge(tipoing);
            }
            for (Ingpla ingplaListIngpla : ingrediente.getIngplaList()) {
                Ingrediente oldIdingOfIngplaListIngpla = ingplaListIngpla.getIding();
                ingplaListIngpla.setIding(ingrediente);
                ingplaListIngpla = em.merge(ingplaListIngpla);
                if (oldIdingOfIngplaListIngpla != null) {
                    oldIdingOfIngplaListIngpla.getIngplaList().remove(ingplaListIngpla);
                    oldIdingOfIngplaListIngpla = em.merge(oldIdingOfIngplaListIngpla);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingrediente ingrediente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingrediente persistentIngrediente = em.find(Ingrediente.class, ingrediente.getIding());
            TipoIngrediente tipoingOld = persistentIngrediente.getTipoing();
            TipoIngrediente tipoingNew = ingrediente.getTipoing();
            List<Ingpla> ingplaListOld = persistentIngrediente.getIngplaList();
            List<Ingpla> ingplaListNew = ingrediente.getIngplaList();
            if (tipoingNew != null) {
                tipoingNew = em.getReference(tipoingNew.getClass(), tipoingNew.getIding());
                ingrediente.setTipoing(tipoingNew);
            }
            List<Ingpla> attachedIngplaListNew = new ArrayList<Ingpla>();
            for (Ingpla ingplaListNewIngplaToAttach : ingplaListNew) {
                ingplaListNewIngplaToAttach = em.getReference(ingplaListNewIngplaToAttach.getClass(), ingplaListNewIngplaToAttach.getIdip());
                attachedIngplaListNew.add(ingplaListNewIngplaToAttach);
            }
            ingplaListNew = attachedIngplaListNew;
            ingrediente.setIngplaList(ingplaListNew);
            ingrediente = em.merge(ingrediente);
            if (tipoingOld != null && !tipoingOld.equals(tipoingNew)) {
                tipoingOld.getIngredienteList().remove(ingrediente);
                tipoingOld = em.merge(tipoingOld);
            }
            if (tipoingNew != null && !tipoingNew.equals(tipoingOld)) {
                tipoingNew.getIngredienteList().add(ingrediente);
                tipoingNew = em.merge(tipoingNew);
            }
            for (Ingpla ingplaListOldIngpla : ingplaListOld) {
                if (!ingplaListNew.contains(ingplaListOldIngpla)) {
                    ingplaListOldIngpla.setIding(null);
                    ingplaListOldIngpla = em.merge(ingplaListOldIngpla);
                }
            }
            for (Ingpla ingplaListNewIngpla : ingplaListNew) {
                if (!ingplaListOld.contains(ingplaListNewIngpla)) {
                    Ingrediente oldIdingOfIngplaListNewIngpla = ingplaListNewIngpla.getIding();
                    ingplaListNewIngpla.setIding(ingrediente);
                    ingplaListNewIngpla = em.merge(ingplaListNewIngpla);
                    if (oldIdingOfIngplaListNewIngpla != null && !oldIdingOfIngplaListNewIngpla.equals(ingrediente)) {
                        oldIdingOfIngplaListNewIngpla.getIngplaList().remove(ingplaListNewIngpla);
                        oldIdingOfIngplaListNewIngpla = em.merge(oldIdingOfIngplaListNewIngpla);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ingrediente.getIding();
                if (findIngrediente(id) == null) {
                    throw new NonexistentEntityException("The ingrediente with id " + id + " no longer exists.");
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
            Ingrediente ingrediente;
            try {
                ingrediente = em.getReference(Ingrediente.class, id);
                ingrediente.getIding();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingrediente with id " + id + " no longer exists.", enfe);
            }
            TipoIngrediente tipoing = ingrediente.getTipoing();
            if (tipoing != null) {
                tipoing.getIngredienteList().remove(ingrediente);
                tipoing = em.merge(tipoing);
            }
            List<Ingpla> ingplaList = ingrediente.getIngplaList();
            for (Ingpla ingplaListIngpla : ingplaList) {
                ingplaListIngpla.setIding(null);
                ingplaListIngpla = em.merge(ingplaListIngpla);
            }
            em.remove(ingrediente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingrediente> findIngredienteEntities() {
        return findIngredienteEntities(true, -1, -1);
    }

    public List<Ingrediente> findIngredienteEntities(int maxResults, int firstResult) {
        return findIngredienteEntities(false, maxResults, firstResult);
    }

    private List<Ingrediente> findIngredienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingrediente.class));
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

    public Ingrediente findIngrediente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingrediente.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngredienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingrediente> rt = cq.from(Ingrediente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
