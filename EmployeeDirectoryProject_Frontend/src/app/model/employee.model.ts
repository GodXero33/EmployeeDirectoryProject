class EmployeeBuilder {
	private employee: Employee;

	constructor (employee: Employee) {
		this.employee = employee;
	}

	public id (id: number): EmployeeBuilder {
		this.employee.id = id;
		return this;
	}

	public name (name: string): EmployeeBuilder {
		this.employee.name = name;
		return this;
	}

	public email (email: string): EmployeeBuilder {
		this.employee.email = email;
		return this;
	}

	public department (department: string): EmployeeBuilder {
		this.employee.department = department;
		return this;
	}

	public createdAt (createdAt: string): EmployeeBuilder {
		this.employee.createdAt = createdAt;
		return this;
	}

	public updatedAt (updatedAt: string): EmployeeBuilder {
		this.employee.updatedAt = updatedAt;
		return this;
	}

	public build () {
		return this.employee;
	}
}

export class Employee {
	public id: number | null = null;
	public name: string | null = null;
	public email: string | null = null;
	public department: string | null = null;
	public createdAt: string | null = null;
	public updatedAt: string | null = null;

	public static builder () {
		return new EmployeeBuilder(new Employee());
	}
}
