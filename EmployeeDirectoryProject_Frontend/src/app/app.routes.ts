import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { EmployeeComponent } from './components/employee/employee.component';
import { AuthGuard } from './service/auth-gard.service';

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
		data: { adminOnly: true }
	}
];
