package dev.icet.edp.controller;

import dev.icet.edp.dto.Employee;
import dev.icet.edp.service.custom.EmployeeService;
import dev.icet.edp.util.ControllerResponseUtil;
import dev.icet.edp.util.CustomHttpResponse;
import dev.icet.edp.util.Response;
import dev.icet.edp.util.enums.ResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
@PreAuthorize("hasAuthority('ADMIN')")
public class EmployeeController {
	private final EmployeeService employeeService;
	private final ControllerResponseUtil controllerResponseUtil;

	@GetMapping("/{id}")
	public CustomHttpResponse<Employee> get (@PathVariable("id") Long id) {
		if (id <= 0) return this.controllerResponseUtil.getInvalidDetailsResponse("Id can't be negative");

		final Response<Employee> response = this.employeeService.get(id);

		return switch (response.getStatus()) {
			case FOUND -> new CustomHttpResponse<>(HttpStatus.OK, response.getData(), "Employee found");
			case SERVER_ERROR -> this.controllerResponseUtil.getServerErrorResponse();
			default -> new CustomHttpResponse<>(HttpStatus.NOT_FOUND, null, "Employee not found");
		};
	}

	@PostMapping("")
	public CustomHttpResponse<Employee> add (@RequestBody Employee employee, BindingResult result) {
		if (result.hasErrors()) return this.controllerResponseUtil.getInvalidDetailsResponse(result);
		if (employee.getId() != null) return this.controllerResponseUtil.getInvalidDetailsResponse("New employee can't have a ID");

		final Response<Employee> response = this.employeeService.add(employee);

		return response.getStatus() == ResponseType.CREATED ?
			new CustomHttpResponse<>(HttpStatus.OK, response.getData(), "Employee created") :
			this.controllerResponseUtil.getServerErrorResponse();
	}
}
