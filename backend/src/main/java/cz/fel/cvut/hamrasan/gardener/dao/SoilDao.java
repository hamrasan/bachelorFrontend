package cz.fel.cvut.hamrasan.gardener.dao;

import cz.fel.cvut.hamrasan.gardener.model.Soil;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class SoilDao extends BaseDao<Soil> {

    public SoilDao(){super(Soil.class);}

    public Soil findLatest() {

        try {
            return em.createNamedQuery("Soil.findLatest", Soil.class).setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
