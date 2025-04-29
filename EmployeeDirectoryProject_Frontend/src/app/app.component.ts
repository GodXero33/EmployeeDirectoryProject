import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { AuthService } from './service/auth.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'EmployeeDirectoryProject_Frontend';

  constructor (private authService: AuthService, private router: Router) {
     this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        if (this.authService.isAuthenticated()) {
          this.router.navigate(['/employee']);
        } else {
          this.router.navigate(['/login']);
        }
      }
    });
  }
}
