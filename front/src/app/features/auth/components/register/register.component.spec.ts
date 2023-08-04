import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../interfaces/registerRequest.interface';
import { Router } from '@angular/router';
import { of, throwError} from 'rxjs';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let mockAuthService: Partial<AuthService>;
  let mockRouter: Partial<Router>;
  let mockFormBuilder: FormBuilder;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;

    // Créer des instances de mocks pour le service AuthService et le Router
    mockAuthService = {
      register: jest.fn().mockReturnValue({ subscribe: jest.fn() })
    };
    mockRouter = {
      navigate: jest.fn()
    };
    mockFormBuilder = new FormBuilder();

    // Créer une instance du composant RegisterComponent avec les mocks
    component = new RegisterComponent(
      mockAuthService as AuthService,
      mockFormBuilder,
      mockRouter as Router
    );

    const registerFields: RegisterRequest = {
      email: '',
      firstName: '',
      lastName: '',
      password: ''
    };

    // Créer un groupe de formulaire valide pour le composant
    component.form = mockFormBuilder.group(registerFields);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Submit', () => {
    beforeEach(() => {
      // Réinitialiser les mocks avant chaque test
      jest.clearAllMocks();
    });

    it('should create an account with success', () => {
      const requiredFields: RegisterRequest = {
        email: 'john@doe.com',
        firstName: 'John',
        lastName: 'Doe',
        password: 'john'
      };

      const request = component.form.setValue(requiredFields);
      
      // Mock la méthode register du AuthService pour renvoyer une réponse réussie
      jest.spyOn(mockAuthService, 'register').mockReturnValue(of(request));

      // Set form values and call the submit method
      component.submit();

      // Vérifie que la méthode register du AuthService a été appelée avec les bonnes données
      expect(mockAuthService.register).toHaveBeenCalledTimes(1);
      expect(mockAuthService.register).toHaveBeenCalledWith(requiredFields);

      // Vérifie que la méthode navigate du Router a été appelée avec le bon chemin
      expect(mockRouter.navigate).toHaveBeenCalledWith(['/login']);
    });

    it('show error to empty required field', () => {
      const invalidRegister: RegisterRequest = {
        email: '',
        firstName: '',
        lastName: 'test',
        password: 'test'
      };

      component.form.setValue(invalidRegister);
      jest.spyOn(mockAuthService, 'register').mockReturnValue(throwError(() => new Error('Fields empty')));
      component.submit();

      // Vérifie que la propriété onError est définie à true lorsque la création de compte échoue
      expect(component.onError).toBe(true);

      // Vérifie que la méthode navigate du Router n'a pas été appelée
      expect(mockRouter.navigate).not.toHaveBeenCalled();
    });
  });
});