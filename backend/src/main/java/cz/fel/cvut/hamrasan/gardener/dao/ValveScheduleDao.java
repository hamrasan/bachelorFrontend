package cz.fel.cvut.hamrasan.gardener.dao;

import cz.fel.cvut.hamrasan.gardener.model.ValveSchedule;
import org.springframework.stereotype.Repository;

@Repository
public class ValveScheduleDao extends BaseDao<ValveSchedule> {

    public ValveScheduleDao(){super(ValveSchedule.class);}

}
