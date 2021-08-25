package model;
import java.sql.*;
public class UserRepository {
	public static boolean checkUser(Users u) {
		boolean result =true;
		try {
			Connection con=DatabaseConnection.getConnection();
			PreparedStatement stm =con.prepareStatement("select * from user_tbl where name=? and password=?");
			stm.setString(1, u.getName());
			stm.setString(2, u.getPassword());
			ResultSet rs = stm.executeQuery();
			result= rs.next()?true:false;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e1) {
            e1.printStackTrace();
		}
		return result;
	}

//	checkAdimin


}
