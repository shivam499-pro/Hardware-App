/**
 * Redux Store Configuration
 */

import { configureStore } from '@reduxjs/toolkit';
import appReducer from './slices/appSlice';
import categoriesReducer from './slices/categoriesSlice';
import productsReducer from './slices/productsSlice';

export const store = configureStore({
  reducer: {
    app: appReducer,
    categories: categoriesReducer,
    products: productsReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
