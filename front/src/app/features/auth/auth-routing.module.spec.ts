import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { Router } from '@angular/router';
import { expect } from '@jest/globals';

describe('AuthRoutingModule', () => {
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        AuthRoutingModule,
        RouterTestingModule.withRoutes([]),
      ],
      declarations: [
        LoginComponent,
        RegisterComponent
      ]
    }).compileComponents();

    router = TestBed.inject(Router);
  });

  it('should navigate to login', () => {
    const navigateSpy = jest.spyOn(router, 'navigate');

    router.navigate(['/login']);

    expect(navigateSpy).toHaveBeenCalledWith(['/login']);
  });

  it('should navigate to register', () => {
    const navigateSpy = jest.spyOn(router, 'navigate');

    router.navigate(['/register']);

    expect(navigateSpy).toHaveBeenCalledWith(['/register']);
  });
});