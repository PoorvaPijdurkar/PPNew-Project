import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute } from './layouts/error/error.route';
import { navbarRoute } from './layouts/navbar/navbar.route';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { Authority } from 'app/config/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          data: {
            authorities: [Authority.ADMIN],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
        },
        {
          path: 'account',
          loadChildren: () => import('./account/account.module').then(m => m.AccountModule),
        },
        {
          path: 'login',
          loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
        },
        {
          path: 'sku',
          loadChildren: () => import(`./sku/sku.module`).then(m => m.SkuModule),
        },
        {
          path: 'department',
          loadChildren: () => import(`./department/dept.module`).then(m => m.DeptModule),
        },
        {
          path: 'supplier',
          loadChildren: () => import(`./supplier-details/supplier-details.module`).then(m => m.SupplierDetailsModule),
        },
        {
          path: 'order',
          loadChildren: () => import(`./order/order.module`).then(m => m.OrderModule),
        },
        {
          path: 'orderAssignment',
          loadChildren: () => import(`./order-assignment/order-assignment.module`).then(m => m.OrderAssignmentModule),
        },
        {
          path: 'orderPurchase',
          loadChildren: () => import(`./order-purchased/order-purchased.module`).then(m => m.OrderPurchasedModule),
        },
        {
          path: '',
          loadChildren: () => import(`./entities/entity-routing.module`).then(m => m.EntityRoutingModule),
        },
        {
          path: '',
          loadChildren: () => import(`./dashboard/dashboard.module`).then(m => m.DashboardModule),
        },
        {
          path: 'bom',
          loadChildren: () => import(`./bom/bom.module`).then(m => m.BomModule),
        },
        {
          path: '',
          loadChildren: () => import(`./orders-summary/orders-summary.module`).then(m => m.OrdersSummaryModule),
        },
        {
          path: 'production',
          loadChildren: () => import(`./production/production.module`).then(m => m.ProductionModule),
        },
        {
          path: 'customer',
          loadChildren: () => import(`./Customer/customer.module`).then(m => m.CustomerModule),
        },
        {
          path: 'lead-source',
          loadChildren: () => import(`./lead-source/lead-source.module`).then(m => m.LeadSourceModule),
        },
        {
          path: 'sales-order',
          loadChildren: () => import(`./sales-order/sales-order.module`).then(m => m.SalesOrderModule),
        },
        navbarRoute,
        ...errorRoute,
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    ),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
