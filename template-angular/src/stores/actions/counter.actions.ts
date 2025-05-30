import { createAction, props } from '@ngrx/store';

export const incrementCounters = createAction(
  '[Counter] Increment Counters'
);
export const decrementCounters = createAction(
  '[Counter] Decrement Counters'
);

