package smash.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import smash.model.Fighter;

import javax.transaction.Transactional;

/**
 * Created by Lauren on 5/15/2017.
 */
@Repository
@Transactional
public interface FighterDao extends CrudRepository<Fighter, Integer> {
}
