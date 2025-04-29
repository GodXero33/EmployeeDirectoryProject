import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../../service/api.service';
import { Employee } from '../../../model/employee.model';

@Component({
  selector: 'app-add-employee',
  imports: [FormsModule],
  templateUrl: './add-employee.component.html',
  styleUrl: './add-employee.component.css'
})
export class AddEmployeeComponent {
  public name: string = '';
  public email: string = '';
  public department: string = 'HR';

  @ViewChild('nameField') nameField!: ElementRef;
  @ViewChild('emailField') emailField!: ElementRef;

  constructor (private apiService: ApiService) {}

  public addEmployee (): void {
    if (!this.nameField || !this.emailField) return;

    const nameInput = this.nameField.nativeElement as HTMLInputElement;
    const emailInput = this.emailField.nativeElement as HTMLInputElement;

    if (!/^[A-Za-z ]{1,100}$/.test(this.name)) {
      nameInput.setCustomValidity("Name must be 1–100 characters long and contain only letters and spaces.");
      nameInput.reportValidity();
      return;
    } else {
      nameInput.setCustomValidity("");
    }

    if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(this.email)) {
      emailInput.setCustomValidity("Please enter a valid email address (e.g., user@example.com).");
      emailInput.reportValidity();
      return;
    } else {
      emailInput.setCustomValidity("");
    }

    const newEmployee = Employee.builder()
      .name(this.name)
      .email(this.email)
      .department(this.department)
      .build();

    this.apiService.post('/employee', newEmployee).subscribe({
      next: (res: any) => {
        this.name = '';
        this.email = '';
        this.department = 'HR';

        alert(`New employee added successfully, new employee's id is ${res.id}`);
      },
      error: (error) => {
        console.error(error);
        alert(`Failed to add new Employee. try again: \n ${error}`);
      }
    });
  }
}
