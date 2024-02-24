import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DentistMainComponent } from './dentist-main.component';

describe('DentistMainComponent', () => {
  let component: DentistMainComponent;
  let fixture: ComponentFixture<DentistMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DentistMainComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DentistMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
