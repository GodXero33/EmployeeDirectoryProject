import { Component, ElementRef, ViewChild } from '@angular/core';
import { ApiService } from '../../../../service/api.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-search-employee-by-id',
  imports: [FormsModule],
  templateUrl: './search-employee-by-id.component.html',
  styleUrl: './search-employee-by-id.component.css'
})
export class SearchEmployeeByIdComponent {
  public id: number | null = null;
  public name: string = '';
  public email: string = '';
  public department: string = '';
  public searched: boolean = false;

  constructor (private apiService: ApiService) {}

  public searchEmployee (): void {
    this.apiService.get(`/employee/${this.id}`).subscribe({
      next: (res: any) => {
        this.searched = true;
        this.name = res.name;
        this.email = res.email;
        this.department = res.department[0].toUpperCase() + res.department.substring(1).toLowerCase();
      },
      error: (error) => {
        console.error(error);
        alert('Employee not found');
      }
    });
  }
}
