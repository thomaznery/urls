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



public class UsuarioDAO {
	private Connection connection;
	
	
	public UsuarioDAO(Connection conn){
		this.connection = conn;
	}
	
	public void createTable(){
		String query = "create table if not exists"
				+ " users(id int not null auto_increment primary key,"
				+ "nome varchar(40) not null)";
		
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.execute();			
		} catch (SQLException e) {
			Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, "Erro no createTable() usuarioDAO", e);
		}
		
	}
	
	public boolean save(Usuario u){
	
	if(!(ifUserExist(u.getNome()))){	
		String query = "insert into users(nome) values(?)";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, u.getNome());
			stmt.execute();
			return true;
		} catch (SQLException e) {
			Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, "Erro no save(Usuario u) usuarioDAO", e);
		}
	}
		return false;
	}
	
	public Usuario getUsuario(int id){
		String query = "select * from users where id = ?";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setInt(1,id);
			stmt.execute();
			try(ResultSet rs = stmt.getResultSet()){
				while(rs.next()){
					String nome = rs.getString(2);
					if(!(nome == null))return new Usuario(id, nome);
				}
			}
			
		} catch (SQLException e) {
			Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, "Erro no getUsuario(int id) usuarioDAO", e);
		}
		return null;
	}
	
	public int getId(String nome){
		String query = "select * from users where nome = ?";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, nome);
			stmt.execute();
			
			try(ResultSet rs = stmt.getResultSet()){
				while(rs.next())return rs.getInt(1);
				
			}
		}catch (SQLException e) {
			Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, "Erro no getId(Stirng nome) usuarioDAO", e);
		}
		return -1;
	}

	public boolean ifUserExist(String nome){
		String query = "select * from users where nome = ?";
		
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, nome);
			stmt.execute();
			
			try(ResultSet rs = stmt.getResultSet()){
				while(rs.next()){
					return true;
				}
			}
		} catch (SQLException e) {
			Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, "Erro no if UserExist usuarioDAO", e);
		}
		return false;
	}
	
	public boolean deleteUsuario(int id){
		String query = "delete from users where id = ?";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setInt(1, id);
			stmt.execute();
			return true;
		} catch (SQLException e) {
			Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, "Erro deletarUsuaro usuarioDAO", e);
		}
		return false;
	}
	
    public List<URL> getTop10UrlsOffUser(int idUsuario) {
        String query = "select * from urls  where user = ? order by -hits limit 10 ";
        List<URL> l;
        try(PreparedStatement stmt= connection.prepareStatement(query)){
        	stmt.setInt(1, idUsuario);
        	stmt.executeQuery();
            l = new ArrayList<>();
            try(ResultSet rs = stmt.getResultSet()){
                while(rs.next()){
                    URL u = new URL(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3).trim(),
                            rs.getString(4).trim(),
                            idUsuario
                    );
                    l.add(u);
                }
            }
            return l;
        } catch (SQLException ex) {
        	Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, "Erro getTop10offUser UsuarioDAO", ex);
        }
        return null;
    }
	
	public Stats gerarStatsUsuario(int idUsuario){
		int somaHits = 0;
		int urlCount = 0;
		List<URL> list = getTop10UrlsOffUser(idUsuario);
		boolean usuarioExiste = false;
		
		String query = "select * from urls where user = ?";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setInt(1, idUsuario);
			stmt.executeQuery();
			try(ResultSet rs = stmt.getResultSet()){
				while(rs.next()){
					 System.out.println("...");
					usuarioExiste = true;
					somaHits += rs.getInt(2);
					urlCount++;
				}
				if(usuarioExiste){
					return new Stats(somaHits,urlCount,list);
				}
			}
			
			
		} catch (SQLException e) {
			Logger.getLogger(UrlDAO.class.getName()).log(Level.SEVERE, "gerarStatusUsuario usuarioDAO", e);
		}
		
		return null;
	}
}
