import { Injectable } from '@angular/core';
import * as global from '../global';
import {GetResponseService} from './getResponse.service';
import {Observable} from 'rxjs/Observable';
const url = global.baseUrl + '/ProductlineManageController';
// 生产线匹配关系管理页面
@Injectable()
export class ProductlineManageService {
  constructor (private response: GetResponseService) {}
  // 获取未匹配上的生产线
  getUnmatchedProductline(param): Observable<any> {
    return this.response.appPost(param, url + '/getUnmatchedProductline');
  }
  // 获取生产线匹配关系表的数据
  getMatchedProductline(param): Observable<any> {
    return this.response.appPost(param, url + '/ getMatchedProductline');
  }
  // 添加生产线匹配关系
  addMatchedProductline(param): Observable<any> {
    return this.response.appPost(param, url + '/addMatchedProductline');
  }
  // 更新生产线匹配关系
  updateMatchedProductline(param): Observable<any> {
    return this.response.appPost(param, url + '/ updateMatchedProductline');
  }
  // 删除生产线匹配关系
  deleteMatchedProductline(param): Observable<any> {
    return this.response.appPost(param, url + '/deleteMatchedProductline');
  }
}
