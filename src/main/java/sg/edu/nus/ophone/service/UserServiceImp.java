package sg.edu.nus.ophone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.ophone.interfacemethods.UserService;
import sg.edu.nus.ophone.model.User;
import sg.edu.nus.ophone.repository.UserRepository;

import java.util.List;

/**
 * 
 * Creating, Cancel, RetriveOrder(by userId or orderId), and comfirmOrder
 * order details in the cart system.
 * 
 * Created by: LianDa,GaoZijie
 * Created on: 10/09/2024
 */
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

	@Override
	public User findByUserId(int id) {
		return userRepository.findByUserId(id);
	}

	@Override
	public boolean login(String name,String password) {
		User user=userRepository.findByName(name);
		
		if(user != null) {
			return user.getPassword().equals(password);
		}
		return false;
	}
	@Override
	public boolean usernameExists(String username) {
		return userRepository.existsByName(username);
		
	}
	
	public void saveUser(User user) {
	userRepository.save(user);
	}

	@Override
	public User findByName(String name) {
		return userRepository.findByName(name);
	}
}