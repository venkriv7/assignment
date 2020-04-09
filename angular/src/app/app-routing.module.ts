import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SigninComponent } from './components/signin/signin.component';
import { SignupComponent } from './components/signup/signup.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';

import { AuthGuard } from "./shared/auth.guard";
import { HomeComponent } from './components/home/home.component';
import { TratesComponent } from './components/trates/trates.component';
import { HistoricalComponent } from './components/historical/historical.component';


const routes: Routes = [
  { path: '', redirectTo: '/log-in', pathMatch: 'full' },
  { path: 'log-in', component: SigninComponent },
  { path: 'sign-up', component: SignupComponent },
  { path: 'landing', component: HomeComponent, canActivate: [AuthGuard]  },
  { path: 'trates', component: TratesComponent , canActivate: [AuthGuard] },
  { path: 'historical', component: HistoricalComponent, canActivate: [AuthGuard]  },
  { path: 'user-profile/:id', component: UserProfileComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
