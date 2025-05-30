import { Component } from '@angular/core';
import { select, Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AppState } from 'src/stores';
import { decrementCounters, incrementCounters } from 'src/stores/actions/counter.actions';
import { selectCount } from 'src/stores/selectors/counter.selectors';

@Component({
  selector: 'app-root',
  templateUrl: './root.component.html',
  styleUrls: ['./root.component.scss']
})
export class RootComponent {

  count: Observable<number>
  constructor(private store: Store<AppState>) {
    this.count = this.store.pipe(select(selectCount))
  }
  increment() {
    this.store.dispatch(incrementCounters())
  }
  decrement() {
    this.store.dispatch(decrementCounters())
  }
}
