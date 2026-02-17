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
import { RouteProp } from '@react-navigation/native';
import { RootStackParamList } from '../../../navigation';
import { quoteService, templateService, configService } from '../../../services/api';

type RequestQuoteScreenNavigationProp = StackNavigationProp<RootStackParamList, 'RequestQuote'>;
type RequestQuoteScreenRouteProp = RouteProp<RootStackParamList, 'RequestQuote'>;

interface Props {
  navigation: RequestQuoteScreenNavigationProp;
  route: RequestQuoteScreenRouteProp;
}

const RequestQuoteScreen: React.FC<Props> = ({ navigation, route }) => {
  const { productId, productName } = route.params || {};

  const [form, setForm] = useState({
    name: '',
    phone: '',
    product: productName || '',
    quantity: '',
    location: '',
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
    if (!form.product.trim()) {
      Alert.alert('Error', 'Please enter product name');
      return false;
    }
    if (!form.quantity.trim()) {
      Alert.alert('Error', 'Please enter quantity');
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

    setLoading(true);

    try {
      // Submit quote request to backend
      const quoteData = {
        name: form.name,
        phone: form.phone,
        productId: productId,
        quantity: form.quantity,
        location: form.location,
        languageCode: 'en', // Default language
      };

      await quoteService.submitQuoteRequest(quoteData);

      // Get WhatsApp number from config and render template
      let whatsappMessage = `Hello, I want to inquire about ${form.product} for ${form.quantity}. Name: ${form.name}, Phone: ${form.phone}, Location: ${form.location}`;
      
      try {
        const config = await configService.getBusinessConfig();
        const renderedTemplate = await templateService.renderWhatsAppQuoteTemplate('en', {
          product: form.product,
          quantity: form.quantity,
          name: form.name,
          location: form.location,
        });
        whatsappMessage = renderedTemplate || whatsappMessage;
      } catch (templateError) {
        console.log('Using default message format');
      }

      // Show success message
      Alert.alert(
        'Success',
        'Quote request submitted successfully! We will contact you soon.',
        [
          {
            text: 'OK',
            onPress: () => {
              navigation.goBack();
            },
          },
        ]
      );
    } catch (error: any) {
      const errorMessage = error?.response?.data?.message || 'Failed to submit quote request. Please try again.';
      Alert.alert('Error', errorMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Request Quote</Text>
        <Text style={styles.subtitle}>
          Fill in the details below and we'll get back to you with pricing and availability.
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
          <Text style={styles.label}>Product *</Text>
          <TextInput
            style={styles.input}
            value={form.product}
            onChangeText={(value) => handleInputChange('product', value)}
            placeholder="Enter product name"
          />
        </View>

        <View style={styles.inputGroup}>
          <Text style={styles.label}>Quantity *</Text>
          <TextInput
            style={styles.input}
            value={form.quantity}
            onChangeText={(value) => handleInputChange('quantity', value)}
            placeholder="e.g., 10 bags, 5 tons"
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
            <Text style={styles.submitButtonText}>Submit Quote Request</Text>
          )}
        </TouchableOpacity>

        <Text style={styles.note}>
          * Required fields. We'll contact you via WhatsApp or phone with pricing and delivery details.
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
});

export default RequestQuoteScreen;