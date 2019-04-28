import { Component, OnInit } from '@angular/core';
import { CoreService } from '../../services/core.service';



@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  left: string[];

  right: string[];

  queryLEFT: string = "select * from sqlgame.a";

  queryRIGHT: string = "select * from sqlgame.b";

  queryOUTPUT: string;

  output: string[];

  constructor(private coreService: CoreService) { }

  ngOnInit() {
    this.coreService.getGrid(this.queryLEFT).subscribe(
      data => {
        console.log("POST Request is successful ", data);
        this.left = data;
      },
      error => {
        console.log("Rrror", error);
      }
    );
    this.coreService.getGrid(this.queryRIGHT).subscribe(
      data => {
        console.log("POST Request is successful ", data);
        this.right = data;
      },
      error => {
        console.log("Rrror", error);
      }
    );
  }

  public getOutput(event: any) {
    let temp = [];
    this.coreService.getGrid(event.target.value).subscribe(
      data => {
        console.log("POST Request is successful ", data);
        data.forEach(element => {
          if(!Array.isArray(element)){
            temp.push([element]);
          }
        });
        if(temp.length > 0){
          this.output = temp;
        }else{
          this.output = data;
        }
      },
      error => {
        console.log("Rrror", error);
      }
    );
  }
}
