package com.capsule.TaskTracker.jdbc;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.capsule.TaskTracker.entity.Task;
import com.capsule.TaskTracker.entity.User;

@Repository
public class UserDAO {
	
	EntityManager entityManager;
	
	
	@Autowired
	public UserDAO(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	

	public boolean insertUser(User user) {
		Session currentSession = entityManager.unwrap(Session.class);
//		System.out.println("inserting user...........");
		System.out.println(user);
		
		currentSession.save(user);
		return true;
	}

	public User getUser(int userId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
//		System.out.println(userId);
//		System.out.println("get user dta");
		
		Query<User> query = 
				currentSession.createQuery("from User where userId=:id",User.class);
		query.setParameter("id", userId);
//		System.out.println("Query" + query);
		
		User existingUser = query.getSingleResult();
//		System.out.println("get user dta");
		
//		System.out.println(existingUser);
		return existingUser;
	}

	public List<User> getUserList() {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<User> query = 
				currentSession.createQuery("from User",User.class);
		
		List<User> userList = query.getResultList();
//		System.out.println(userList);
		
		return userList;
	}

	@Transactional
	public boolean deleteUser(int userId) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		User existingUser = getUser(userId);
		currentSession.delete(existingUser);
		return true;
	}

	@Transactional
	public boolean updateUser(User user) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.update(user);
		currentSession.close();

		return true;
	}


	public User getUserbyProjectId(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<User> query = 
				currentSession.createQuery("from User where projectId=:id",User.class);
		query.setParameter("id", id);
		
		User existingUser = query.getSingleResult();
//		System.out.println("get user dta");
		
//		System.out.println(existingUser);
		return existingUser;
	}


	public void updateUsersdelete(int projectId) {
		Session currentSession = entityManager.unwrap(Session.class);
//		System.out.println("updating User on delete for project " + projectId);
		Query query = 
				currentSession.createQuery("update User set project_id = 0 where project_id=:id");
		query.setParameter("id", projectId);
		int result = query.executeUpdate();
	}

}
