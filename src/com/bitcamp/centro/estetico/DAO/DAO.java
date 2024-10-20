package com.bitcamp.centro.estetico.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import com.bitcamp.centro.estetico.models.Main;

public interface DAO<T>{
    
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
    
    Optional<T> get(int id);
    List<T> getAll();

    int update(int id, T obj);

    int toggle(T obj);
    int toggle(int id);

    int delete(int id);
}
