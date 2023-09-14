import { HttpClientModule, HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { Session } from '../interfaces/session.interface';
import { SessionApiService } from './session-api.service';
import { expect } from '@jest/globals';

describe('SessionApiService', () => {
  let service: SessionApiService;
  let httpClient: HttpClient;

  const mockHttpClient = {
    get: jest.fn(),
    post: jest.fn(),
    put: jest.fn(),
    delete: jest.fn()
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [
        SessionApiService,
        { provide: HttpClient, useValue: mockHttpClient }
      ]
    });
    service = TestBed.inject(SessionApiService);
    httpClient = TestBed.inject(HttpClient);
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  const sessionTemplate : Session = { id: 1, name: 'first', description: 'first description', date: new Date(), teacher_id: 1, users: [] };


  it('should call HttpClient get method for all sessions', () => {
    const mockSessions: Session[] = [ { id: 1, name: 'Session 1', description: 'Description 1', date: new Date(), teacher_id: 1, users: [] },
    { id: 2, name: 'Session 2', description: 'Description 2', date: new Date(), teacher_id: 2, users: [] }];
    mockHttpClient.get.mockReturnValue(of(mockSessions));

    service.all().subscribe((sessions: Session[]) => {
      expect(sessions).toEqual(mockSessions);
    });

    expect(httpClient.get).toHaveBeenCalledWith('api/session');
  });

  it('should call HttpClient get method for session detail', () => {
    mockHttpClient.get.mockReturnValue(of(sessionTemplate));

    service.detail('1').subscribe((session: Session) => {
      expect(session).toEqual(sessionTemplate);
    });

    expect(httpClient.get).toHaveBeenCalledWith('api/session/1');
  });

  it('should call HttpClient delete method for session deletion', () => {
    mockHttpClient.delete.mockReturnValue(of({}));

    service.delete('1').subscribe(() => {
      expect(httpClient.delete).toHaveBeenCalledWith('api/session/1');
    });
  });

  it('should call HttpClient post method for session creation', () => {
    mockHttpClient.post.mockReturnValue(of(sessionTemplate));

    service.create(sessionTemplate).subscribe((createdSession: Session) => {
      expect(createdSession).toEqual(sessionTemplate);
    });

    expect(httpClient.post).toHaveBeenCalledWith('api/session', sessionTemplate);
  });

  it('should call HttpClient put method for session update', () => {
    mockHttpClient.put.mockReturnValue(of(sessionTemplate));

    service.update('1', sessionTemplate).subscribe((updatedSession: Session) => {
      expect(updatedSession).toEqual(sessionTemplate);
    });

    expect(httpClient.put).toHaveBeenCalledWith('api/session/1', sessionTemplate);
  });

  it('should call HttpClient post method for user participation', () => {
    mockHttpClient.post.mockReturnValue(of({}));

    service.participate('1', '1').subscribe(() => {
      expect(httpClient.post).toHaveBeenCalledWith('api/session/1/participate/1', null);
    });
  });

  it('should call HttpClient delete method for user unparticipation', () => {
    mockHttpClient.delete.mockReturnValue(of({}));

    service.unParticipate('1', '1').subscribe(() => {
      expect(httpClient.delete).toHaveBeenCalledWith('api/session/1/participate/1');
    });
  });
});
