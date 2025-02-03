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
    if (!this.email || !this.password) {
      alert("Email and password are required!");
      return;
    }
  
    this.authService.register(this.email, this.password).subscribe({
      next: (response) => {
        console.log('Registration Success:', response);
  
        // 🔹 Extract the actual response message
        let message = response.body?.message || response.body || "User registered successfully!";
        alert(message);
      },
      error: (err) => {
        console.log('Registration Error:', err);
  
        // 🔹 Extract error message if available
        let errorMessage = err.error?.message || err.error || "Registration failed";
        alert(errorMessage);
      }
    });
  }
}
