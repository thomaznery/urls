package br.com.urls.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.urls.modelos.Stats;
import br.com.urls.modelos.URL;
import br.com.urls.modelos.Usuario;
import br.com.urls.util.App;


/**
 *
 * @author tmichelini
 */
public class UrlDAO {

    String value;
    Connection connection;

    public UrlDAO(Connection conn) {
        this.connection = conn;
    }
    public void createDataBase(){
        String query = "create database if not exists urls_data";
        
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void createTableUrl(){
//create table urlsTeste(id int not null auto_increment primary key, hits int default 0 not null, longUrl varchar(400) not null, shorUrl varchar(30));        
        String query = "create table if not exists"
                + "urls_data("
                + "id int not null auto_increment primary key,"
                + " hits int default 0 not null,"
                + " longUrl varchar(400) not null,"
                + " shorUrl varchar(30),"
                + " user int not null default 0)";
        
        try(PreparedStatement stmt = connection.prepareStatement(query) ){
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean save(URL url) throws SQLException {
        String query = "insert into urls(longUrl,user) values(?,?)";
        if(!(ifUrlExist(url))){
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, url.getUrl());
            stmt.setInt(2, url.getIdUser());
            stmt.execute();
            
            //até o momento  do execute não temos o valor do id,
            //sendo assim, podemos deduzir o shorUtrl em uma segunda conexao
            atualizarShortUrl(url.getUrl());
            return true;

        }
       }
        addOneHist(App.managerUrl.getId(url.getUrl()));
        return false;
        
        
    }
    public void addOneHist(int id){
     
        String query = "update urls set hits = hits+1 where id= ?";
        try(PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
    }
    
    public List<URL> getList(){
        List<URL> l ;
        String query = "select * from urls ";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.executeQuery();
            l=new ArrayList<>();
            try(ResultSet rs = stmt.getResultSet()){
                while(rs.next()){
                    int hits = rs.getInt(2);
                    String longUrl = rs.getString(3);
                    String shortUrl = rs.getString(4);
                    l.add(new URL(hits,longUrl,shortUrl));
                   
                }
            }
            return l;
            
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    return null;
    }
   
    public URL getUrl(int idVal) {
        String query = "select * from urls where id = ?";
        URL url = null;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idVal);
            stmt.execute();

            try (ResultSet rs = stmt.getResultSet()) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    int hits = rs.getInt(2);
                    String longUrl = rs.getString(3);
                    url = new URL(longUrl);
                    url.setHits(hits);
                    url.setId(id);
                    url.setShortUrl();
                    return url;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return null;
    }

    public boolean ifUrlExist(URL url) {
        String query = "select * from urls where longUrl = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, url.getUrl());
            stmt.execute();

            try (ResultSet rs = stmt.getResultSet()) {
                while (rs.next()) {
                    return true;
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public int getId(String url) {
        String query = "select * from urls where longUrl = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, url);
            stmt.execute();

            try (ResultSet rs = stmt.getResultSet()) {
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public void atualizarShortUrl(String urlLong) {
        URL url = new URL(urlLong);
        url.setId(getId(urlLong));//atualiza a id com a conculta
        //feita no banco utilizando o urlLong
        
        url.setShortUrl();//adita banco com um shortUrl 
        String query = "update urls set shortUrl = ? where longUrl = ?";
        
        try(PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.setString(1, url.getShortUrl().trim());
            stmt.setString(2, url.getUrl().trim());
            stmt.execute();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<URL> getTop10Urls() {
        String query = "select * from urls order by -hits limit 10";
        List<URL> l;
        try(PreparedStatement stmt= connection.prepareStatement(query)){
            stmt.executeQuery();
            l = new ArrayList<>();
            try(ResultSet rs = stmt.getResultSet()){
                while(rs.next()){
                    URL u = new URL(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3).trim(),
                            rs.getString(4).trim()
                    );
                    l.add(u);
                }
            }
            return l;
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean deleteUrl(int id){
    	String query = "delete from urls where id = ?";
    	
    	try(PreparedStatement stmt = connection.prepareStatement(query)){
    		stmt.setInt(1, id);
    		
    		return true;
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }

    public URL getUrl(String longurl){
    	String query = "select * from urls where longUrl = ?";
    	
    	try(PreparedStatement stmt= connection.prepareStatement(query)){
    		stmt.setString(1, longurl);
    		stmt.execute();
    		try(ResultSet rs = stmt.getResultSet()){
    			while(rs.next()){
    				int id = rs.getInt(1);
    				int hits = rs.getInt(2);
    				String url = rs.getString(3);
    				String shorUrl = rs.getString(4);
    				return new URL(id,hits,url,shorUrl);
    			}
    		}
    		
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public int getHitsTotal(){
    	List<URL> list = getList();
    	int somaHits = 0;
    	for(URL u: list){
    		somaHits += u.getHits();
    	}
    	return somaHits;
    }
    
    public int countUrls(){
    	String query = "select * from urls";
    	int cont=0;
    	  try(PreparedStatement stmt= connection.prepareStatement(query)){
              stmt.executeQuery();
            
              try(ResultSet rs = stmt.getResultSet()){
                  while(rs.next()){
                	  cont++;
                  }
              }
              
          } catch (SQLException ex) {
              Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, null, ex);
          }
    	return cont;
    }
    
    public Stats gerarStats(){
    	int totalHits = 0;
    	int urlCount = 0;
    	List<URL> topUrls = getTop10Urls();
    	System.out.println("dentro do urlDAO in gerarStats()");
    	String query = "select * from urls";
    	try(PreparedStatement stmt = connection.prepareStatement(query)){
    		stmt.executeQuery();
    		try(ResultSet rs = stmt.getResultSet()){
    			while(rs.next()){
    				totalHits += rs.getInt(2);
    				urlCount++;
    			}
    			
    		}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	return new Stats(totalHits,urlCount, topUrls);
    }
}