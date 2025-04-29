import { Component, OnDestroy } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { filter, Subscription } from 'rxjs';

@Component({
  selector: 'app-employee',
  imports: [RouterOutlet],
  templateUrl: './employee.component.html',
  styleUrl: './employee.component.css'
})
export class EmployeeComponent implements OnDestroy {
  public activeContent: string = '';
  private routeSub!: Subscription;

  constructor (private router: Router) {
    this.routeSub = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      const currentUrl = this.router.url;
      const segments = currentUrl.split('/').filter(Boolean);

      this.activeContent = segments.length > 1 ? segments[1] : '';
    });
  }

  public ngOnDestroy (): void {
    if (this.routeSub) this.routeSub.unsubscribe();
  }

  public navigateTo (url: string) {
    this.router.navigate([`/employee/${url}`]);
  }
}
