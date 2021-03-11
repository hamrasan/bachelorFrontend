package cz.fel.cvut.hamrasan.gardener.dao;

import cz.fel.cvut.hamrasan.gardener.model.Temperature;
import org.springframework.stereotype.Repository;

@Repository
public class TemperatureDao extends BaseDao<Temperature> {
    public TemperatureDao(){super(Temperature.class);}
}
