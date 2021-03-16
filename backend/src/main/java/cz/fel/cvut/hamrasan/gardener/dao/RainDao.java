package cz.fel.cvut.hamrasan.gardener.dao;

import cz.fel.cvut.hamrasan.gardener.model.Rain;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class RainDao extends BaseDao<Rain> {

    public RainDao(){super(Rain.class);}

    public Rain findLatest(){
        try {
            return em.createNamedQuery("Rain.findLatest", Rain.class).setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
