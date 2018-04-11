import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  array = [ 'assets/picture/p1.png'];

  ngOnInit() {
    setTimeout(_ => {
      this.array = ['assets/picture/p1.png',
        'assets/picture/p2.png',
        'assets/picture/p3.png',
        'assets/picture/p4.png'];
    }, 500);
  }

  constructor() { }

}
