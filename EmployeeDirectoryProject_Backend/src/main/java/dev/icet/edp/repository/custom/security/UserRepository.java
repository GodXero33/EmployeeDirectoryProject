package dev.icet.edp.repository.custom.security;

import dev.icet.edp.entity.security.UserEntity;
import dev.icet.edp.repository.CrudRepository;
import dev.icet.edp.util.Response;

public interface UserRepository extends CrudRepository<UserEntity> {
	Response<UserEntity> getByUserName (String name);
	Response<UserEntity> getByUserNameForAuth (String name);
	Response<Boolean> isUsernameExist (String username);
	Response<Boolean> isEmployeeExistById (Long employeeId);
	Response<Boolean> isEmployeeAlreadyUser (Long employeeId);
}
