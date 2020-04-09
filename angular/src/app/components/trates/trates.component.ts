import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from './../../shared/auth.service';
import { Router } from '@angular/router';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { CurrencyRates } from 'src/app/shared/currencyrates';

@Component({
  selector: 'app-trates',
  templateUrl: './trates.component.html',
  styleUrls: ['./trates.component.css']
})
export class TratesComponent implements OnInit {
  displayedColumns: string[] = ['id', 'currencyCode', 'rate', 'rateDate'];
  fetchUrl = 'rates/getCurrentDateRates';
  currentRates: CurrencyRates[];
  dataSource: MatTableDataSource<any>;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(public authService: AuthService,
    public router: Router) { 
      this.authService.getCurrencyRates(this.fetchUrl).subscribe(
        (data) => {
        this.currentRates = data;
        console.log('services: ', this.currentRates); // it loads the data and print in console
        });
        //const ELEMENT_DATA: CurrencyRates[] = this.currentRates;
        this.dataSource = new MatTableDataSource(this.currentRates);
        console.log('init: ', this.currentRates); //at this point i lost all the data
    }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
      }

      applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();
    
        if (this.dataSource.paginator) {
          this.dataSource.paginator.firstPage();
        }
      }
    
      historial() {
              this.router.navigate(['historical']);
      }
}
