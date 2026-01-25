import React, { useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  FlatList,
  Image,
  Dimensions,
} from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../../navigation';

type HomeScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Home'>;

interface Props {
  navigation: HomeScreenNavigationProp;
}

const { width } = Dimensions.get('window');

const HomeScreen: React.FC<Props> = ({ navigation }) => {
  // Mock data - will be replaced with API calls
  const banners = [
    { id: 1, image: 'https://via.placeholder.com/400x200?text=Banner+1' },
    { id: 2, image: 'https://via.placeholder.com/400x200?text=Banner+2' },
  ];

  const categories = [
    { id: 1, name: 'Cement', image: 'https://via.placeholder.com/100?text=Cement' },
    { id: 2, name: 'Steel', image: 'https://via.placeholder.com/100?text=Steel' },
    { id: 3, name: 'Bricks', image: 'https://via.placeholder.com/100?text=Bricks' },
  ];

  const handleCategoryPress = (category: typeof categories[0]) => {
    navigation.navigate('Category', {
      categoryId: category.id,
      categoryName: category.name,
    });
  };

  const handleCallPress = () => {
    // Implement phone call
    console.log('Call Now');
  };

  const handleWhatsAppPress = () => {
    // Implement WhatsApp
    console.log('WhatsApp Now');
  };

  const renderCategory = ({ item }: { item: typeof categories[0] }) => (
    <TouchableOpacity
      style={styles.categoryCard}
      onPress={() => handleCategoryPress(item)}
    >
      <Image source={{ uri: item.image }} style={styles.categoryImage} />
      <Text style={styles.categoryName}>{item.name}</Text>
    </TouchableOpacity>
  );

  return (
    <View style={styles.container}>
      <FlatList
        data={[{ key: 'content' }]}
        renderItem={() => (
          <View>
            {/* Language Toggle */}
            <View style={styles.languageToggle}>
              <TouchableOpacity style={styles.languageButton}>
                <Text style={styles.languageText}>EN</Text>
              </TouchableOpacity>
              <TouchableOpacity style={styles.languageButton}>
                <Text style={styles.languageText}>ने</Text>
              </TouchableOpacity>
            </View>

            {/* Quick Action Buttons */}
            <View style={styles.quickActions}>
              <TouchableOpacity style={styles.actionButton} onPress={handleCallPress}>
                <Text style={styles.actionButtonText}>Call Now</Text>
              </TouchableOpacity>
              <TouchableOpacity style={styles.actionButton} onPress={handleWhatsAppPress}>
                <Text style={styles.actionButtonText}>WhatsApp Now</Text>
              </TouchableOpacity>
            </View>

            {/* Banners */}
            <FlatList
              data={banners}
              horizontal
              pagingEnabled
              showsHorizontalScrollIndicator={false}
              keyExtractor={(item) => item.id.toString()}
              renderItem={({ item }) => (
                <Image source={{ uri: item.image }} style={styles.banner} />
              )}
            />

            {/* Categories */}
            <View style={styles.section}>
              <Text style={styles.sectionTitle}>Product Categories</Text>
              <View style={styles.categoriesGrid}>
                {categories.map((category) => (
                  <View key={category.id} style={styles.categoryWrapper}>
                    {renderCategory({ item: category })}
                  </View>
                ))}
              </View>
            </View>

            {/* Trust Highlights */}
            <View style={styles.section}>
              <Text style={styles.sectionTitle}>Why Choose Us?</Text>
              <View style={styles.trustItem}>
                <Text style={styles.trustText}>✓ Quality Products</Text>
              </View>
              <View style={styles.trustItem}>
                <Text style={styles.trustText}>✓ Competitive Prices</Text>
              </View>
              <View style={styles.trustItem}>
                <Text style={styles.trustText}>✓ Fast Delivery</Text>
              </View>
              <View style={styles.trustItem}>
                <Text style={styles.trustText}>✓ Payment After Delivery</Text>
              </View>
            </View>
          </View>
        )}
        keyExtractor={(item) => item.key}
        showsVerticalScrollIndicator={false}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  languageToggle: {
    flexDirection: 'row',
    justifyContent: 'flex-end',
    padding: 10,
  },
  languageButton: {
    paddingHorizontal: 15,
    paddingVertical: 5,
    marginHorizontal: 5,
    backgroundColor: '#34495e',
    borderRadius: 5,
  },
  languageText: {
    color: '#fff',
    fontWeight: 'bold',
  },
  quickActions: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    padding: 15,
  },
  actionButton: {
    backgroundColor: '#e74c3c',
    paddingHorizontal: 20,
    paddingVertical: 10,
    borderRadius: 5,
    flex: 1,
    marginHorizontal: 5,
  },
  actionButtonText: {
    color: '#fff',
    textAlign: 'center',
    fontWeight: 'bold',
  },
  banner: {
    width: width,
    height: 200,
  },
  section: {
    padding: 15,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 10,
    color: '#2c3e50',
  },
  categoriesGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'space-between',
    paddingBottom: 10,
  },
  categoryWrapper: {
    width: '48%', // Two columns with spacing
    marginBottom: 10,
  },
  categoryCard: {
    flex: 1,
    backgroundColor: '#fff',
    margin: 5,
    borderRadius: 8,
    padding: 10,
    alignItems: 'center',
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  categoryImage: {
    width: 80,
    height: 80,
    borderRadius: 40,
    marginBottom: 10,
  },
  categoryName: {
    fontSize: 14,
    fontWeight: 'bold',
    textAlign: 'center',
    color: '#2c3e50',
  },
  trustItem: {
    backgroundColor: '#fff',
    padding: 10,
    marginVertical: 2,
    borderRadius: 5,
  },
  trustText: {
    fontSize: 16,
    color: '#27ae60',
  },
});

export default HomeScreen;