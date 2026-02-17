/**
 * UI Components - Button Component
 */

import React from 'react';
import {
  TouchableOpacity,
  Text,
  StyleSheet,
  ActivityIndicator,
  ViewStyle,
  TextStyle,
} from 'react-native';
import { colors, typography, spacing, borderRadius } from '../../app/theme';

type ButtonVariant = 'primary' | 'secondary' | 'success' | 'warning' | 'error' | 'whatsapp' | 'outline';
type ButtonSize = 'small' | 'medium' | 'large';

interface ButtonProps {
  title: string;
  onPress: () => void;
  variant?: ButtonVariant;
  size?: ButtonSize;
  disabled?: boolean;
  loading?: boolean;
  style?: ViewStyle;
  textStyle?: TextStyle;
  icon?: React.ReactNode;
}

const getButtonColors = (variant: ButtonVariant) => {
  switch (variant) {
    case 'secondary':
      return { bg: colors.secondary, text: colors.text.white };
    case 'success':
      return { bg: colors.success, text: colors.text.white };
    case 'warning':
      return { bg: colors.warning, text: colors.text.white };
    case 'error':
      return { bg: colors.error, text: colors.text.white };
    case 'whatsapp':
      return { bg: colors.whatsapp, text: colors.text.white };
    case 'outline':
      return { bg: 'transparent', text: colors.primary };
    default:
      return { bg: colors.primary, text: colors.text.white };
  }
};

const getButtonPadding = (size: ButtonSize) => {
  switch (size) {
    case 'small':
      return { py: spacing.sm, px: spacing.md };
    case 'large':
      return { py: spacing.lg, px: spacing.xxl };
    default:
      return { py: spacing.md, px: spacing.xl };
  }
};

export const Button: React.FC<ButtonProps> = ({
  title,
  onPress,
  variant = 'primary',
  size = 'medium',
  disabled = false,
  loading = false,
  style,
  textStyle,
  icon,
}) => {
  const buttonColors = getButtonColors(variant);
  const buttonPadding = getButtonPadding(size);

  const isOutline = variant === 'outline';

  return (
    <TouchableOpacity
      style={[
        styles.button,
        {
          backgroundColor: buttonColors.bg,
          paddingVertical: buttonPadding.py,
          paddingHorizontal: buttonPadding.px,
          borderWidth: isOutline ? 1 : 0,
          borderColor: isOutline ? colors.primary : 'transparent',
        },
        disabled && styles.disabled,
        style,
      ]}
      onPress={onPress}
      disabled={disabled || loading}
      activeOpacity={0.7}
    >
      {loading ? (
        <ActivityIndicator color={buttonColors.text} size="small" />
      ) : (
        <>
          {icon}
          <Text
            style={[
              styles.text,
              { color: buttonColors.text },
              size === 'small' && styles.textSmall,
              size === 'large' && styles.textLarge,
              textStyle,
            ]}
          >
            {title}
          </Text>
        </>
      )}
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  button: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: borderRadius.md,
    minWidth: 100,
  },
  disabled: {
    opacity: 0.5,
  },
  text: {
    fontSize: typography.fontSize.lg,
    fontWeight: 'bold',
    textAlign: 'center',
  },
  textSmall: {
    fontSize: typography.fontSize.md,
  },
  textLarge: {
    fontSize: typography.fontSize.xl,
  },
});

export default Button;
