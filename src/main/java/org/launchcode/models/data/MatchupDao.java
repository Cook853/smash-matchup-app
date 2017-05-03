package org.launchcode.models.data;

import org.launchcode.models.Matchup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Lauren on 4/18/2017.
 */
@Repository
@Transactional
public interface MatchupDao extends CrudRepository<Matchup, Integer> {
}
