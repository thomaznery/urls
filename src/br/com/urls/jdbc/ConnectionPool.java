package br.com.urls.jdbc;

import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
 
public class ConnectionPool {
    private final MysqlDataSource dataSource;
    
    public ConnectionPool(){
        MysqlDataSource pool = new MysqlDataSource();
        pool.setURL(PropertyConnection.URL.getValue());
        pool.setUser(PropertyConnection.USERNAME.getValue());
        pool.setPassword(PropertyConnection.PASSWORD.getValue());
        this.dataSource = pool;
    }
    
    public java.sql.Connection getConnection() throws SQLException{
        return dataSource.getConnection();
    }
}
