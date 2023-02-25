package daos;

import java.util.Vector;

public interface DaoInterface<T> {

	ThongBao create(T duLieu) throws Exception;

	ThongBao update(int id, T duLieu) throws Exception;

	ThongBao delete(int id) throws Exception;

	T findOne(int id) throws Exception;

	Vector<T> findAll() throws Exception;


	Vector<T> findBy(String key, String data) throws Exception;

}
