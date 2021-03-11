package cz.fel.cvut.hamrasan.gardener.dao;

import cz.fel.cvut.hamrasan.gardener.model.Pressure;
import org.springframework.stereotype.Repository;

@Repository
public class PressureDao extends BaseDao<Pressure> {
    public PressureDao(){super(Pressure.class);}
}
