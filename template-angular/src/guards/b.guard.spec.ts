import { TestBed } from '@angular/core/testing';

import { BGuard } from './b.guard';

describe('BGuard', () => {
  let guard: BGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(BGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
