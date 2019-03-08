package com.test.scheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author inexture
 *
 */
public class CronExp {

	/**
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static JobBean CronExpression() throws SQLException {
		// initialize Bean for set-get cron expression
		JobBean bean = new JobBean();
		Connection con = null;
		try {
			String expression = null;

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TaskDB", "root", "root");
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT delay FROM task_table");
			while (rs.next()) {
				expression = rs.getString("delay");
			}
			// set cron expression in setDelay Method
			bean.setDelay(expression);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			con.close();
		}
		return bean;
	}
}
