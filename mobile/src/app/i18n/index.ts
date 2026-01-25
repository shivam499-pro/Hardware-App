import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import AsyncStorage from '@react-native-async-storage/async-storage';
import * as RNLocalize from 'react-native-localize';

import en from './locales/en.json';
import ne from './locales/ne.json';

const LANGUAGES = {
  en: {
    translation: en,
    name: 'English',
  },
  ne: {
    translation: ne,
    name: 'नेपाली',
  },
};

const LANG_CODES = Object.keys(LANGUAGES);

const LANGUAGE_DETECTOR = {
  type: 'languageDetector' as const,
  async: true,
  detect: async (callback: (lang: string) => void) => {
    try {
      const language = await AsyncStorage.getItem('user-language');
      if (language) {
        callback(language);
      } else {
        // Default to English
        callback('en');
      }
    } catch (error) {
      console.log('Error fetching language from storage', error);
      callback('en');
    }
  },
  init: () => {},
  cacheUserLanguage: (language: string) => {
    AsyncStorage.setItem('user-language', language);
  },
};

i18n
  .use(LANGUAGE_DETECTOR)
  .use(initReactI18next)
  .init({
    fallbackLng: 'en',
    debug: __DEV__,
    resources: LANGUAGES,
    interpolation: {
      escapeValue: false,
    },
  });

export default i18n;