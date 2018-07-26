package net.sharelog.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.sharelog.repositories.User;

@Repository
//@Scope("prototype")
@Qualifier("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {

}
