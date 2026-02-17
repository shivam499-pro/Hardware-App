/**
 * UI Components - Card Component
 */

import React from 'react';
import { View, Text, StyleSheet, ViewStyle } from 'react-native';
import { colors, spacing, borderRadius, shadows, typography } from '../../app/theme';

interface CardProps {
  children: React.ReactNode;
  title?: string;
  style?: ViewStyle;
  noPadding?: boolean;
}

export const Card: React.FC<CardProps> = ({
  children,
  title,
  style,
  noPadding = false,
}) => {
  return (
    <View style={[styles.card, noPadding && styles.noPadding, style]}>
      {title && <Text style={styles.title}>{title}</Text>}
      {children}
    </View>
  );
};

const styles = StyleSheet.create({
  card: {
    backgroundColor: colors.card,
    borderRadius: borderRadius.md,
    padding: spacing.lg,
    ...shadows.small,
  },
  noPadding: {
    padding: 0,
  },
  title: {
    fontSize: typography.fontSize.xl,
    fontWeight: 'bold',
    color: colors.text.primary,
    marginBottom: spacing.md,
  },
});

export default Card;
