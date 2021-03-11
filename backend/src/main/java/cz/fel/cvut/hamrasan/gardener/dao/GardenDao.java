package cz.fel.cvut.hamrasan.gardener.dao;

import cz.fel.cvut.hamrasan.gardener.model.Garden;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class GardenDao extends BaseDao<Garden> {

    public GardenDao(){super(Garden.class);}

    public Garden findByName(String name) {
        try {
            return em.createNamedQuery("Garden.findByName", Garden.class).setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
