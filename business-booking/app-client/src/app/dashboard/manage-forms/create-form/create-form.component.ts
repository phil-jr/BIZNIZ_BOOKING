import { Component, OnInit } from '@angular/core';
import { CreateForm } from 'src/app/model/create-form-model';

@Component({
  selector: 'app-create-form',
  templateUrl: './create-form.component.html',
  styleUrls: ['./create-form.component.css']
})
export class CreateFormComponent implements OnInit {

  newForm: CreateForm = new CreateForm();
  selectedAdd = "";
  addInputs = ["", "Text", "Number", "Selection"];
  showSelect = false;

  constructor() { }

  ngOnInit(): void {
  }

  toggleShowSelect(){
    this.showSelect = !this.showSelect;
  }

  testChange(){
    
  }

}
