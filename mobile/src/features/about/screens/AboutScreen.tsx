import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
} from 'react-native';

const AboutScreen: React.FC = () => {
  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>About Manish Hardware</Text>
      </View>

      <View style={styles.content}>
        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Our Story</Text>
          <Text style={styles.text}>
            Manish Hardware has been serving the construction and hardware needs of Kathmandu and surrounding areas for over 15 years. We pride ourselves on providing quality materials and excellent customer service.
          </Text>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>What We Offer</Text>
          <Text style={styles.text}>
            • High-quality cement and concrete products{'\n'}
            • Steel and TMT bars for construction{'\n'}
            • Bricks, sand, and aggregates{'\n'}
            • Plumbing and electrical materials{'\n'}
            • Tools and hardware supplies{'\n'}
            • Paints and finishing materials
          </Text>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Why Choose Us?</Text>
          <Text style={styles.text}>
            ✓ Competitive pricing with daily market rates{'\n'}
            ✓ Fast and reliable delivery{'\n'}
            ✓ Payment after delivery for your convenience{'\n'}
            ✓ Expert advice from experienced professionals{'\n'}
            ✓ Quality assurance on all products{'\n'}
            ✓ Serving contractors, builders, and homeowners
          </Text>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Our Experience</Text>
          <Text style={styles.text}>
            With 15+ years in the hardware business, we understand the construction industry and the importance of reliable suppliers. Our team consists of experienced professionals who can guide you in selecting the right materials for your project.
          </Text>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Contact Us</Text>
          <Text style={styles.text}>
            Ready to start your project? Contact us today for competitive quotes and fast delivery. We look forward to serving your construction needs.
          </Text>
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
    marginBottom: 15,
  },
  text: {
    fontSize: 16,
    color: '#34495e',
    lineHeight: 24,
  },
});

export default AboutScreen;