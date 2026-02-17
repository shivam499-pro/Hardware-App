/**
 * Categories Feature Hooks
 */

import { useState, useEffect } from 'react';
import { productService, Product } from '../../../services/api';
import { ProductQueryParams } from '../../../services/api/productService';

interface UseCategoryProductsResult {
  products: Product[];
  loading: boolean;
  error: string | null;
  fetchProducts: () => Promise<void>;
}

export const useCategoryProducts = (
  categoryId: number,
  params?: ProductQueryParams
): UseCategoryProductsResult => {
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchProducts = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await productService.getProductsByCategory(categoryId, {
        page: 0,
        size: 50,
        sortBy: 'id',
        sortDir: 'asc',
        ...params,
      });
      setProducts(response.content);
    } catch (err) {
      setError('Failed to load products. Please try again.');
      console.error('Error fetching products:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, [categoryId]);

  return {
    products,
    loading,
    error,
    fetchProducts,
  };
};

export default useCategoryProducts;
