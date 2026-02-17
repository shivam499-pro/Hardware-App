/**
 * Common Components - ErrorView Component
 */

import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import { colors, typography, spacing, borderRadius } from '../../app/theme';

interface ErrorViewProps {
  message: string;
  onRetry?: () => void;
  retryText?: string;
}

export const ErrorView: React.FC<ErrorViewProps> = ({
  message,
  onRetry,
  retryText = 'Retry',
}) => {
  return (
    <View style={styles.container}>
      <Text style={styles.errorIcon}>⚠️</Text>
      <Text style={styles.message}>{message}</Text>
      {onRetry && (
        <TouchableOpacity style={styles.retryButton} onPress={onRetry}>
          <Text style={styles.retryText}>{retryText}</Text>
        </TouchableOpacity>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: colors.background,
    padding: spacing.xxl,
  },
  errorIcon: {
    fontSize: 48,
    marginBottom: spacing.lg,
  },
  message: {
    fontSize: typography.fontSize.lg,
    color: colors.text.primary,
    textAlign: 'center',
    marginBottom: spacing.xl,
  },
  retryButton: {
    backgroundColor: colors.primary,
    paddingVertical: spacing.md,
    paddingHorizontal: spacing.xl,
    borderRadius: borderRadius.md,
  },
  retryText: {
    color: colors.text.white,
    fontSize: typography.fontSize.lg,
    fontWeight: 'bold',
  },
});

export default ErrorView;
