/**
 * App State Slice
 * Manages global application state
 */

import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { User, AppState } from '../../types';

const initialState: AppState = {
  isLoading: false,
  error: null,
  language: 'en',
  user: null,
};

const appSlice = createSlice({
  name: 'app',
  initialState,
  reducers: {
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.isLoading = action.payload;
    },
    setError: (state, action: PayloadAction<string | null>) => {
      state.error = action.payload;
    },
    setLanguage: (state, action: PayloadAction<string>) => {
      state.language = action.payload;
    },
    setUser: (state, action: PayloadAction<User | null>) => {
      state.user = action.payload;
    },
    clearError: (state) => {
      state.error = null;
    },
    reset: () => initialState,
  },
});

export const { setLoading, setError, setLanguage, setUser, clearError, reset } = appSlice.actions;
export default appSlice.reducer;
