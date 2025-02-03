import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    if (this.password !== this.confirmPassword) {
      this.errorMessage = "Passwords do not match!";
      return;
    }

    this.authService.register(this.email, this.password).subscribe({
      next: () => {
        alert('Registration successful! Please log in.');
        this.router.navigate(['/login']); // Redirect to login page after registration
      },
      error: err => {
        this.errorMessage = "Registration failed. Please try again.";
        console.error(err);
      }
    });
  }
}
