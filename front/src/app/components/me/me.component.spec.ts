import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { User } from '../../interfaces/user.interface';
import { SessionService } from '../../services/session.service';
import { UserService } from '../../services/user.service';
import { MeComponent } from './me.component';
import { expect } from '@jest/globals';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let routerMock: Partial<Router>;
  let sessionServiceMock: Partial<SessionService>;
  let matSnackBarMock: Partial<MatSnackBar>;
  let userServiceMock: Partial<UserService>;
    
  beforeEach(() => {
    routerMock = {
      navigate: jest.fn()
    };
    sessionServiceMock = {
        sessionInformation: {
            token: 'token',
            type: 'Bearer',
            id: 1,
            username: 'johnDoe',
            firstName: 'John',
            lastName: 'Doe',
            admin: true
          } as SessionInformation,
          logOut: jest.fn()
    };
    matSnackBarMock = {
      open: jest.fn()
    };
    userServiceMock = {
      getById: jest.fn().mockReturnValue(of({ id: 1, firstName: 'John', lastName: 'Doe' } as User)),
      delete: jest.fn().mockReturnValue(of({}))
    };

    TestBed.configureTestingModule({
      declarations: [MeComponent],
      providers: [
        { provide: Router, useValue: routerMock },
        { provide: SessionService, useValue: sessionServiceMock },
        { provide: MatSnackBar, useValue: matSnackBarMock },
        { provide: UserService, useValue: userServiceMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch user data on initialization', () => {
    component.ngOnInit();

    expect(userServiceMock.getById).toHaveBeenCalledWith('1');
    expect(component.user).toEqual({ id: 1, firstName: 'John', lastName: 'Doe' } as User);
  });

  it('should go back when back() is called', () => {
    const windowSpy = jest.spyOn(window.history, 'back');

    component.back();

    expect(windowSpy).toHaveBeenCalled();
  });

  it('should delete user account, show snack bar message, log out, and navigate to home', () => {
    component.delete();

    expect(userServiceMock.delete).toHaveBeenCalledWith('1');
    expect(matSnackBarMock.open).toHaveBeenCalledWith("Your account has been deleted !", 'Close', { duration: 3000 });
    expect(sessionServiceMock.logOut).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/']);
  });
});