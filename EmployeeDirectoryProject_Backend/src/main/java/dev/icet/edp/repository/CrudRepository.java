package dev.icet.edp.repository;

import dev.icet.edp.util.Response;

import java.util.List;

public interface CrudRepository<T> {
	Response<T> add (T entity);
	Response<T> update (T entity);
	Response<Object> delete (Long id);
	Response<T> get (Long id);
	Response<List<T>> getAll ();
}
