import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.email, this.password).subscribe({
      next: (response) => {
        localStorage.setItem('authToken', response.token);
        this.router.navigate(['/products']);
      },
      error: (err) => alert('Invalid credentials')
    });
  }

  register() {
    this.authService.register(this.email, this.password).subscribe({
      next: () => alert('User registered successfully!'),
      error: (err) => alert('Registration failed')
    });
  }
}
