import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TextInput,
  TouchableOpacity,
  Alert,
  ActivityIndicator,
} from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { useSelector, useDispatch } from 'react-redux';
import { RootStackParamList } from '../../../navigation';
import { quoteService } from '../../../services/api';
import { RootState } from '../../../store';
import { clearCart } from '../../../store/slices/cartSlice';

type CartCheckoutScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Home'>; // Safe fallback

interface Props {
  navigation: CartCheckoutScreenNavigationProp;
}

const CartCheckoutScreen: React.FC<Props> = ({ navigation }) => {
  const cartItems = useSelector((state: RootState) => state.cart.items);
  const authState = useSelector((state: RootState) => state.auth);
  
  const isAuthenticated = authState?.isAuthenticated || false;
  const phone = authState?.phone || '';
  const fullName = authState?.fullName || '';
  const location = authState?.location || '';
  
  const dispatch = useDispatch();

  const [form, setForm] = useState({
    name: isAuthenticated && fullName ? fullName : '',
    phone: isAuthenticated && phone ? phone : '',
    location: isAuthenticated && location ? location : '',
  });
  const [loading, setLoading] = useState(false);

  const handleInputChange = (field: string, value: string) => {
    setForm(prev => ({ ...prev, [field]: value }));
  };

  const validateForm = () => {
    if (!form.name.trim()) {
      Alert.alert('Error', 'Please enter your name');
      return false;
    }
    if (!form.phone.trim()) {
      Alert.alert('Error', 'Please enter your phone number');
      return false;
    }
    if (!form.location.trim()) {
      Alert.alert('Error', 'Please enter delivery location');
      return false;
    }
    return true;
  };

  const handleSubmit = async () => {
    if (!validateForm()) return;

    if (cartItems.length === 0) {
      Alert.alert('Error', 'Your cart is empty.');
      return;
    }

    setLoading(true);

    try {
      // Loop over cart and submit individually to respect backend architecture
      const promises = cartItems.map(item => {
        return quoteService.submitQuoteRequest({
          name: form.name,
          phone: form.phone,
          productId: item.productId,
          quantity: item.quantity,
          location: form.location,
          languageCode: 'en',
        });
      });

      await Promise.all(promises);

      dispatch(clearCart());

      Alert.alert(
        'Success',
        'Bulk Quote request submitted successfully! We will contact you soon.',
        [
          {
            text: 'OK',
            onPress: () => {
              // Navigate back to the home tab or screen list
              navigation.navigate('Home'); 
            },
          },
        ]
      );
    } catch (error: any) {
      const errorMessage = error?.response?.data?.message || 'Failed to submit quote requests. Please try again.';
      Alert.alert('Error', errorMessage);
    } finally {
      setLoading(false);
    }
  };

  if (cartItems.length === 0) {
    return (
      <View style={styles.emptyContainer}>
        <Text style={styles.errorText}>No items to checkout.</Text>
      </View>
    );
  }

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Quote Checkout</Text>
        <Text style={styles.subtitle}>
          You are requesting a quote for {cartItems.length} items. Please provide your delivery details.
        </Text>
      </View>

      <View style={styles.form}>
        <View style={styles.inputGroup}>
          <Text style={styles.label}>Name *</Text>
          <TextInput
            style={styles.input}
            value={form.name}
            onChangeText={(value) => handleInputChange('name', value)}
            placeholder="Enter your full name"
          />
        </View>

        <View style={styles.inputGroup}>
          <Text style={styles.label}>Phone Number *</Text>
          <TextInput
            style={styles.input}
            value={form.phone}
            onChangeText={(value) => handleInputChange('phone', value)}
            placeholder="Enter your phone number"
            keyboardType="phone-pad"
          />
        </View>

        <View style={styles.inputGroup}>
          <Text style={styles.label}>Delivery Location *</Text>
          <TextInput
            style={[styles.input, styles.textArea]}
            value={form.location}
            onChangeText={(value) => handleInputChange('location', value)}
            placeholder="Enter delivery address"
            multiline
            numberOfLines={3}
          />
        </View>

        <TouchableOpacity
          style={[styles.submitButton, loading && styles.disabledButton]}
          onPress={handleSubmit}
          disabled={loading}
        >
          {loading ? (
            <ActivityIndicator color="#fff" />
          ) : (
            <Text style={styles.submitButtonText}>Submit Bulk Request</Text>
          )}
        </TouchableOpacity>

        <Text style={styles.note}>
          * Required fields. We'll contact you via WhatsApp or phone with pricing and delivery details for all your items.
        </Text>
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  emptyContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  header: {
    backgroundColor: '#fff',
    padding: 20,
    marginBottom: 10,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#2c3e50',
    marginBottom: 10,
  },
  subtitle: {
    fontSize: 16,
    color: '#7f8c8d',
    lineHeight: 24,
  },
  form: {
    padding: 20,
  },
  inputGroup: {
    marginBottom: 20,
  },
  label: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#2c3e50',
    marginBottom: 5,
  },
  input: {
    backgroundColor: '#fff',
    borderWidth: 1,
    borderColor: '#ddd',
    borderRadius: 8,
    padding: 12,
    fontSize: 16,
  },
  textArea: {
    height: 80,
    textAlignVertical: 'top',
  },
  submitButton: {
    backgroundColor: '#27ae60',
    paddingVertical: 15,
    borderRadius: 8,
    marginTop: 20,
    marginBottom: 10,
  },
  disabledButton: {
    backgroundColor: '#95a5a6',
  },
  submitButtonText: {
    color: '#fff',
    textAlign: 'center',
    fontSize: 18,
    fontWeight: 'bold',
  },
  note: {
    fontSize: 14,
    color: '#7f8c8d',
    fontStyle: 'italic',
    textAlign: 'center',
  },
  errorText: {
    fontSize: 18,
    color: '#e74c3c',
  },
});

export default CartCheckoutScreen;
