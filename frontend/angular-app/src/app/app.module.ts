import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { ProductService } from './services/product.service';
import { ProductListComponent } from './components/product-list/product-list.component';
import { Routes, RouterModule } from '@angular/router';
import { ProductCategoryMenuComponent } from './components/product-category-menu/product-category-menu.component';
import { SearchComponent } from './components/search/search.component';
import { CartDetailsComponent } from './components/cart-details/cart-details.component';
import { CartStatusComponent } from './components/cart-status/cart-status.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CheckoutService } from './services/checkout.service';
import { OrderReportComponent } from './components/order-report/order-report.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './services/auth.guard';
import { RegisterComponent } from './components/register/register.component';




const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'checkout', component: CheckoutComponent, canMatch: [AuthGuard] },
  { path: 'cart-details', component: CartDetailsComponent, canMatch: [AuthGuard] },
  { path: 'products/:id', component: ProductDetailsComponent, canMatch: [AuthGuard] },
  { path: 'search/:keyword', component: ProductListComponent, canMatch: [AuthGuard] },
  { path: 'category/:id', component: ProductListComponent, canMatch: [AuthGuard] },
  { path: 'category', component: ProductListComponent, canMatch: [AuthGuard] },
  { path: 'products', component: ProductListComponent, canMatch: [AuthGuard] },
  { path: 'order-reports', component: OrderReportComponent, canMatch: [AuthGuard] },

  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  declarations: [
    AppComponent,
    ProductListComponent,
    ProductCategoryMenuComponent,
    SearchComponent,
    ProductDetailsComponent,
    CartStatusComponent,
    CartDetailsComponent,
    CheckoutComponent,
    OrderReportComponent,
    LoginComponent,
    RegisterComponent,
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    NgbModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [ProductService, CheckoutService, AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }