import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CancellationDeadlineComponent } from './cancellation-deadline.component';

describe('CancellationDeadlineComponent', () => {
  let component: CancellationDeadlineComponent;
  let fixture: ComponentFixture<CancellationDeadlineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CancellationDeadlineComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CancellationDeadlineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
