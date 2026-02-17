import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  ActivityIndicator,
} from 'react-native';
import { configService, templateService } from '../../../services/api';

interface AboutData {
  businessName: string;
  aboutContent: string;
  services: string[];
  whyChooseUs: string[];
  experience: string;
}

const AboutScreen: React.FC = () => {
  const [loading, setLoading] = useState(true);
  const [aboutData, setAboutData] = useState<AboutData>({
    businessName: 'Manish Hardware',
    aboutContent: 'Manish Hardware has been serving the construction and hardware needs of Kathmandu and surrounding areas for over 15 years. We pride ourselves on providing quality materials and excellent customer service.',
    services: [
      'High-quality cement and concrete products',
      'Steel and TMT bars for construction',
      'Bricks, sand, and aggregates',
      'Plumbing and electrical materials',
      'Tools and hardware supplies',
      'Paints and finishing materials',
    ],
    whyChooseUs: [
      'Competitive pricing with daily market rates',
      'Fast and reliable delivery',
      'Payment after delivery for your convenience',
      'Expert advice from experienced professionals',
      'Quality assurance on all products',
      'Serving contractors, builders, and homeowners',
    ],
    experience: 'With 15+ years in the hardware business, we understand the construction industry and the importance of reliable suppliers. Our team consists of experienced professionals who can guide you in selecting the right materials for your project.',
  });

  useEffect(() => {
    fetchAboutData();
  }, []);

  const fetchAboutData = async () => {
    try {
      setLoading(true);
      
      // Fetch business config
      const config = await configService.getBusinessConfig();
      
      // Try to fetch about template if available
      let aboutContent = aboutData.aboutContent;
      try {
        const templateContent = await templateService.getTemplateContent('about', 'en');
        if (templateContent) {
          aboutContent = templateContent;
        }
      } catch (templateError) {
        // Template not found, use default content
        console.log('About template not found, using default content');
      }

      setAboutData({
        businessName: config.business_name || 'Manish Hardware',
        aboutContent: aboutContent,
        services: aboutData.services,
        whyChooseUs: aboutData.whyChooseUs,
        experience: aboutData.experience,
      });
    } catch (error) {
      console.error('Failed to fetch about data:', error);
      // Keep default values if fetch fails
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color="#e74c3c" />
        <Text style={styles.loadingText}>Loading about information...</Text>
      </View>
    );
  }

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>About {aboutData.businessName}</Text>
      </View>

      <View style={styles.content}>
        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Our Story</Text>
          <Text style={styles.text}>{aboutData.aboutContent}</Text>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>What We Offer</Text>
          <Text style={styles.text}>
            {aboutData.services.map((service, index) => `• ${service}`).join('\n')}
          </Text>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Why Choose Us?</Text>
          <Text style={styles.text}>
            {aboutData.whyChooseUs.map((reason, index) => `✓ ${reason}`).join('\n')}
          </Text>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Our Experience</Text>
          <Text style={styles.text}>{aboutData.experience}</Text>
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
    marginBottom: 15,
  },
  text: {
    fontSize: 16,
    color: '#34495e',
    lineHeight: 24,
  },
});

export default AboutScreen;
