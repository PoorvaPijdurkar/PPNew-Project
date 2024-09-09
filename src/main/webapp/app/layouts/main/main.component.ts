import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRouteSnapshot, NavigationEnd } from '@angular/router';
import { faBuilding, faTruck, faShoppingCart } from '@fortawesome/free-solid-svg-icons';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { Account } from 'app/core/auth/account.model';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import { ProfileService } from 'app/layouts/profiles/profile.service';

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html',
})
export class MainComponent implements OnInit {
  account: Account | null = null;
  isHovered = false;
  isLoggedIn = false;
  showMenu = false;
  sidenavOpened = true;
  faBuilding = faBuilding;
  faTruck = faTruck;
  faShoppingCart = faShoppingCart;
  isExpanded = false;
  entitiesNavbarItems: any[] = [];
  inProduction?: boolean;
  openAPIEnabled?: boolean;
  selectedRoute!: string;
  isSignInVisible = true;
  isRegisterVisible = true;
  isSettingsVisible = false;
  isPasswordVisible = false;
  isLogoutVisible = false;

  constructor(
    private profileService: ProfileService,
    private loginService: LoginService,
    public accountService: AccountService,
    private titleService: Title,
    private router: Router
  ) {}

  toggleSidenav(): void {
    this.sidenavOpened = !this.sidenavOpened;
  }
  login(): void {
    this.router.navigate(['/login']);
    this.isLoggedIn = true;
  }

  logout(): void {
    this.loginService.logout();
    this.router.navigate(['']);
    this.isLoggedIn = false;
  }
  selectRoute(route: string): void {
    this.selectedRoute = route;
  }

  ngOnInit(): void {
    this.entitiesNavbarItems = EntityNavbarItems;
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.openAPIEnabled = profileInfo.openAPIEnabled;
    });

    // try to log in automatically
    this.accountService.identity().subscribe();
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateTitle();
      }
    });

    this.accountService.getAuthenticationState().subscribe(account => {
      if (account) {
        this.isLoggedIn = true;
        this.isSignInVisible = false;
        this.isRegisterVisible = false;
        this.isSettingsVisible = true;
        this.isPasswordVisible = true;
        this.isLogoutVisible = true;
      } else {
        this.isLoggedIn = false;
        this.isSignInVisible = true;
        this.isRegisterVisible = true;
        this.isSettingsVisible = false;
        this.isPasswordVisible = false;
        this.isLogoutVisible = false;
      }
    });
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot): string {
    const title: string = routeSnapshot.data['pageTitle'] ?? '';
    if (routeSnapshot.firstChild) {
      return this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  private updateTitle(): void {
    let pageTitle = this.getPageTitle(this.router.routerState.snapshot.root);
    if (!pageTitle) {
      pageTitle = 'Procure';
    }
    this.titleService.setTitle(pageTitle);
  }
}
