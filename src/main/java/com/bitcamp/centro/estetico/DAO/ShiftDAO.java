package com.bitcamp.centro.estetico.DAO;

import java.util.List;
import java.util.Optional;

import com.bitcamp.centro.estetico.models.Turn;

public class ShiftDAO implements DAO<Turn> {

    private ShiftDAO(){}
    private static class SingletonHelper {
        private static ShiftDAO INSTANCE = new ShiftDAO();
    }
	public static ShiftDAO getInstance() {
		return SingletonHelper.INSTANCE;
	}

    @Override
    public Optional<Turn> insert(Turn obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

    @Override
    public Optional<Turn> get(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Turn> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public int update(Long id, Turn obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int toggle(Turn obj) {
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
