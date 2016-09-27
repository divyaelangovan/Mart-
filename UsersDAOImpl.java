package com.niit.martzone.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.martzone.model.Users;

@Repository("usersDAO")
public class UsersDAOImpl implements UsersDAO {

	@Autowired
	private SessionFactory sessionFactory;


	public UsersDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public List<Users> list() {
		@SuppressWarnings("unchecked")
		List<Users> list = (List<Users>) sessionFactory.getCurrentSession().createCriteria(Users.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		return list;
	}

	@Transactional
	public void saveOrUpdate(Users users) {
		users.setRole("ROLE_USER");
		users.setEnabled(true);
		sessionFactory.getCurrentSession().saveOrUpdate(users);

	}

	/*
	 * @Transactional public void saveOrUpdate(User userDetails) {
	 * sessionFactory.getCurrentSession().saveOrUpdate(userDetails); }
	 */

	@Transactional
	public void delete(String username) {
		Users users = new Users();
		users.setUsername(username);
		
		sessionFactory.getCurrentSession().delete(users);
	}

	@Transactional
	public Users get(String username) {
		String hql = "from Users where username=" + username;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		@SuppressWarnings("unchecked")
		List<Users> list = (List<Users>) query.list();

		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	public boolean equals(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isValidUser1(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Transactional
	public boolean isValidUser(String username, String password) {
		System.out.println("dao impl");
		String hql = "from Users where username= '" + username + "' and " + " password ='" + password + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		@SuppressWarnings("unchecked")
		List<Users> list = (List<Users>) query.list();

		if (list != null && !list.isEmpty()) {
			return true;
		}

		return false;
	}

	
	
	
}
