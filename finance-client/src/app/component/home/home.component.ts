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
      this.array = [
        'assets/picture/p6.jpg',
        'assets/picture/p5.JPG',
        'assets/picture/p7.jpg',
        'assets/picture/p8.jpg'];
    }, 500);
  }

  constructor() { }

}
