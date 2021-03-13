package cz.fel.cvut.hamrasan.gardener.dao;

import cz.fel.cvut.hamrasan.gardener.model.Humidity;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class HumidityDao extends BaseDao<Humidity> {
    public HumidityDao(){super(Humidity.class);}

    public Humidity findLatest(){
        try {
            return em.createNamedQuery("Humidity.findLatest", Humidity.class).setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
