import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Linking,
  ActivityIndicator,
} from 'react-native';
import { useContactConfig } from '../hooks/useContactConfig';

const ContactScreen: React.FC = () => {
  const { config, loading, error } = useContactConfig();

  const handleCall = () => {
    const phone = config.phone_number || config.whatsapp_number;
    if (phone) {
      Linking.openURL(`tel:${phone}`);
    }
  };

  const handleWhatsApp = () => {
    const whatsapp = config.whatsapp_number || config.phone_number;
    if (whatsapp) {
      const message = 'Hello, I need information about your products.';
      Linking.openURL(`whatsapp://send?phone=${whatsapp.replace(/\D/g, '')}&text=${encodeURIComponent(message)}`);
    }
  };

  const handleMap = () => {
    const address = config.address || 'Kathmandu, Nepal';
    Linking.openURL(`https://maps.google.com/?q=${encodeURIComponent(address)}`);
  };

  if (loading) {
    return (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color="#e74c3c" />
        <Text style={styles.loadingText}>Loading contact information...</Text>
      </View>
    );
  }

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Contact & Location</Text>
      </View>

      <View style={styles.content}>
        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Phone</Text>
          <Text style={styles.info}>{config.phone_number || '+977-1234567890'}</Text>
          <TouchableOpacity style={styles.button} onPress={handleCall}>
            <Text style={styles.buttonText}>Call Now</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>WhatsApp</Text>
          <Text style={styles.info}>{config.whatsapp_number || config.phone_number || '+977-1234567890'}</Text>
          <TouchableOpacity style={[styles.button, styles.whatsappButton]} onPress={handleWhatsApp}>
            <Text style={styles.buttonText}>Message on WhatsApp</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Address</Text>
          <Text style={styles.info}>{config.address || 'Kathmandu, Nepal'}</Text>
          <TouchableOpacity style={[styles.button, styles.mapButton]} onPress={handleMap}>
            <Text style={styles.buttonText}>View on Map</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Business Hours</Text>
          <Text style={styles.info}>{config.business_hours || 'Sunday - Friday: 9:00 AM - 6:00 PM'}</Text>
          <Text style={styles.info}>Saturday: 9:00 AM - 4:00 PM</Text>
        </View>

        {config.business_email && (
          <View style={styles.section}>
            <Text style={styles.sectionTitle}>Email</Text>
            <Text style={styles.info}>{config.business_email}</Text>
            <TouchableOpacity 
              style={[styles.button, styles.emailButton]} 
              onPress={() => Linking.openURL(`mailto:${config.business_email}`)}
            >
              <Text style={styles.buttonText}>Send Email</Text>
            </TouchableOpacity>
          </View>
        )}
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f5f5f5',
  },
  loadingText: {
    marginTop: 10,
    fontSize: 16,
    color: '#7f8c8d',
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
  },
  content: {
    padding: 20,
  },
  section: {
    backgroundColor: '#fff',
    padding: 20,
    marginBottom: 15,
    borderRadius: 8,
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#2c3e50',
    marginBottom: 10,
  },
  info: {
    fontSize: 16,
    color: '#34495e',
    marginBottom: 15,
  },
  button: {
    backgroundColor: '#e74c3c',
    paddingVertical: 12,
    paddingHorizontal: 20,
    borderRadius: 8,
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  whatsappButton: {
    backgroundColor: '#27ae60',
  },
  mapButton: {
    backgroundColor: '#3498db',
  },
  emailButton: {
    backgroundColor: '#9b59b6',
  },
  buttonText: {
    color: '#fff',
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 16,
  },
});

export default ContactScreen;
