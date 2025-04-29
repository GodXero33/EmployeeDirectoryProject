import { Component, ElementRef, ViewChild } from '@angular/core';
import { ApiService } from '../../../service/api.service';
import { Employee } from '../../../model/employee.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-update-employee',
  imports: [FormsModule],
  templateUrl: './update-employee.component.html',
  styleUrl: './update-employee.component.css'
})
export class UpdateEmployeeComponent {
  public id: number | null = null;
  public name: string = '';
  public email: string = '';
  public department: string = 'HR';
  public searched: boolean = false;
  private searchedId: number | null = null;

  @ViewChild('idField') idField!: ElementRef;
  @ViewChild('nameField') nameField!: ElementRef;
  @ViewChild('emailField') emailField!: ElementRef;

  constructor (private apiService: ApiService) {}

  public searchEmployee (): void {
    this.searchedId = this.id;

    this.apiService.get(`/employee/${this.id}`).subscribe({
      next: (res: any) => {
        this.searched = true;
        this.name = res.name;
        this.email = res.email;
        this.department = res.department;
      },
      error: (error) => {
        console.error(error);
        alert('Employee not found');
      }
    });
  }

  public updateEmployee (): void {
    if (!this.nameField || !this.emailField || this.searchedId == null) return;

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
      .id(this.searchedId)
      .name(this.name)
      .email(this.email)
      .department(this.department)
      .build();

    this.apiService.put('/employee', newEmployee).subscribe({
      next: (res: any) => {
        this.name = '';
        this.email = '';
        this.department = 'HR';
        this.searched = false;
        this.searchedId = null;
        this.id = null;

        alert('Employee update successful');
      },
      error: (error) => {
        console.error(error);
        alert(`Failed to update Employee. try again: \n ${error}`);
      }
    });
  }
}
