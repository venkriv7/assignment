import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TratesComponent } from './trates.component';

describe('TratesComponent', () => {
  let component: TratesComponent;
  let fixture: ComponentFixture<TratesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TratesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TratesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
