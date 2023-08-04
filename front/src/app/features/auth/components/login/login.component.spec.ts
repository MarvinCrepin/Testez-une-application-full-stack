import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { LoginComponent } from './login.component';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { of, throwError } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [SessionService], // Utilisez ici le bon service de session
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule
      ]
    }).compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('LoginComponent', () => {
    let mockFormBuilder: FormBuilder;
    let mockAuthService: Partial<AuthService>;
    let mockSessionService: Partial<SessionService>;
    let mockRouter: Partial<Router>;
  
    beforeEach(() => {
      // Réinitialiser les mocks avant chaque test
      jest.clearAllMocks();

      // Créer des instances de mocks pour les services et FormBuilder
      mockAuthService = { login: jest.fn().mockReturnValue({ subscribe: jest.fn() }) };
      mockSessionService = { logIn: jest.fn() };
      mockRouter = { navigate: jest.fn() };
      mockFormBuilder = new FormBuilder();

      // Créer une instance du composant LoginComponent avec les mocks
      component = new LoginComponent(
        mockAuthService as AuthService,
        mockFormBuilder,
        mockRouter as Router,
        mockSessionService as SessionService
      );

      // Créer un groupe de formulaire valide pour le composant
      const validFormData = {
        email: 'yoga@studio.com',
        password: 'test!1234'
      };
      component.form = mockFormBuilder.group(validFormData);
    });

    it('should handle successful login', () => {
      // Données de test pour une connexion réussie
      const loginData = { email: 'yoga@studio.com', password: 'test!1234' };

      // Réponse simulée renvoyée par le service AuthService
      const mockResponse = {
        token: 'testToken',
        type: '',
        id: 1,
        username: 'Admin',
        firstName: 'Admin',
        lastName: 'Admin',
        admin: true
      };

      // Mock la méthode login du AuthService pour renvoyer la réponse simulée
      jest.spyOn(mockAuthService, 'login').mockReturnValue(of(mockResponse));

      // Exécute la méthode de soumission du formulaire
      component.submit();

      // Vérifie que la méthode login du AuthService a été appelée avec les bonnes données
      expect(mockAuthService.login).toHaveBeenCalledTimes(1);
      expect(mockAuthService.login).toHaveBeenCalledWith(loginData);

      // Vérifie que la méthode logIn du SessionService a été appelée avec la réponse simulée
      expect(mockSessionService.logIn).toHaveBeenCalledTimes(1);
      expect(mockSessionService.logIn).toHaveBeenCalledWith(mockResponse);

      // Vérifie que la méthode navigate du Router a été appelée avec le bon chemin
      expect(mockRouter.navigate).toHaveBeenCalledTimes(1);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['/sessions']);
    });

    it('should handle login errors', () => {
      // Mock la méthode login du AuthService pour renvoyer une erreur
      jest.spyOn(mockAuthService, 'login').mockReturnValue(throwError(() => new Error('Bad credentials')));

      // Exécute la méthode de soumission du formulaire
      component.submit();

      // Vérifie que la propriété onError est définie à true lorsque la connexion échoue
      expect(component.onError).toBe(true);

      // Vérifie que la méthode logIn du SessionService et la méthode navigate du Router n'ont pas été appelées
      expect(mockSessionService.logIn).not.toHaveBeenCalled();
      expect(mockRouter.navigate).not.toHaveBeenCalled();

      // Test pour des champs de connexion invalides (non conformes aux exigences)
      const invalidLoginData = { email: 'john@doe.com', password: 'john' };
      component.form.setValue(invalidLoginData);
      component.submit();
      expect(component.onError).toBe(true);

      // Test pour des champs de connexion vides
      const emptyEmailLogin = { email: '', password: 'yoga' };
      component.form.setValue(emptyEmailLogin);

      // Mock la méthode login du AuthService pour renvoyer une autre erreur
      jest.spyOn(mockAuthService, 'login').mockReturnValue(throwError(() => new Error('You have to provide a non empty mail')));
      component.submit();
      expect(component.onError).toBe(true);
    });
  });
});