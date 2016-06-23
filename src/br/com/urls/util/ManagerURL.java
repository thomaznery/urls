package br.com.urls.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.com.urls.jdbc.ConnectionPool;
import br.com.urls.modelos.Stats;
import br.com.urls.modelos.URL;
import br.com.urls.dao.UrlDAO;
import br.com.urls.dao.UsuarioDAO;

public class ManagerURL {
    public ManagerURL(){
        
       
    }        
   public void createTable(){
       try(Connection conn = new ConnectionPool().getConnection()){
           new UrlDAO(conn).createTableUrl();
       } catch (SQLException ex) {
            Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, "Erro createTable Manager", ex);
        }
   }
           
   public void createDataBase(){
       try(Connection conn = new ConnectionPool().getConnection()){
           new UrlDAO(conn).createDataBase();
       } catch (SQLException ex) {
            Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, "Erro createDataBase Manager", ex);
        }
   } 
   
   public boolean save(URL url){
       try(Connection conn = new ConnectionPool().getConnection()){
            return new UrlDAO(conn).save(url);    
       } catch (SQLException ex) {
            Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, 
                    "Erro save URL Manager.", ex);
        }
       return false;
    }
    
   
   public void addOneHits(int id){
       try(Connection conn = new ConnectionPool().getConnection()){
           new UrlDAO(conn).addOneHist(id);
       } catch (SQLException ex) {
            Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, "ERRO:/ AddOneHits", ex);
        }
   }
   public URL getUrl(int id){
        try(Connection conn = new ConnectionPool().getConnection()){
            return new UrlDAO(conn).getUrl(id);
        }   catch (SQLException ex) {
                Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, "ERRO:/ getUrl(int id)", ex);
            }
    return null;
    }
    
    public int getId(String url){
        try(Connection conn = new ConnectionPool().getConnection()){
            return new UrlDAO(conn).getId(url);
        } catch (SQLException ex) {
                Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, "ERRO:/ getId()", ex);
            }
        return -1;
    }
    
    public boolean ifExist(URL url){
        try(Connection conn = new ConnectionPool().getConnection()){
            if(new UrlDAO(conn).ifUrlExist(url)) return true;
        } catch (SQLException ex) {
        	 Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, "ERRO:/ ifExist", ex);
        }
        return false;
    }

    public void atualizarShortUrl(String url){
        try(Connection conn = new ConnectionPool().getConnection()){
            new UrlDAO(conn).atualizarShortUrl(url);
        } catch (SQLException ex) {
        	 Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, "ERRO:/ atualizarShorUrl", ex);
        }
    }
    
    public List<URL> getList(){
        try(Connection conn = new ConnectionPool().getConnection()){
            return new UrlDAO(conn).getList();
        } catch (SQLException ex) {
            Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, "Erro: getList()", ex);
        }
        
        return null;
    }
    
    public List<URL> getTop10Urls(){
        try(Connection conn = new ConnectionPool().getConnection()){
            return new UrlDAO(conn).getTop10Urls();
        } catch (SQLException ex) {
            Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, "ERRO: getTop10Urls()", ex);
        }
        
        return null;        
    }
    
    public boolean deleteUrl(int id){
    	try(Connection conn = new ConnectionPool().getConnection()){
    		return new UrlDAO(conn).deleteUrl(id);
    	} catch (SQLException e) {
    		 Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, "ERRO:/ deleteUrl", e);
		}
    	return false;
    }
    
    public URL getUrl(String longUrl){
    	try(Connection conn = new ConnectionPool().getConnection()){
    		return new UrlDAO(conn).getUrl(longUrl);
    	} catch (SQLException e) {
    		 Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, "ERRO:/ getUrk(String longUrl)", e);
		}
    	return null;
    }
   
    public Stats gerarStats(){
    	Stats stats = null;
    	try(Connection conn = new ConnectionPool().getConnection()){
    		stats =  new UrlDAO(conn).gerarStats();
    	} catch (SQLException e) {
    		 Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, "ERRO:/ gerarStats()", e);
		}
    	return stats; 
    }
}