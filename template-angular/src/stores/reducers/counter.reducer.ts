import { Action, createReducer, on } from '@ngrx/store';
import { decrementCounters, incrementCounters, } from '../actions/counter.actions';


export const counterFeatureKey = 'counter';

export interface State {
  count: number
}

export const initialState: State = {
  count: 0
};

export const reducer = createReducer(
  initialState,
  on(incrementCounters, state => ({ count: state.count + 1 })),
  on(decrementCounters, state => ({ count: state.count - 1 }))
  
);
