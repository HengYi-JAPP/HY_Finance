import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {
  @Input() isVisible: boolean;
  @Output() HandleOk = new EventEmitter();
  @Output() HandleCancel = new EventEmitter();
  validateForm: FormGroup;
  // showModal = () => {
  //   this.isVisible = true;
  // }
  handleOk = (e) => {
    console.log(e)
    this.HandleOk.emit(e);
  }

  handleCancel = (e) => {
    console.log(e)
    this.HandleCancel.emit(e);
  }
  constructor() { }

  ngOnInit() {
  }

}
