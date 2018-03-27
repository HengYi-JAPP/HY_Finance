import { Injectable } from '@angular/core';
import * as global from '../global';
// import 'rxjs/add/observable/of';
import {GetResponseService} from './getResponse.service';
@Injectable()
export class ResultService {
  data: any;
  constructor(private response: GetResponseService) {}
  getData() {
    const params = {
      test: 1
    };
    this.response.appPost(params, global.baseUrl + '/FinanceBudgetController/getResultData').subscribe(
      data => console.log(data)
    );
  }
}
