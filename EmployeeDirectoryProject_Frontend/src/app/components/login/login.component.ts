import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../service/api.service';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  public username: string  = 'god_xero';
  public password: string  = '1234';

  @ViewChild('usernameField') usernameField!: ElementRef;
  @ViewChild('passwordField') passwordField!: ElementRef;

  constructor (private apiService: ApiService, private router: Router, private authService: AuthService) {}

  public login (): void {
    if (!this.usernameField || !this.passwordField) return;

    const usernameInput = this.usernameField.nativeElement as HTMLInputElement;
    const passwordInput = this.passwordField.nativeElement as HTMLInputElement;

    if (!/^[a-zA-Z0-9_]{3,15}$/.test(this.username)) {
      usernameInput.setCustomValidity("Username must be 3–15 characters long and can only contain letters, numbers, and underscores.");
      usernameInput.reportValidity();
      return;
    } else {
      usernameInput.setCustomValidity("");
    }

    if (this.password.length < 4) {
      passwordInput.setCustomValidity("Password must be at least 4 characters long.");
      passwordInput.reportValidity();
    } else {
      passwordInput.setCustomValidity("");
    }

    this.apiService.post('/user/login', {
      username: this.username,
      password: this.password
    }).subscribe({
      next: (res: any) => {
        this.authService.setToken(res.token);

        this.username = '';
        this.password = '';

        this.router.navigate(['/employee']);
      },
      error: (error) => {
        console.error(error);
      }
    });
  }
}
