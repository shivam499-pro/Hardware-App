/**
 * External Services
 * Handles external links and integrations (WhatsApp, Phone, Maps, Email)
 */

import { Linking, Platform } from 'react-native';

// Open phone dialer
export const openPhoneDialer = (phoneNumber: string): Promise<boolean> => {
  const cleaned = phoneNumber.replace(/\D/g, '');
  const url = `tel:${cleaned}`;
  
  return Linking.canOpenURL(url)
    .then((supported) => {
      if (supported) {
        return Linking.openURL(url).then(() => true);
      }
      return false;
    })
    .catch((error) => {
      console.error('Error opening phone dialer:', error);
      return false;
    });
};

// Open WhatsApp with phone number and message
export const openWhatsApp = (
  phoneNumber: string,
  message?: string
): Promise<boolean> => {
  const cleaned = phoneNumber.replace(/\D/g, '');
  const encodedMessage = message ? encodeURIComponent(message) : '';
  
  // WhatsApp URL scheme
  const url = Platform.select({
    ios: `whatsapp://send?phone=${cleaned}&text=${encodedMessage}`,
    android: `whatsapp://send?phone=${cleaned}&text=${encodedMessage}`,
    default: `https://wa.me/${cleaned}?text=${encodedMessage}`,
  });

  return Linking.canOpenURL(url || '')
    .then((supported) => {
      if (supported) {
        return Linking.openURL(url || '').then(() => true);
      }
      // Fallback to web WhatsApp
      const webUrl = `https://wa.me/${cleaned}?text=${encodedMessage}`;
      return Linking.openURL(webUrl).then(() => true);
    })
    .catch((error) => {
      console.error('Error opening WhatsApp:', error);
      return false;
    });
};

// Open maps with address
export const openMaps = (address: string): Promise<boolean> => {
  const encodedAddress = encodeURIComponent(address);
  
  const url = Platform.select({
    ios: `maps:?q=${encodedAddress}`,
    android: `geo:0,0?q=${encodedAddress}`,
    default: `https://maps.google.com/?q=${encodedAddress}`,
  });

  return Linking.canOpenURL(url || '')
    .then((supported) => {
      if (supported) {
        return Linking.openURL(url || '').then(() => true);
      }
      // Fallback to Google Maps web
      const webUrl = `https://maps.google.com/?q=${encodedAddress}`;
      return Linking.openURL(webUrl).then(() => true);
    })
    .catch((error) => {
      console.error('Error opening maps:', error);
      return false;
    });
};

// Open maps with coordinates
export const openMapsWithCoords = (
  latitude: number,
  longitude: number,
  label?: string
): Promise<boolean> => {
  const url = Platform.select({
    ios: `maps:?ll=${latitude},${longitude}${label ? `&q=${encodeURIComponent(label)}` : ''}`,
    android: `geo:${latitude},${longitude}${label ? `?q=${encodeURIComponent(label)}` : ''}`,
    default: `https://maps.google.com/?q=${latitude},${longitude}`,
  });

  return Linking.canOpenURL(url || '')
    .then((supported) => {
      if (supported) {
        return Linking.openURL(url || '').then(() => true);
      }
      const webUrl = `https://maps.google.com/?q=${latitude},${longitude}`;
      return Linking.openURL(webUrl).then(() => true);
    })
    .catch((error) => {
      console.error('Error opening maps with coordinates:', error);
      return false;
    });
};

// Open email client
export const openEmail = (
  email: string,
  subject?: string,
  body?: string
): Promise<boolean> => {
  const url = `mailto:${email}?subject=${encodeURIComponent(subject || '')}&body=${encodeURIComponent(body || '')}`;

  return Linking.canOpenURL(url)
    .then((supported) => {
      if (supported) {
        return Linking.openURL(url).then(() => true);
      }
      return false;
    })
    .catch((error) => {
      console.error('Error opening email:', error);
      return false;
    });
};

// Open URL in browser
export const openURL = (url: string): Promise<boolean> => {
  return Linking.canOpenURL(url)
    .then((supported) => {
      if (supported) {
        return Linking.openURL(url).then(() => true);
      }
      return false;
    })
    .catch((error) => {
      console.error('Error opening URL:', error);
      return false;
    });
};

// Check if WhatsApp is installed
export const isWhatsAppInstalled = (): Promise<boolean> => {
  return Linking.canOpenURL('whatsapp://send').catch(() => false);
};

// Share content
export const shareContent = async (
  title: string,
  message: string,
  url?: string
): Promise<boolean> => {
  try {
    // For React Native, you would typically use the Share API
    // This is a simplified version using WhatsApp
    const shareMessage = url ? `${message}\n${url}` : message;
    return openWhatsApp('', shareMessage);
  } catch (error) {
    console.error('Error sharing content:', error);
    return false;
  }
};

export default {
  openPhoneDialer,
  openWhatsApp,
  openMaps,
  openMapsWithCoords,
  openEmail,
  openURL,
  isWhatsAppInstalled,
  shareContent,
};
