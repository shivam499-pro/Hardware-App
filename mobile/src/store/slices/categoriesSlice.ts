/**
 * Categories State Slice
 * Manages categories data
 */

import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { Category } from '../../features/categories/types';

interface CategoriesState {
  items: Category[];
  loading: boolean;
  error: string | null;
  lastFetched: string | null;
}

const initialState: CategoriesState = {
  items: [],
  loading: false,
  error: null,
  lastFetched: null,
};

const categoriesSlice = createSlice({
  name: 'categories',
  initialState,
  reducers: {
    setCategories: (state, action: PayloadAction<Category[]>) => {
      state.items = action.payload;
      state.lastFetched = new Date().toISOString();
    },
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.loading = action.payload;
    },
    setError: (state, action: PayloadAction<string | null>) => {
      state.error = action.payload;
    },
    addCategory: (state, action: PayloadAction<Category>) => {
      state.items.push(action.payload);
    },
    updateCategory: (state, action: PayloadAction<Category>) => {
      const index = state.items.findIndex(c => c.id === action.payload.id);
      if (index !== -1) {
        state.items[index] = action.payload;
      }
    },
    removeCategory: (state, action: PayloadAction<number>) => {
      state.items = state.items.filter(c => c.id !== action.payload);
    },
    clearCategories: (state) => {
      state.items = [];
      state.lastFetched = null;
    },
  },
});

export const {
  setCategories,
  setLoading,
  setError,
  addCategory,
  updateCategory,
  removeCategory,
  clearCategories,
} = categoriesSlice.actions;

export default categoriesSlice.reducer;
