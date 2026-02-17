/**
 * Products State Slice
 * Manages products data
 */

import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { Product } from '../../features/products/types';

interface ProductsState {
  items: Product[];
  currentProduct: Product | null;
  loading: boolean;
  error: string | null;
  lastFetched: string | null;
}

const initialState: ProductsState = {
  items: [],
  currentProduct: null,
  loading: false,
  error: null,
  lastFetched: null,
};

const productsSlice = createSlice({
  name: 'products',
  initialState,
  reducers: {
    setProducts: (state, action: PayloadAction<Product[]>) => {
      state.items = action.payload;
      state.lastFetched = new Date().toISOString();
    },
    setCurrentProduct: (state, action: PayloadAction<Product | null>) => {
      state.currentProduct = action.payload;
    },
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.loading = action.payload;
    },
    setError: (state, action: PayloadAction<string | null>) => {
      state.error = action.payload;
    },
    addProduct: (state, action: PayloadAction<Product>) => {
      state.items.push(action.payload);
    },
    updateProduct: (state, action: PayloadAction<Product>) => {
      const index = state.items.findIndex(p => p.id === action.payload.id);
      if (index !== -1) {
        state.items[index] = action.payload;
      }
      if (state.currentProduct?.id === action.payload.id) {
        state.currentProduct = action.payload;
      }
    },
    removeProduct: (state, action: PayloadAction<number>) => {
      state.items = state.items.filter(p => p.id !== action.payload);
      if (state.currentProduct?.id === action.payload) {
        state.currentProduct = null;
      }
    },
    clearProducts: (state) => {
      state.items = [];
      state.lastFetched = null;
    },
  },
});

export const {
  setProducts,
  setCurrentProduct,
  setLoading,
  setError,
  addProduct,
  updateProduct,
  removeProduct,
  clearProducts,
} = productsSlice.actions;

export default productsSlice.reducer;
