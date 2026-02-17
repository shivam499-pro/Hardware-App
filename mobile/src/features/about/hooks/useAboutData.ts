/**
 * About Feature Hooks
 */

import { useState, useEffect } from 'react';
import { configService, templateService } from '../../../services/api';
import { AboutData } from '../types';

interface UseAboutDataResult {
  aboutData: AboutData;
  loading: boolean;
  error: string | null;
  fetchAboutData: () => Promise<void>;
}

const DEFAULT_ABOUT_DATA: AboutData = {
  businessName: 'Manish Hardware',
  aboutContent: 'Manish Hardware has been serving the construction and hardware needs of Kathmandu and surrounding areas for over 15 years. We pride ourselves on providing quality materials and excellent customer service.',
  services: [
    'High-quality cement and concrete products',
    'Steel and TMT bars for construction',
    'Bricks, sand, and aggregates',
    'Plumbing and electrical materials',
    'Tools and hardware supplies',
    'Paints and finishing materials',
  ],
  whyChooseUs: [
    'Competitive pricing with daily market rates',
    'Fast and reliable delivery',
    'Payment after delivery for your convenience',
    'Expert advice from experienced professionals',
    'Quality assurance on all products',
    'Serving contractors, builders, and homeowners',
  ],
  experience: 'With 15+ years in the hardware business, we understand the construction industry and the importance of reliable suppliers. Our team consists of experienced professionals who can guide you in selecting the right materials for your project.',
};

export const useAboutData = (): UseAboutDataResult => {
  const [aboutData, setAboutData] = useState<AboutData>(DEFAULT_ABOUT_DATA);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchAboutData = async () => {
    try {
      setLoading(true);
      
      // Fetch business config
      const config = await configService.getBusinessConfig();
      
      // Try to fetch about template if available
      let aboutContent = DEFAULT_ABOUT_DATA.aboutContent;
      try {
        const templateContent = await templateService.getTemplateContent('about', 'en');
        if (templateContent) {
          aboutContent = templateContent;
        }
      } catch (templateError) {
        // Template not found, use default content
        console.log('About template not found, using default content');
      }

      setAboutData({
        businessName: config.business_name || DEFAULT_ABOUT_DATA.businessName,
        aboutContent: aboutContent,
        services: DEFAULT_ABOUT_DATA.services,
        whyChooseUs: DEFAULT_ABOUT_DATA.whyChooseUs,
        experience: DEFAULT_ABOUT_DATA.experience,
      });
    } catch (err) {
      console.error('Failed to fetch about data:', err);
      setError('Failed to load about information.');
      // Keep default values if fetch fails
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAboutData();
  }, []);

  return {
    aboutData,
    loading,
    error,
    fetchAboutData,
  };
};

export default useAboutData;
