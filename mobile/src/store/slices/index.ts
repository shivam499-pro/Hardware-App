/**
 * Store Slices - Index
 */

export { default as appReducer } from './appSlice';
export { default as categoriesReducer } from './categoriesSlice';
export { default as productsReducer } from './productsSlice';

// Export actions with aliases to avoid naming conflicts
export {
  setLoading as setAppLoading,
  setError as setAppError,
  setLanguage,
  setUser,
  clearError,
  reset,
} from './appSlice';

export {
  setCategories,
  setLoading as setCategoriesLoading,
  setError as setCategoriesError,
  addCategory,
  updateCategory,
  removeCategory,
  clearCategories,
} from './categoriesSlice';

export {
  setProducts,
  setCurrentProduct,
  setLoading as setProductsLoading,
  setError as setProductsError,
  addProduct,
  updateProduct,
  removeProduct,
  clearProducts,
} from './productsSlice';
