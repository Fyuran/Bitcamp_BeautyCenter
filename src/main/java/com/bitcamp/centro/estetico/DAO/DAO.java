package com.bitcamp.centro.estetico.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import com.bitcamp.centro.estetico.models.Main;
import com.bitcamp.centro.estetico.models.Model;

public interface DAO<T extends Model>{
    
    default Connection getConnection() {
        try {
            Connection conn = Main.getConnection();
            return conn;
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Errore impossibile connettersi al database ", "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    Optional<T> insert(T obj);
    
    boolean isEmpty();
    
    Optional<T> get(Long id);
    List<T> getAll();

    int update(Long id, T obj);

    int toggle(T obj);
    int toggle(Long id);

    int delete(Long id);
}
