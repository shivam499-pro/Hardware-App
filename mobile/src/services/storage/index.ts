/**
 * Storage Service
 * Handles local storage operations using AsyncStorage
 */

import AsyncStorage from '@react-native-async-storage/async-storage';
import { STORAGE_KEYS } from '../../app/config';

// Storage keys type
type StorageKey = typeof STORAGE_KEYS[keyof typeof STORAGE_KEYS];

// Get item from storage
export const getItem = async <T>(key: StorageKey): Promise<T | null> => {
  try {
    const item = await AsyncStorage.getItem(key);
    return item ? JSON.parse(item) : null;
  } catch (error) {
    console.error(`Error getting item ${key} from storage:`, error);
    return null;
  }
};

// Set item in storage
export const setItem = async <T>(key: StorageKey, value: T): Promise<boolean> => {
  try {
    await AsyncStorage.setItem(key, JSON.stringify(value));
    return true;
  } catch (error) {
    console.error(`Error setting item ${key} in storage:`, error);
    return false;
  }
};

// Remove item from storage
export const removeItem = async (key: StorageKey): Promise<boolean> => {
  try {
    await AsyncStorage.removeItem(key);
    return true;
  } catch (error) {
    console.error(`Error removing item ${key} from storage:`, error);
    return false;
  }
};

// Clear all storage
export const clearAll = async (): Promise<boolean> => {
  try {
    await AsyncStorage.clear();
    return true;
  } catch (error) {
    console.error('Error clearing storage:', error);
    return false;
  }
};

// Get multiple items
export const getMultipleItems = async (keys: StorageKey[]): Promise<Record<string, any>> => {
  try {
    const pairs = await AsyncStorage.multiGet(keys);
    const result: Record<string, any> = {};
    pairs.forEach(([key, value]) => {
      if (value) {
        result[key] = JSON.parse(value);
      }
    });
    return result;
  } catch (error) {
    console.error('Error getting multiple items from storage:', error);
    return {};
  }
};

// Set multiple items
export const setMultipleItems = async (items: Record<StorageKey, any>): Promise<boolean> => {
  try {
    const pairs: [string, string][] = Object.entries(items).map(([key, value]) => [
      key,
      JSON.stringify(value),
    ]);
    await AsyncStorage.multiSet(pairs);
    return true;
  } catch (error) {
    console.error('Error setting multiple items in storage:', error);
    return false;
  }
};

// Auth token helpers
export const getAuthToken = (): Promise<string | null> => {
  return getItem<string>(STORAGE_KEYS.AUTH_TOKEN);
};

export const setAuthToken = (token: string): Promise<boolean> => {
  return setItem(STORAGE_KEYS.AUTH_TOKEN, token);
};

export const removeAuthToken = (): Promise<boolean> => {
  return removeItem(STORAGE_KEYS.AUTH_TOKEN);
};

// Language helpers
export const getLanguage = (): Promise<string | null> => {
  return getItem<string>(STORAGE_KEYS.USER_LANGUAGE);
};

export const setLanguage = (language: string): Promise<boolean> => {
  return setItem(STORAGE_KEYS.USER_LANGUAGE, language);
};

// Cache helpers
export const getCachedData = <T>(key: StorageKey): Promise<T | null> => {
  return getItem<T>(key);
};

export const setCachedData = <T>(key: StorageKey, data: T): Promise<boolean> => {
  return setItem(key, data);
};

export const getLastSync = (): Promise<string | null> => {
  return getItem<string>(STORAGE_KEYS.LAST_SYNC);
};

export const setLastSync = (timestamp: string): Promise<boolean> => {
  return setItem(STORAGE_KEYS.LAST_SYNC, timestamp);
};

export default {
  getItem,
  setItem,
  removeItem,
  clearAll,
  getMultipleItems,
  setMultipleItems,
  getAuthToken,
  setAuthToken,
  removeAuthToken,
  getLanguage,
  setLanguage,
  getCachedData,
  setCachedData,
  getLastSync,
  setLastSync,
};
