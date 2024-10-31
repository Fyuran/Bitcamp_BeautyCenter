package com.bitcamp.centro.estetico.DAO;

import java.util.List;
import java.util.Optional;

import com.bitcamp.centro.estetico.models.Reservation;

public class ReservationDAO implements DAO<Reservation> {

    private ReservationDAO(){}
    private static class SingletonHelper {
        private static ReservationDAO INSTANCE = new ReservationDAO();
    }
	public static ReservationDAO getInstance() {
		return SingletonHelper.INSTANCE;
	}
    
    @Override
    public Optional<Reservation> insert(Reservation obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

    @Override
    public Optional<Reservation> get(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Reservation> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public int update(Long id, Reservation obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int toggle(Reservation obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toggle'");
    }

    @Override
    public int toggle(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toggle'");
    }

    @Override
    public int delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
