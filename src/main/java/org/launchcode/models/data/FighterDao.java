package org.launchcode.models.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.launchcode.models.Fighter;

import javax.transaction.Transactional;

/**
 * Created by Lauren on 4/18/2017.
 */
@Repository
@Transactional
public interface FighterDao extends CrudRepository<Fighter, Integer>{
}
