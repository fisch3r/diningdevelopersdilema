package org.diningdevelopers.core.database.gateways;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.MappingService;
import org.diningdevelopers.core.business.model.User;
import org.diningdevelopers.core.business.persistence.UserPersistence;
import org.diningdevelopers.core.database.dao.UserDao;
import org.diningdevelopers.core.database.entities.UserEntity;

@Stateless
public class UserGateway implements UserPersistence {

	@Inject
	private UserDao userDao;
	
	@Inject
	private MappingService mappingService;
	
	@Override
	public List<User> findAll() {
		return mappingService.mapCollection(userDao.findAll(), User.class);
	}

	@Override
	public User findByUsername(String username) {
		return mappingService.map(userDao.findByUsername(username), User.class);
	}

	@Override
	public User findById(Long id) {
		return mappingService.map(userDao.findById(id), User.class);
	}

	@Override
	public void persist(User user) {
		userDao.persist(mappingService.map(user, UserEntity.class));
	}

}