import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Linking,
} from 'react-native';

const ContactScreen: React.FC = () => {
  const handleCall = () => {
    Linking.openURL('tel:+977-1234567890');
  };

  const handleWhatsApp = () => {
    const message = 'Hello, I need information about your products.';
    Linking.openURL(`whatsapp://send?phone=+977-1234567890&text=${encodeURIComponent(message)}`);
  };

  const handleMap = () => {
    const address = 'Kathmandu, Nepal';
    Linking.openURL(`https://maps.google.com/?q=${encodeURIComponent(address)}`);
  };

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Contact & Location</Text>
      </View>

      <View style={styles.content}>
        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Phone</Text>
          <Text style={styles.info}>+977-1234567890</Text>
          <TouchableOpacity style={styles.button} onPress={handleCall}>
            <Text style={styles.buttonText}>Call Now</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>WhatsApp</Text>
          <Text style={styles.info}>+977-1234567890</Text>
          <TouchableOpacity style={[styles.button, styles.whatsappButton]} onPress={handleWhatsApp}>
            <Text style={styles.buttonText}>Message on WhatsApp</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Address</Text>
          <Text style={styles.info}>Kathmandu, Nepal</Text>
          <TouchableOpacity style={[styles.button, styles.mapButton]} onPress={handleMap}>
            <Text style={styles.buttonText}>View on Map</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Business Hours</Text>
          <Text style={styles.info}>Sunday - Friday: 9:00 AM - 6:00 PM</Text>
          <Text style={styles.info}>Saturday: 9:00 AM - 4:00 PM</Text>
        </View>
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
  buttonText: {
    color: '#fff',
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 16,
  },
});

export default ContactScreen;