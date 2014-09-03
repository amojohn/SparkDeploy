package com.hansight.deploy.core.conf.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLitePersist extends AbstractConfPersist {
	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractConfPersist.class);
	protected static final String driver = "org.sqlite.JDBC";
	protected static final String url = "jdbc:sqlite:conf/config.db";
	protected static final String table = "cmbconf";

	private Connection getConn() throws SQLException {
		return DriverManager.getConnection(url);
	}

	private static void close(Connection conn) {
		if (conn == null) {
			return;
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected synchronized void loadValue() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			LOG.error("error1001:uncorrect sql driver class" + driver, e);
			System.exit(-1);
		}
		Connection conn = null;
		try {
			conn = getConn();
			Statement stmt = conn.createStatement();
			String createSQL = "create table if not exists " + table
					+ " (name text primary key, value text not null)";
			int res = stmt.executeUpdate(createSQL);
			LOG.debug("create table :{}", res);
			ResultSet rs = stmt.executeQuery("select * from " + table);
			if (rs != null) {
				String name = null, value = null;
				while (rs.next()) {
					name = rs.getString("name");
					value = rs.getString("value");
					Value val = new Value();
					val.setValue(value);
					cache.put(name, val);
					LOG.debug("read conf from sqlite:{}={}", name, value);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
	}

	public boolean insertValue(String name, Value val) throws Exception {
		Connection conn = null;
		try {
			conn = getConn();
			PreparedStatement pstmt2 = null;
			pstmt2 = conn.prepareStatement("insert into " + table
					+ "(name, value) values(?, ?)");
			pstmt2.setString(1, name);
			pstmt2.setString(2, val.getValue());
			int res = pstmt2.executeUpdate();
			LOG.debug("insert {}={} result[{}]",
					new Object[] { name, val.getValue(), res == 1 });
			return res == 1;
		} finally {
			close(conn);
		}
	}

	public boolean updateValue(String name, Value val) throws Exception {
		Connection conn = null;
		try {
			conn = getConn();
			PreparedStatement pstmt2 = null;
			pstmt2 = conn.prepareStatement("update " + table
					+ " set value=? where name=?");
			pstmt2.setString(1, val.getValue());
			pstmt2.setString(2, name);
			int res = pstmt2.executeUpdate();
			LOG.debug("update {}={} result[{}]",
					new Object[] { name, val.getValue(), res == 1 });
			return res == 1;
		} finally {
			close(conn);
		}
	}

	@Override
	public boolean deleteValue(String name) throws SQLException {
		Connection conn = null;
		try {
			conn = getConn();
			PreparedStatement pstmt2 = null;
			pstmt2 = conn.prepareStatement("delete from " + table
					+ " where name=?");
			pstmt2.setString(1, name);
			int res = pstmt2.executeUpdate();
			LOG.debug("delete {} result[{}]", new Object[] { name, res == 1 });
			return res == 1;
		} finally {
			close(conn);
		}
	}

	@Override
	public boolean setValue(String name, Value val) throws Exception {
		if (!updateValue(name, val)) {
			return insertValue(name, val);
		}
		return false;
	}
}
