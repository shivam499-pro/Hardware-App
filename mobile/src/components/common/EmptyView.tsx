/**
 * Common Components - EmptyView Component
 */

import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { colors, typography, spacing } from '../../app/theme';

interface EmptyViewProps {
  message: string;
  icon?: string;
}

export const EmptyView: React.FC<EmptyViewProps> = ({
  message,
  icon = '📭',
}) => {
  return (
    <View style={styles.container}>
      <Text style={styles.icon}>{icon}</Text>
      <Text style={styles.message}>{message}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    paddingVertical: spacing.xxxl,
  },
  icon: {
    fontSize: 48,
    marginBottom: spacing.lg,
  },
  message: {
    fontSize: typography.fontSize.lg,
    color: colors.text.secondary,
    textAlign: 'center',
  },
});

export default EmptyView;
