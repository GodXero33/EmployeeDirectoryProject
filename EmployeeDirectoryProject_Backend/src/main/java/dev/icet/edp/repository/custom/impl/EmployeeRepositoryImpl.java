package dev.icet.edp.repository.custom.impl;

import dev.icet.edp.entity.EmployeeEntity;
import dev.icet.edp.repository.custom.EmployeeRepository;
import dev.icet.edp.util.Response;

import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {
	@Override
	public Response<EmployeeEntity> add (EmployeeEntity entity) {
		return null;
	}

	@Override
	public Response<EmployeeEntity> update (EmployeeEntity entity) {
		return null;
	}

	@Override
	public Response<Object> delete (Long id) {
		return null;
	}

	@Override
	public Response<EmployeeEntity> get (Long id) {
		return null;
	}

	@Override
	public Response<List<EmployeeEntity>> getAll () {
		return null;
	}
}
