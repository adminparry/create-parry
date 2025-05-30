import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  constructor(private route: ActivatedRoute,private router: Router){}

  ngOnInit(){
    this.route.queryParamMap.subscribe(query => {
      const name = query.get('name');

      console.log(name)
    })

    this.route.paramMap.subscribe(query => {
      const name = query.get('name');

      console.log(name)
    })
  }
  jump(){
    this.router.navigate(['/test', 'jump'])
  }
}
