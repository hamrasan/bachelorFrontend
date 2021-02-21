package cz.fel.cvut.hamrasan.gardener.dao;

import cz.fel.cvut.hamrasan.gardener.model.PlantCategory;
import org.springframework.stereotype.Repository;

@Repository
public class PlantCategoryDao extends BaseDao<PlantCategory> {
    public PlantCategoryDao(){super(PlantCategory.class);}
}
