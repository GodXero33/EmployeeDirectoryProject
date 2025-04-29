package dev.icet.edp.repository.custom.impl;

import dev.icet.edp.entity.EmployeeEntity;
import dev.icet.edp.repository.custom.EmployeeRepository;
import dev.icet.edp.util.CrudUtil;
import dev.icet.edp.util.DateTimeUtil;
import dev.icet.edp.util.Response;
import dev.icet.edp.util.enums.EmployeeDepartment;
import dev.icet.edp.util.enums.ResponseType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {
	private final CrudUtil crudUtil;
	private final Logger logger;

	@Override
	public Response<EmployeeEntity> add (EmployeeEntity entity) {
		try {
			final Long generatedId = this.crudUtil.executeWithGeneratedKeys(
			"""
				INSERT INTO employee (name, email, department)
				VALUES (?, ?, ?)
				""",
				entity.getName(),
				entity.getEmail(),
				entity.getDepartment().name()
			);

			entity.setCreatedAt(DateTimeUtil.parseDateTime(DateTimeUtil.getCurrentDateTime()));
			entity.setId(generatedId);

			return new Response<>(entity, ResponseType.CREATED);
		} catch (SQLException exception) {
			this.logger.error(exception.getMessage());
			return new Response<>(null, ResponseType.SERVER_ERROR);
		}
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
		try (final ResultSet resultSet = this.crudUtil.execute("""
			SELECT id, name, email, department, created_at, updated_at
			FROM employee
			WHERE is_deleted = FALSE AND id = ?
			""", id)) {
			return resultSet.next() ?
				new Response<>(EmployeeEntity.builder()
					.id(resultSet.getLong(1))
					.name(resultSet.getString(2))
					.email(resultSet.getString(3))
					.department(EmployeeDepartment.fromName(resultSet.getString(4)))
					.createdAt(DateTimeUtil.parseDateTime(resultSet.getString(5)))
					.updatedAt(DateTimeUtil.parseDateTime(resultSet.getString(6)))
					.build(), ResponseType.FOUND) :
				new Response<>(null, ResponseType.NOT_FOUND);
		} catch (SQLException exception) {
			this.logger.error(exception.getMessage());
			return new Response<>(null, ResponseType.SERVER_ERROR);
		}
	}

	@Override
	public Response<List<EmployeeEntity>> getAll () {
		return null;
	}
}
