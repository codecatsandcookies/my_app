// // import { TestBed } from '@angular/core/testing';
// // import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterModule, RouterStateSnapshot } from '@angular/router';

// // import { authGuard } from './auth.guard';
// // import { RouterTestingModule } from '@angular/router/testing';

// // // describe('authGuard', () => {
// // //   const executeGuard: CanActivateFn = (...guardParameters) => 
// // //       TestBed.runInInjectionContext(() => authGuard(...guardParameters));

// // //   beforeEach(() => {
// // //     TestBed.configureTestingModule({});
// // //   });

// // //   it('should be created', () => {
// // //     expect(executeGuard).toBeTruthy();
// // //   });
// // // });

// // describe('AuthGuard', () => {
// //   let router: Router;

// //   beforeEach(() => {
// //     TestBed.configureTestingModule({
// //       imports: [RouterModule.forRoot],
// //     });

// //     router = TestBed.inject(Router);
// //   });

// //   it('should allow access when authenticated', () => {
// //     localStorage.setItem('authToken', 'dummy-token');
    
// //     const result = authGuard({} as ActivatedRouteSnapshot, {} as RouterStateSnapshot);
// //     expect(result).toBeTrue();
// //   });

// //   it('should deny access and redirect when not authenticated', () => {
// //     localStorage.removeItem('authToken');

// //     spyOn(router, 'navigate');
// //     const result = authGuard({} as ActivatedRouteSnapshot, {} as RouterStateSnapshot);

// //     expect(result).toBeFalse();
// //     expect(router.navigate).toHaveBeenCalledWith(['/login']);
// //   });
// // });

// import { TestBed } from '@angular/core/testing';;
// import { AuthGuard } from './auth.guard';
// import { provideRouter, Router, RouterModule } from '@angular/router';
// import { HttpClientTestingModule } from '@angular/common/http/testing';

// describe('AuthGuard', () => {
//   let guard: AuthGuard;
//   let router: Router;

//   beforeEach(() => {
//     TestBed.configureTestingModule({
//       providers: [AuthGuard, provideRouter([])]
//     });
//     guard = TestBed.inject(AuthGuard);
//     router = TestBed.inject(Router);
//   });

//   it('should create', () => {
//     expect(guard).toBeTruthy();
//   });
// });