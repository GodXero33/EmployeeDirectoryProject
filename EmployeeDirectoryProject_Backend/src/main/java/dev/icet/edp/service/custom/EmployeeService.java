package dev.icet.edp.service.custom;

import dev.icet.edp.dto.Employee;
import dev.icet.edp.service.SuperService;
import dev.icet.edp.util.Response;

public interface EmployeeService extends SuperService<Employee> {
	Response<Boolean> isEmailExist (String email);
}
