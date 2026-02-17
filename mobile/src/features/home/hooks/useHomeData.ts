/**
 * Home Feature Hooks
 */

import { useState, useEffect, useCallback } from 'react';
import { categoryService, Category } from '../../../services/api/categoryService';
import { bannerService, Banner } from '../../../services/api/bannerService';
import { configService, BusinessConfig } from '../../../services/api/configService';
import { languageService, SupportedLanguage } from '../../../services/api/languageService';
import { useTranslation } from 'react-i18next';

interface UseHomeDataResult {
  categories: Category[];
  banners: Banner[];
  businessConfig: BusinessConfig;
  languages: SupportedLanguage[];
  loading: boolean;
  error: string | null;
  refreshing: boolean;
  fetchData: () => Promise<void>;
  onRefresh: () => void;
}

export const useHomeData = (): UseHomeDataResult => {
  const { t } = useTranslation();
  const [categories, setCategories] = useState<Category[]>([]);
  const [banners, setBanners] = useState<Banner[]>([]);
  const [businessConfig, setBusinessConfig] = useState<BusinessConfig>({});
  const [languages, setLanguages] = useState<SupportedLanguage[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [refreshing, setRefreshing] = useState(false);

  const fetchData = async () => {
    try {
      setError(null);
      const [categoriesData, bannersData, configData, languagesData] = await Promise.all([
        categoryService.getAllCategories(),
        bannerService.getActiveBanners(),
        configService.getBusinessConfig(),
        languageService.getActiveLanguages(),
      ]);
      setCategories(categoriesData);
      setBanners(bannersData);
      setBusinessConfig(configData);
      setLanguages(languagesData);
    } catch (err: any) {
      console.error('Failed to fetch data:', err);
      setError(t('common.error_loading'));
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const onRefresh = useCallback(() => {
    setRefreshing(true);
    fetchData();
  }, []);

  return {
    categories,
    banners,
    businessConfig,
    languages,
    loading,
    error,
    refreshing,
    fetchData,
    onRefresh,
  };
};

export default useHomeData;
