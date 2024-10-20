package com.bitcamp.centro.estetico.DAO;

import java.util.List;
import java.util.Optional;

import com.bitcamp.centro.estetico.models.Shift;

public class ShiftDAO implements DAO<Shift> {

    private ShiftDAO(){}
    private static class SingletonHelper {
        private static ShiftDAO INSTANCE = new ShiftDAO();
    }
	public static ShiftDAO getInstance() {
		return SingletonHelper.INSTANCE;
	}

    @Override
    public Optional<Shift> insert(Shift obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

    @Override
    public Optional<Shift> get(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Shift> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public int update(int id, Shift obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int toggle(Shift obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toggle'");
    }

    @Override
    public int toggle(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toggle'");
    }

    @Override
    public int delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
