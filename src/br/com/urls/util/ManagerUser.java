package br.com.urls.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.urls.dao.UsuarioDAO;
import br.com.urls.jdbc.ConnectionPool;
import br.com.urls.modelos.Stats;
import br.com.urls.modelos.Usuario;

public class ManagerUser {
	
	 public void createTable(){
	       try(Connection conn = new ConnectionPool().getConnection()){
	           new UsuarioDAO(conn).createTable();
	       } catch (SQLException ex) {
	            Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, null, ex);
	        }
	   }
	 
	 public boolean save(Usuario usuario ){
	       try(Connection conn = new ConnectionPool().getConnection()){
	            return new UsuarioDAO(conn).save(usuario);     
	       } catch (SQLException ex) {
	            Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, 
	                    "Erro ao salvar Url no banco.", ex);
	        }
	       return false;
	    }
	 
	 
	 public Usuario getUsuario(int id){
		  try(Connection conn = new ConnectionPool().getConnection()){
			 return new UsuarioDAO(conn).getUsuario(id); 
		  }catch (SQLException ex) {
	            Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, 
	                    "Erro getusuario(int id).", ex);
	        }
		 return null;
		
	 }
	 
	 public int getId(String nome){
		 try(Connection conn = new ConnectionPool().getConnection()){
			 return new UsuarioDAO(conn).getId(nome);
		 }catch (SQLException ex) {
	            Logger.getLogger(ManagerURL.class.getName()).log(Level.SEVERE, 
	                    "Erro getId(String user).", ex);
	        }
		 return -1;
	 }
	 
	 public boolean deleteUsuario(int id){
	    	try(Connection conn = new ConnectionPool().getConnection()){
	    		return new UsuarioDAO(conn).deleteUsuario(id);
	    	} catch (SQLException e) {
				e.printStackTrace();
			}
	    	return false;
	 }
	 
	 public Stats getStatsOffUsuario(int idUsuario){
		 try(Connection conn = new ConnectionPool().getConnection()){
			 return new UsuarioDAO(conn).gerarStatsUsuario(idUsuario);
		 }catch (SQLException e) {
				e.printStackTrace();
			}
		 return null;
	 }
}
