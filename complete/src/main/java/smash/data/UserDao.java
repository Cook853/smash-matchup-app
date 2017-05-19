package smash.data;

import smash.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Lauren on 5/11/2017.
 */
@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {

}
