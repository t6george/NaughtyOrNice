import { Component, OnInit } from '@angular/core';
import { NgForOf } from '@angular/common';
import {trigger, style, transition, animate, keyframes, query, stagger} from '@angular/animations';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  animations: [
    trigger('goals', [
      transition('* => *', [
        query(':enter', style({opacity: 0}), {optional: true}),

        query(':enter', stagger('300ms', [
          animate('.6s ease-in', keyframes([
            style({opacity: 0, transform: 'translateY(-75%)', offset: 0}),
            style({opacity: .5, transform: 'translateY(35px)', offset: 0.3}),
            style({opacity: 1, transform: 'translateY(0)', offset: 1}),
          ]))]), {optional: true}),

        query(':leave', stagger('300ms', [
          animate('.6s ease-in', keyframes([
            style({opacity: 1, transform: 'translateY(0)', offset: 0}),
            style({opacity: .5, transform: 'translateY(35px)', offset: 0.3}),
            style({opacity: 0, transform: 'translateY(-75%)', offset: 1}),
          ]))]), {optional: true})
      ])
    ])
  ]
})
export class HomeComponent implements OnInit {
  percent: number;
  private bar;
  constructor() {
  }

  ngOnInit() {
    this.bar = document.getElementById('progBar');
    this.percent = 0;
    this.increaseBar(this.percent);
  }

  increaseBar(percent) {
    while (this.percent < 70) {
      console.log(percent);
      this.percent++;
      this.bar.style.width = this.percent +'%';
      // this.bar.innerHTML = this.bar.style.width;
    }
  }



}
  // itemCount: number;
  // btnText = 'Add an item';
  // goalText = 'My first life goal';
  // goals = [];
  //
  //



  //
  // addItem() {
  //   this.goals.push(this.goalText);
  //   this.goalText = '';
  //   this.itemCount = this.goals.length;
  //   this._data.changeGoal(this.goals);
  // }
  //
  // removeItem(i) {
  //   this.goals.splice(i,1);
  //   this._data.changeGoal(this.goals);
  // }


//
// }
