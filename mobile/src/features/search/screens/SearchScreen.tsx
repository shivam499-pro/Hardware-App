import React, { useState, useEffect, useCallback, useRef } from 'react';
import {
  View,
  Text,
  StyleSheet,
  TextInput,
  FlatList,
  TouchableOpacity,
  Image,
  ActivityIndicator,
  Keyboard,
} from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { useDispatch } from 'react-redux';
import Icon from 'react-native-vector-icons/Ionicons';
import { RootStackParamList } from '../../../navigation';
import { productService, Product, categoryService, Category } from '../../../services/api';
import { addToCart } from '../../../store/slices/cartSlice';

type SearchScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Home'>;

interface Props {
  navigation: SearchScreenNavigationProp;
}

const SearchScreen: React.FC<Props> = ({ navigation }) => {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState<Product[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState(false);
  const [hasSearched, setHasSearched] = useState(false);
  const dispatch = useDispatch();
  const debounceTimer = useRef<ReturnType<typeof setTimeout> | null>(null);

  // Load categories on mount for quick-chip display
  useEffect(() => {
    const loadCategories = async () => {
      try {
        const data = await categoryService.getAllCategories();
        setCategories(data);
      } catch (err) {
        console.error('Failed to load categories for search chips:', err);
      }
    };
    loadCategories();
  }, []);

  // Debounced search
  const handleSearch = useCallback((text: string) => {
    setQuery(text);

    if (debounceTimer.current) {
      clearTimeout(debounceTimer.current);
    }

    if (text.trim().length < 2) {
      setResults([]);
      setHasSearched(false);
      return;
    }

    debounceTimer.current = setTimeout(async () => {
      setLoading(true);
      setHasSearched(true);
      try {
        const response = await productService.searchProducts(text.trim(), undefined, { size: 20 });
        setResults(response.content || []);
      } catch (err) {
        console.error('Search error:', err);
        setResults([]);
      } finally {
        setLoading(false);
      }
    }, 400);
  }, []);

  const handleCategoryChip = (category: Category) => {
    navigation.navigate('Category', {
      categoryId: category.id,
      categoryName: category.name,
    });
  };

  const handleProductPress = (product: Product) => {
    navigation.navigate('ProductDetail', { productId: product.id });
  };

  const handleAddToCart = (product: Product) => {
    const productName = product.translations?.[0]?.name || product.brand || 'Product';
    dispatch(addToCart({
      productId: product.id,
      productName,
      imageUrl: product.imageUrl,
      quantity: '1',
    }));
  };

  const getProductName = (product: Product): string => {
    return product.translations?.[0]?.name || product.brand || 'Unknown Product';
  };

  const renderProduct = ({ item }: { item: Product }) => (
    <TouchableOpacity style={styles.productCard} onPress={() => handleProductPress(item)}>
      <Image
        source={{ uri: item.imageUrl || 'https://via.placeholder.com/80x80?text=Product' }}
        style={styles.productImage}
      />
      <View style={styles.productInfo}>
        <Text style={styles.productName} numberOfLines={2}>{getProductName(item)}</Text>
        <Text style={styles.productBrand}>{item.brand}</Text>
      </View>
      <TouchableOpacity style={styles.addCartBtn} onPress={() => handleAddToCart(item)}>
        <Icon name="cart-outline" size={22} color="#fff" />
      </TouchableOpacity>
    </TouchableOpacity>
  );

  return (
    <View style={styles.container}>
      {/* Search Bar */}
      <View style={styles.searchBarContainer}>
        <Icon name="search-outline" size={20} color="#7f8c8d" style={styles.searchIcon} />
        <TextInput
          style={styles.searchInput}
          placeholder="Search products, brands, categories..."
          value={query}
          onChangeText={handleSearch}
          returnKeyType="search"
          autoCorrect={false}
        />
        {query.length > 0 && (
          <TouchableOpacity onPress={() => { setQuery(''); setResults([]); setHasSearched(false); }}>
            <Icon name="close-circle" size={20} color="#bdc3c7" />
          </TouchableOpacity>
        )}
      </View>

      {/* Content */}
      {loading ? (
        <ActivityIndicator size="large" color="#3498db" style={{ marginTop: 40 }} />
      ) : !hasSearched ? (
        /* Quick Category Chips */
        <View style={styles.chipsSection}>
          <Text style={styles.chipsTitle}>Browse by Category</Text>
          <View style={styles.chipsContainer}>
            {categories.map((cat) => (
              <TouchableOpacity
                key={cat.id}
                style={styles.chip}
                onPress={() => handleCategoryChip(cat)}
              >
                <Text style={styles.chipText}>{cat.name}</Text>
              </TouchableOpacity>
            ))}
          </View>
        </View>
      ) : results.length === 0 ? (
        /* No Results */
        <View style={styles.emptyContainer}>
          <Icon name="search" size={60} color="#bdc3c7" />
          <Text style={styles.emptyText}>No products found for "{query}"</Text>
          <Text style={styles.emptySubtext}>Try a different keyword or browse categories below</Text>
        </View>
      ) : (
        /* Search Results */
        <FlatList
          data={results}
          keyExtractor={(item) => item.id.toString()}
          renderItem={renderProduct}
          contentContainerStyle={styles.resultsList}
          keyboardShouldPersistTaps="handled"
          ListHeaderComponent={
            <Text style={styles.resultsCount}>{results.length} result{results.length !== 1 ? 's' : ''} found</Text>
          }
        />
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#f5f5f5' },
  searchBarContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#fff',
    margin: 12,
    paddingHorizontal: 12,
    borderRadius: 10,
    elevation: 3,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  searchIcon: { marginRight: 8 },
  searchInput: { flex: 1, paddingVertical: 12, fontSize: 16, color: '#2c3e50' },

  chipsSection: { padding: 15 },
  chipsTitle: { fontSize: 18, fontWeight: 'bold', color: '#2c3e50', marginBottom: 15 },
  chipsContainer: { flexDirection: 'row', flexWrap: 'wrap' },
  chip: {
    backgroundColor: '#fff',
    paddingHorizontal: 16,
    paddingVertical: 10,
    borderRadius: 20,
    marginRight: 10,
    marginBottom: 10,
    elevation: 2,
    borderWidth: 1,
    borderColor: '#ecf0f1',
  },
  chipText: { fontSize: 14, color: '#2c3e50', fontWeight: '500' },

  resultsList: { paddingHorizontal: 12, paddingBottom: 20 },
  resultsCount: { fontSize: 14, color: '#7f8c8d', marginBottom: 10, marginLeft: 4 },
  productCard: {
    flexDirection: 'row',
    backgroundColor: '#fff',
    borderRadius: 10,
    padding: 12,
    marginBottom: 10,
    alignItems: 'center',
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.08,
    shadowRadius: 3,
  },
  productImage: { width: 65, height: 65, borderRadius: 8 },
  productInfo: { flex: 1, marginLeft: 12 },
  productName: { fontSize: 15, fontWeight: 'bold', color: '#2c3e50' },
  productBrand: { fontSize: 13, color: '#7f8c8d', marginTop: 3 },
  addCartBtn: {
    backgroundColor: '#3498db',
    padding: 10,
    borderRadius: 8,
    marginLeft: 8,
  },

  emptyContainer: { alignItems: 'center', marginTop: 60, paddingHorizontal: 30 },
  emptyText: { fontSize: 18, fontWeight: 'bold', color: '#2c3e50', marginTop: 15, textAlign: 'center' },
  emptySubtext: { fontSize: 14, color: '#7f8c8d', marginTop: 8, textAlign: 'center' },
});

export default SearchScreen;
