package cz.fel.cvut.hamrasan.gardener.dao;

import cz.fel.cvut.hamrasan.gardener.model.Pressure;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class PressureDao extends BaseDao<Pressure> {
    public PressureDao(){super(Pressure.class);}

    public Pressure findLatest(){
        try {
            return em.createNamedQuery("Pressure.findLatest", Pressure.class).setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
