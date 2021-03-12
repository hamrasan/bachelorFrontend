package cz.fel.cvut.hamrasan.gardener.dao;

import cz.fel.cvut.hamrasan.gardener.model.Temperature;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class TemperatureDao extends BaseDao<Temperature> {
    public TemperatureDao(){super(Temperature.class);}

    public Temperature findLatest(){
        try {
            return em.createNamedQuery("Temperature.findLatest", Temperature.class).setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
