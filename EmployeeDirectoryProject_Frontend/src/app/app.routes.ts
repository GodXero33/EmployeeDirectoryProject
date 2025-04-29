import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { EmployeeComponent } from './components/employee/employee.component';
import { AuthGuard } from './service/auth-gard.service';
import { AddEmployeeComponent } from './components/employee/add-employee/add-employee.component';
import { UpdateEmployeeComponent } from './components/employee/update-employee/update-employee.component';
import { DeleteEmployeeComponent } from './components/employee/delete-employee/delete-employee.component';
import { SearchEmployeeComponent } from './components/employee/search-employee/search-employee.component';
import { SearchEmployeeByIdComponent } from './components/employee/search-employee/search-employee-by-id/search-employee-by-id.component';
import { SearchAllEmployeesComponent } from './components/employee/search-employee/search-all-employees/search-all-employees.component';

export const routes: Routes = [
	{
		path: '',
		component: LoginComponent
	},
	{
		path: 'login',
		component: LoginComponent
	},
	{
		path: 'employee',
		component: EmployeeComponent,
		canActivate: [AuthGuard],
		data: { adminOnly: true },
		children: [
			{
				path: '',
				component: AddEmployeeComponent,
				canActivate: [AuthGuard]
			},
			{
				path: 'add',
				component: AddEmployeeComponent,
				canActivate: [AuthGuard]
			},
			{
				path: 'update',
				component: UpdateEmployeeComponent,
				canActivate: [AuthGuard]
			},
			{
				path: 'delete',
				component: DeleteEmployeeComponent,
				canActivate: [AuthGuard]
			},
			{
				path: 'search',
				component: SearchEmployeeComponent,
				canActivate: [AuthGuard],
				children: [
					{
						path: '',
						component: SearchEmployeeByIdComponent,
						canActivate: [AuthGuard]
					},
					{
						path: 'by-id',
						component: SearchEmployeeByIdComponent,
						canActivate: [AuthGuard]
					},
					{
						path: 'all',
						component: SearchAllEmployeesComponent,
						canActivate: [AuthGuard]
					}
				]
			}
		]
	}
];
