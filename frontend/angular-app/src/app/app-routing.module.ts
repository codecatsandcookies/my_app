import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrderReportComponent } from './components/order-report/order-report.component';
import { LoginComponent } from './components/login/login.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { CartDetailsComponent } from './components/cart-details/cart-details.component';
import { AuthGuard } from './services/auth.guard';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { RegisterComponent } from './components/register/register.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'checkout', component: CheckoutComponent, canMatch: [AuthGuard] },
  { path: 'cart-details', component: CartDetailsComponent, canMatch: [AuthGuard] },
  { path: 'products/:id', component: ProductDetailsComponent, canMatch: [AuthGuard] },
  { path: 'products', component: ProductListComponent, canMatch: [AuthGuard] },
  { path: 'order-reports', component: OrderReportComponent, canMatch: [AuthGuard] },

  // Redirect based on login status
  { path: '', redirectTo: localStorage.getItem('authToken') ? '/products' : '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
