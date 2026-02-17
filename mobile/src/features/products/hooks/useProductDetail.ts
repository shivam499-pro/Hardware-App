/**
 * Products Feature Hooks
 */

import { useState, useEffect } from 'react';
import { productService, Product } from '../../../services/api';

interface UseProductDetailResult {
  product: Product | null;
  loading: boolean;
  error: string | null;
  fetchProduct: () => Promise<void>;
}

export const useProductDetail = (productId: number): UseProductDetailResult => {
  const [product, setProduct] = useState<Product | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchProduct = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await productService.getProductById(productId);
      setProduct(data);
    } catch (err) {
      setError('Failed to load product details. Please try again.');
      console.error('Error fetching product:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProduct();
  }, [productId]);

  return {
    product,
    loading,
    error,
    fetchProduct,
  };
};

export default useProductDetail;
