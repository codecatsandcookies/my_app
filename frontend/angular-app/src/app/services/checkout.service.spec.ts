// import { TestBed } from '@angular/core/testing';
// import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
// import { CheckoutService } from './checkout.service';
// import { Purchase } from '../common/purchase';

// describe('CheckoutService', () => {
//   let service: CheckoutService;
//   let httpMock: HttpTestingController;

//   beforeEach(() => {
//     TestBed.configureTestingModule({
//       imports: [HttpClientTestingModule],
//       providers: [CheckoutService]
//     });

//     service = TestBed.inject(CheckoutService);
//     httpMock = TestBed.inject(HttpTestingController);
//   });

//   afterEach(() => {
//     httpMock.verify();
//   });

//   it('should place an order successfully', () => {
//     const mockResponse = { orderTrackingNumber: '12345-abcde' };
//     const mockPurchase: Purchase = {
//       order: { totalQuantity: 1, totalPrice: 100.00 }, // Add required properties
//       customer: { firstName: 'John', lastName: 'Doe', email: 'john@example.com' },
//       shippingAddress: { street: '123 Main St', city: 'New York', state: 'NY', zipCode: '10001', country: 'USA' },
//       billingAddress: { street: '123 Main St', city: 'New York', state: 'NY', zipCode: '10001', country: 'USA' },
//       orderItems: [{ quantity: 1, unitPrice: 100.00, productId: 1, imageUrl: '' }]
//     };

//     service.placeOrder(mockPurchase).subscribe(response => {
//       expect(response).toEqual(mockResponse);
//     });

//     const req = httpMock.expectOne('https://ecommerce-spring-boot-production-b949.up.railway.app/api/checkout/purchase');
//     expect(req.request.method).toBe('POST');
//     req.flush(mockResponse);

//     httpMock.expectOne('https://ecommerce-spring-boot-production-b949.up.railway.app/api/products/updateStock/1').flush({});
//   });

//   it('should return an error when checkout fails', () => {
//     const mockPurchase: Purchase = {
//       order: { totalQuantity: 1, totalPrice: 100.00 }, // Add required properties
//       customer: { firstName: 'John', lastName: 'Doe', email: 'john@example.com' },
//       shippingAddress: { street: '123 Main St', city: 'New York', state: 'NY', zipCode: '10001', country: 'USA' },
//       billingAddress: { street: '123 Main St', city: 'New York', state: 'NY', zipCode: '10001', country: 'USA' },
//       orderItems: [{ quantity: 1, unitPrice: 100.00, productId: 1, imageUrl: ''}]
//     };

//     service.placeOrder(mockPurchase).subscribe(
//       () => fail('Expected an error, but got success'),
//       error => {
//         expect(error.status).toBe(500);
//       }
//     );

//     const req = httpMock.expectOne('https://ecommerce-spring-boot-production-b949.up.railway.app/api/checkout/purchase');
//     req.flush({ message: 'Checkout failed' }, { status: 500, statusText: 'Internal Server Error' });
//   });
// });
