package net.sharelog.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import org.springframework.transaction.annotation.Transactional;

import net.sharelog.dao.UserRepository;
import net.sharelog.repositories.User;

@Service
public class UserService implements IUserService {
	@Autowired
	UserRepository userRepository;
 
	private static Integer BATCH_TIMES = 10;
	private static Integer BATCH_SIZE = 5000;
	private static Integer THREAD_NUMS = 10;
	
	@Transactional
	//@Rollback(false)
	private void mytest(String name, CountDownLatch cl) {

		Thread t1 = new Thread(new MyDBJob(name, cl));
		t1.start();
	}
	
	@Override
	public void register() {
		long begin = System.currentTimeMillis();
		CountDownLatch countlatch = new CountDownLatch(THREAD_NUMS);
		for (int i = 0; i < THREAD_NUMS; i++) {
			mytest("worker:" + i, countlatch);

		}
		try {
			countlatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long duration = (System.currentTimeMillis() - begin) / 1000;
		System.out.println("The task took :" + duration + " s. The TPS is:" + THREAD_NUMS * BATCH_TIMES * BATCH_SIZE / duration +"!");
	}
	
	class MyDBJob implements Runnable {
		private String name;
		CountDownLatch countDownLatch;

		public MyDBJob(String n, CountDownLatch cl) {
			this.name = n;
			this.countDownLatch = cl;
		}

		@Override
		public void run() {
			
			for (int sum = 0; sum < BATCH_TIMES; sum++) {
				List<User> users = new ArrayList<User>();
				for (int i = 0; i < BATCH_SIZE; i++) {
					User n = new User();
					n.setName("name" + i);
					n.setEmail("email" + i);
					n.setDescription("setDescriptionsetDescriptionsetDescriptionsetDescriptionsetDescriptionsetDescriptionsetDescriptionsetDescriptionsetDescriptionsetDescriptionsetDescription"+i);
					n.setAddress("addressaddressaddressaddressaddressaddressaddressaddressaddressaddressaddressaddressaddressaddressaddressaddressaddress"+i);
					n.setLastUpdate(new Date().toString());
					n.setSex(""+i);
					users.add(n);
				}
				userRepository.saveAll(users);
				users = null;
				//users.clear();
			}

			countDownLatch.countDown();
		}
	}
}
