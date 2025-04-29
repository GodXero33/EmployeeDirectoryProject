import { Component, OnDestroy, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { filter, Subscription } from 'rxjs';

@Component({
  selector: 'app-search-employee',
  imports: [RouterOutlet],
  templateUrl: './search-employee.component.html',
  styleUrl: './search-employee.component.css'
})
export class SearchEmployeeComponent implements OnDestroy, OnInit {
  public activeContent: string = '';
  private routeSub!: Subscription;

  constructor (private router: Router) {}

  public ngOnInit(): void {
    this.routeSub = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      const currentUrl = this.router.url;
      const segments = currentUrl.split('/').filter(Boolean);

      this.activeContent = segments.length > 2 ? segments[2] : '';
    });
  }

  public ngOnDestroy (): void {
    if (this.routeSub) this.routeSub.unsubscribe();
  }

  public navigateTo (url: string) {
    this.router.navigate([`/employee/search/${url}`]);
  }
}
