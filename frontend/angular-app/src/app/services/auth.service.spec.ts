import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService],
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); 
  });

  it('should login a user and store token', () => {
    const mockResponse = { token: 'fake-jwt-token' };
    const email = 'test@example.com';
    const password = 'securepassword';

    service.login(email, password).subscribe(response => {
      expect(response).toEqual(mockResponse);
      expect(localStorage.getItem('authToken')).toBe(mockResponse.token);
    });

    
    const req = httpMock.expectOne('https://ecommerce-spring-boot-production-b949.up.railway.app/api/auth/login');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ email, password });

    
    req.flush(mockResponse);
  });

});