import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoticePeriodDialogComponent } from './notice-period-dialog.component';

describe('NoticePeriodDialogComponent', () => {
  let component: NoticePeriodDialogComponent;
  let fixture: ComponentFixture<NoticePeriodDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NoticePeriodDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NoticePeriodDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
