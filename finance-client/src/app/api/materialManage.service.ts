import { Injectable } from '@angular/core';
import * as global from '../global';
import {GetResponseService} from './getResponse.service';
import {Observable} from 'rxjs/Observable';
const url = global.baseUrl + '/MaterialManageController';
// 物料匹配关系管理页面
@Injectable()
export class MaterialManageService {
  constructor (private response: GetResponseService) {}
  // 获取未匹配上的物料
  getUnmatchedMaterial(param): Observable<any> {
    return this.response.appPost(param, url + '/getUnmatchedMaterial');
  }
  // 获取物料匹配关系表的数据
  getMatchedMaterial(param): Observable<any> {
    return this.response.appPost(param, url + '/getMatchedMaterial');
  }
  // 添加物料匹配关系
  addMatchedMaterial(param): Observable<any> {
    return this.response.appPost(param, url + '/addUnmatchedMaterial');
  }
  // 更新物料匹配关系
  updateMatchedMaterial(param): Observable<any> {
    return this.response.appPost(param, url + '/updateUnmatchedMaterial');
  }
}
