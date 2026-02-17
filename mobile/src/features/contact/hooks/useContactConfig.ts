/**
 * Contact Feature Hooks
 */

import { useState, useEffect } from 'react';
import { configService, BusinessConfig } from '../../../services/api/configService';

interface UseContactConfigResult {
  config: BusinessConfig;
  loading: boolean;
  error: string | null;
  fetchConfig: () => Promise<void>;
}

export const useContactConfig = (): UseContactConfigResult => {
  const [config, setConfig] = useState<BusinessConfig>({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchConfig = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await configService.getBusinessConfig();
      setConfig(data);
    } catch (err) {
      setError('Failed to load contact information. Please try again.');
      console.error('Error fetching contact config:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchConfig();
  }, []);

  return {
    config,
    loading,
    error,
    fetchConfig,
  };
};

export default useContactConfig;
