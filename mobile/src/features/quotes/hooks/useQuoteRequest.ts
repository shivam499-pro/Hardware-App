/**
 * Quotes Feature Hooks
 */

import { useState, useCallback } from 'react';
import { quoteService, QuoteRequest as ServiceQuoteRequest } from '../../../services/api';
import { QuoteRequestInput, QuoteRequest } from '../types';
import i18n from '../../../app/i18n';

interface UseQuoteRequestResult {
  loading: boolean;
  error: string | null;
  success: boolean;
  submitQuote: (data: QuoteRequestInput) => Promise<ServiceQuoteRequest | null>;
  reset: () => void;
}

export const useQuoteRequest = (): UseQuoteRequestResult => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState(false);

  const submitQuote = useCallback(async (data: QuoteRequestInput): Promise<ServiceQuoteRequest | null> => {
    try {
      setLoading(true);
      setError(null);
      setSuccess(false);

      const quoteData: ServiceQuoteRequest = {
        name: data.name,
        phone: data.phone,
        productId: data.productId,
        quantity: data.quantity,
        location: data.location,
        languageCode: i18n.language,
      };

      const result = await quoteService.submitQuoteRequest(quoteData);
      setSuccess(true);
      return result;
    } catch (err: any) {
      console.error('Error submitting quote:', err);
      setError(err.message || 'Failed to submit quote request. Please try again.');
      return null;
    } finally {
      setLoading(false);
    }
  }, []);

  const reset = useCallback(() => {
    setLoading(false);
    setError(null);
    setSuccess(false);
  }, []);

  return {
    loading,
    error,
    success,
    submitQuote,
    reset,
  };
};

export default useQuoteRequest;
