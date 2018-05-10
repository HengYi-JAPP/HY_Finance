import { Component, OnInit } from '@angular/core';
import { ElMessageService } from 'element-angular';
import {Router, ActivatedRoute, NavigationEnd} from '@angular/router';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  path: string;
  isCollapse: boolean;
  cols: number;
  today: string;
  tags: [{}] = [{id: 0, name: '欢迎页', type: 'gray', path: '/Home'}];
  constructor(private message: ElMessageService,
              private router: Router,
              private route: ActivatedRoute) {
    const date = new Date();
    this.today = date.getFullYear() + '-' + date.getMonth() + '-' + date.getDate() + ' ' + date.getHours() +
      ':' + date.getMinutes() + ':' + date.getSeconds();
    this.isCollapse = true;
    this.cols = 20;
  }
  ngOnInit () {
    this.router.events
      .filter(event => event instanceof NavigationEnd)
      .map(() => this.route)
      .map(route => {
        while (route.firstChild) {
          route = route.firstChild;
          this.path = route.routeConfig.path;
          return route;
        }
      })
      .filter(route => route.outlet === 'primary')
      .mergeMap(route => route.data)
      .subscribe((event) => {
         if (event['PageName'] === undefined) {
         } else {
           this.tags.push({id: this.tags.length, name: event['PageName'], type: 'gray', path: this.path});
         }
       }
      );
  }
  // 关闭标签
  closeTag (i: number) {
    if (this.tags.length === 1) {
      this.message['info']('最后一个标签无法删除！ ' );
    } else {
      if (this.tags[i]['path'] === this.path) {
        this.tags.splice(i, 1);
        if (i >= this.tags.length) {
          this.router.navigate([this.tags[i - 1]['path']]);
        } else {
          this.router.navigate([this.tags[i]['path']]);
        }
      } else {
        this.router.navigate([this.path]);
      }
    }
  }
  // 隐藏收拢菜单栏
  getTogether() {
    this.isCollapse = false;
    this.cols = 22;
  }
  // 展开菜单栏
  expand() {
    this.isCollapse = true;
    this.cols = 20;
  }
  handle(i: string): void {
    console.log(i);
  }

}


