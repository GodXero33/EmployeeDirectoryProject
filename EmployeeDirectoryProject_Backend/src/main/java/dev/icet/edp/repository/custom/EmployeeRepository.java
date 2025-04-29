package dev.icet.edp.repository.custom;

import dev.icet.edp.entity.EmployeeEntity;
import dev.icet.edp.repository.CrudRepository;
import dev.icet.edp.util.Response;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity> {
	Response<Boolean> isEmailExist (String email, Long employeeId);
}
