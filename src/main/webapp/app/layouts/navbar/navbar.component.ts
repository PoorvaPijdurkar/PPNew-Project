import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { VERSION } from 'app/app.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import { faBuilding, faTruck, faShoppingCart } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  sidenavOpened = true;
  isLoggedIn = false;
  faBuilding = faBuilding;
  faTruck = faTruck;
  faShoppingCart = faShoppingCart;
  inProduction?: boolean;
  isNavbarCollapsed = true;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  selectedRoute!: string;
  entitiesNavbarItems: any[] = [];

  isSignInVisible = true;
  isRegisterVisible = true;
  isSettingsVisible = false;
  isPasswordVisible = false;
  isLogoutVisible = false;

  // user: string | undefined;
  user: any;
  constructor(
    private loginService: LoginService,
    public accountService: AccountService,
    private profileService: ProfileService,

    private router: Router
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }

  ngOnInit(): void {
    this.entitiesNavbarItems = EntityNavbarItems;
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.openAPIEnabled = profileInfo.openAPIEnabled;
    });

    this.accountService.getAuthenticationState().subscribe(account => {
      console.log('Account :: ', this.account);
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
      this.account = account;
      this.user = this.account?.firstName + '-' + this.account?.lastName;
    });
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }
  toggleSidenav() {
    this.sidenavOpened = !this.sidenavOpened;
  }
  selectRoute(route: string): void {
    this.selectedRoute = route;
  }
}
