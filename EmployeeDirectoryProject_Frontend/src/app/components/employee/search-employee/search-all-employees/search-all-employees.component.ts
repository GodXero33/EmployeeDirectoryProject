import { Component, OnInit } from '@angular/core';
import { Employee } from '../../../../model/employee.model';
import { ApiService } from '../../../../service/api.service';

@Component({
  selector: 'app-search-all-employees',
  imports: [],
  templateUrl: './search-all-employees.component.html',
  styleUrl: './search-all-employees.component.css'
})
export class SearchAllEmployeesComponent implements OnInit {
  public employees: Array<Employee> = [];
  public displayEmployees: Array<Employee> = [];
  public currentPageIndex: number = 0;
  public maximumPages: number = 1;
  private employeesCountPerPage: number = 20;

  constructor (private apiService: ApiService) {}
  
  public ngOnInit(): void {
    this.refresh();
  }

  public refresh (): void {
    this.apiService.get('/employee/all').subscribe({
      next: (res: any) => {
        this.employees.length = 0;

        res.forEach((employee: any) => {
          this.employees.push(Employee.builder()
            .id(employee.id)
            .name(employee.name)
            .email(employee.email)
            .department(employee.department[0].toUpperCase() + employee.department.substring(1).toLowerCase())
            .createdAt(employee.createdAt)
            .updatedAt(employee.updatedAt)
            .build());
        });

        this.updateDisplayEmployees();
      },
      error: (error) => {
        console.error(error);
        alert('Failed to load employees');
      }
    });
  }

  private updateDisplayEmployees (): void {
    const startIndex = this.currentPageIndex * this.employeesCountPerPage;
    this.maximumPages = Math.floor(this.employees.length / this.employeesCountPerPage);

    this.displayEmployees = this.employees.slice(startIndex, startIndex + this.employeesCountPerPage);
  }

  public nextPage (): void {
    this.currentPageIndex++;

    if (this.currentPageIndex > this.maximumPages) this.currentPageIndex = this.maximumPages;

    this.updateDisplayEmployees();
  }

  public prevPage (): void {
    this.currentPageIndex--;

    if (this.currentPageIndex < 0) this.currentPageIndex = 0;

    this.updateDisplayEmployees();
  }
}
