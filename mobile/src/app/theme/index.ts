/**
 * App Theme
 * Central theme configuration for the mobile app
 */

import { StyleSheet } from 'react-native';

// Colors
export const colors = {
  primary: '#2c3e50',
  primaryLight: '#34495e',
  primaryDark: '#1a252f',
  
  secondary: '#e74c3c',
  secondaryLight: '#ec7063',
  secondaryDark: '#c0392b',
  
  success: '#27ae60',
  successLight: '#58d68d',
  successDark: '#1e8449',
  
  warning: '#f39c12',
  warningLight: '#f5b041',
  warningDark: '#d68910',
  
  error: '#e74c3c',
  errorLight: '#ec7063',
  errorDark: '#c0392b',
  
  whatsapp: '#25D366',
  whatsappLight: '#128C7E',
  whatsappDark: '#075E54',
  
  background: '#f5f5f5',
  backgroundDark: '#e0e0e0',
  
  card: '#ffffff',
  cardDark: '#f8f8f8',
  
  text: {
    primary: '#2c3e50',
    secondary: '#7f8c8d',
    light: '#bdc3c7',
    white: '#ffffff',
    error: '#e74c3c',
    success: '#27ae60',
  },
  
  border: '#e0e0e0',
  borderDark: '#bdbdbd',
  
  shadow: 'rgba(0, 0, 0, 0.1)',
  overlay: 'rgba(0, 0, 0, 0.5)',
};

// Typography
export const typography = {
  fontFamily: {
    regular: 'System',
    medium: 'System',
    bold: 'System',
  },
  fontSize: {
    xs: 10,
    sm: 12,
    md: 14,
    lg: 16,
    xl: 18,
    xxl: 24,
    xxxl: 32,
  },
  lineHeight: {
    tight: 1.2,
    normal: 1.5,
    relaxed: 1.75,
  },
};

// Spacing
export const spacing = {
  xs: 4,
  sm: 8,
  md: 12,
  lg: 16,
  xl: 20,
  xxl: 24,
  xxxl: 32,
};

// Border Radius
export const borderRadius = {
  sm: 4,
  md: 8,
  lg: 12,
  xl: 16,
  xxl: 24,
  full: 9999,
};

// Shadows
export const shadows = {
  small: {
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.1,
    shadowRadius: 2,
    elevation: 2,
  },
  medium: {
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.15,
    shadowRadius: 4,
    elevation: 4,
  },
  large: {
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.2,
    shadowRadius: 8,
    elevation: 8,
  },
};

// Common Styles
export const commonStyles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.background,
  },
  card: {
    backgroundColor: colors.card,
    borderRadius: borderRadius.md,
    padding: spacing.lg,
    ...shadows.small,
  },
  row: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  spaceBetween: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  center: {
    justifyContent: 'center',
    alignItems: 'center',
  },
  textPrimary: {
    fontSize: typography.fontSize.lg,
    fontWeight: 'bold',
    color: colors.text.primary,
  },
  textSecondary: {
    fontSize: typography.fontSize.md,
    color: colors.text.secondary,
  },
  button: {
    paddingVertical: spacing.md,
    paddingHorizontal: spacing.xl,
    borderRadius: borderRadius.md,
    alignItems: 'center',
    justifyContent: 'center',
  },
  buttonText: {
    color: colors.text.white,
    fontWeight: 'bold',
    fontSize: typography.fontSize.lg,
  },
  input: {
    borderWidth: 1,
    borderColor: colors.border,
    borderRadius: borderRadius.md,
    padding: spacing.md,
    fontSize: typography.fontSize.lg,
    backgroundColor: colors.card,
  },
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: colors.background,
  },
  errorContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: colors.background,
    padding: spacing.xxl,
  },
  emptyContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    paddingVertical: spacing.xxxl,
  },
});

// Theme Object
export const theme = {
  colors,
  typography,
  spacing,
  borderRadius,
  shadows,
  commonStyles,
};

export default theme;
