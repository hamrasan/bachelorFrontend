package cz.fel.cvut.hamrasan.gardener.dao;

import cz.fel.cvut.hamrasan.gardener.model.Humidity;
import org.springframework.stereotype.Repository;

@Repository
public class HumidityDao extends BaseDao<Humidity> {
    public HumidityDao(){super(Humidity.class);}
}
