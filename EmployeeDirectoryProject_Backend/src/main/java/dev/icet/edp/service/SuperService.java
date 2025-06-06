package dev.icet.edp.service;

import dev.icet.edp.util.Response;

import java.util.List;

public interface SuperService<D> {
	Response<D> get (Long id);
	Response<List<D>> getAll ();
	Response<D> add (D dto);
	Response<D> update (D dto);
	Response<Object> delete (Long id);
}
