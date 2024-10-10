package sg.edu.nus.ophone.interfacemethods;

import org.springframework.data.repository.query.Param;
import sg.edu.nus.ophone.model.User;

import java.util.List;

/**
 * 
 * Creating, Cancel, RetriveOrder(by userId or orderId), and comfirmOrder
 * order details in the cart system.
 * 
 * Created by: LianDa,GaoZijie
 * Created on: 10/09/2024
 */

public interface UserService {

	User findByUserId(int id);
	boolean login(String username,String password);
	boolean usernameExists(String username);
	void saveUser(User user);
	User findByName(String name);

}