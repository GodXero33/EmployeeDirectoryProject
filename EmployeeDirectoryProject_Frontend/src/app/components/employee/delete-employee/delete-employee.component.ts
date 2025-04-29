import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../../service/api.service';

@Component({
  selector: 'app-delete-employee',
  imports: [FormsModule],
  templateUrl: './delete-employee.component.html',
  styleUrl: './delete-employee.component.css'
})
export class DeleteEmployeeComponent {
  public id: number | null = null;
  public name: string = '';
  public email: string = '';
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
      },
      error: (error) => {
        console.error(error);
        alert('Employee not found');
      }
    });
  }

  public deleteEmployee (): void {
    if (!confirm('You sure that you want to delete this employee?')) return;

    this.apiService.delete(`/employee/${this.searchedId}`).subscribe({
      next: (res: any) => {
        this.searched = true;
        this.name = '';
        this.email = '';
        this.searchedId = null;
        this.id = null;
      },
      error: (error) => {
        console.error(error);
        alert('Employee not found');
      }
    });
  }
}
