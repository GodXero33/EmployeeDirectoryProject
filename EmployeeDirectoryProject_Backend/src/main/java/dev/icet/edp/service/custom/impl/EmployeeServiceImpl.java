package dev.icet.edp.service.custom.impl;

import dev.icet.edp.dto.Employee;
import dev.icet.edp.entity.EmployeeEntity;
import dev.icet.edp.repository.custom.EmployeeRepository;
import dev.icet.edp.service.SuperServiceHandler;
import dev.icet.edp.service.custom.EmployeeService;
import dev.icet.edp.util.Response;
import org.modelmapper.ModelMapper;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
	private final SuperServiceHandler<Employee, EmployeeEntity> serviceHandler;

	public EmployeeServiceImpl (EmployeeRepository employeeRepository, ModelMapper mapper) {
		this.serviceHandler = new SuperServiceHandler<>(employeeRepository, mapper, Employee.class, EmployeeEntity.class);
	}

	@Override
	public Response<Employee> get (Long id) {
		return this.serviceHandler.get(id);
	}

	@Override
	public Response<List<Employee>> getAll () {
		return this.serviceHandler.getAll();
	}

	@Override
	public Response<Employee> add (Employee dto) {
		return this.serviceHandler.add(dto);
	}

	@Override
	public Response<Employee> update (Employee dto) {
		return this.serviceHandler.update(dto);
	}

	@Override
	public Response<Object> delete (Long id) {
		return this.serviceHandler.delete(id);
	}
}
