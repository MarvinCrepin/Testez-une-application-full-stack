import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { of } from 'rxjs';
import { expect } from '@jest/globals';

import { DetailComponent } from './detail.component';
import { SessionService } from '../../../../services/session.service';
import { SessionApiService } from '../../services/session-api.service';
import { TeacherService } from '../../../../services/teacher.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let sessionApiService: Partial<SessionApiService>;
  let teacherService: Partial<TeacherService>;

  const mockSessionService = {
    sessionInformation: {
      token: 'token',
      type: 'Bearer',
      id: 1,
      username: 'johndoe@doe.com',
      firstName: 'John',
      lastName: 'Doe',
      admin: true
    }
  };

  beforeEach(async () => {
    sessionApiService = {
      delete: jest.fn().mockReturnValue(of({})),
      detail: jest.fn().mockReturnValue(of({})),
      participate: jest.fn().mockReturnValue(of({})),
      unParticipate: jest.fn().mockReturnValue(of({}))
    };

    teacherService = {
      detail: jest.fn().mockReturnValue(of({}))
    };

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatSnackBarModule,
        ReactiveFormsModule
      ],
      declarations: [DetailComponent],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: sessionApiService },
        { provide: TeacherService, useValue: teacherService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call delete function on delete', () => {
    const sessionApiServiceDeleteSpy = jest.spyOn(sessionApiService, 'delete');
    const matSnackBarOpenSpy = jest.spyOn(TestBed.inject(MatSnackBar), 'open');

    component.delete();

    expect(sessionApiServiceDeleteSpy).toHaveBeenCalled();
    expect(matSnackBarOpenSpy).toHaveBeenCalledWith('Session deleted !', 'Close', { duration: 3000 });
  });

  it('should call participate function on participate', () => {
    const sessionApiServiceParticipateSpy = jest.spyOn(sessionApiService, 'participate');

    component.participate();

    expect(sessionApiServiceParticipateSpy).toHaveBeenCalled();
  });

  it('should call unParticipate function on unParticipate', () => {
    const sessionApiServiceUnParticipateSpy = jest.spyOn(sessionApiService, 'unParticipate');

    component.unParticipate();

    expect(sessionApiServiceUnParticipateSpy).toHaveBeenCalled();
  });


});