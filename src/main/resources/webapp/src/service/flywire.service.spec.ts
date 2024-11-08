import { TestBed } from '@angular/core/testing';

import { FlywireService } from './flywire.service';

describe('FlywireService', () => {
  let service: FlywireService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FlywireService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
