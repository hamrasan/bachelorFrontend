package cz.fel.cvut.hamrasan.gardener.dao;

import cz.fel.cvut.hamrasan.gardener.model.Valve;
import org.springframework.stereotype.Repository;

@Repository
public class ValveDao extends BaseDao<Valve> {

    public ValveDao(){super(Valve.class);}
}
