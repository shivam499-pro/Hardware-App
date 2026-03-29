import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity, ActivityIndicator, Alert } from 'react-native';
import { useSelector, useDispatch } from 'react-redux';
import { StackNavigationProp } from '@react-navigation/stack';
import Icon from 'react-native-vector-icons/Ionicons';
import { RootState } from '../../../store';
import { logout } from '../../../store/slices/authSlice';
import { quoteService, QuoteRequest } from '../../../services/api';

type ProfileScreenNavigationProp = StackNavigationProp<any>; // Dynamic any because of Tab to Stack root config

interface Props {
  navigation: ProfileScreenNavigationProp;
}

const ProfileScreen: React.FC<Props> = ({ navigation }) => {
  const authState = useSelector((state: RootState) => state.auth);
  const isAuthenticated = authState?.isAuthenticated || false;
  const phone = authState?.phone || '';
  const fullName = authState?.fullName || '';
  const dispatch = useDispatch();
  
  const [history, setHistory] = useState<QuoteRequest[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (isAuthenticated && phone) {
      loadHistory();
    }
  }, [isAuthenticated, phone]);

  const loadHistory = async () => {
    setLoading(true);
    try {
      if (phone) {
        const data = await quoteService.getCustomerQuotesByPhone(phone);
        setHistory(data);
      }
    } catch (error) {
      console.error('Failed to load quote history:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    Alert.alert('Logout', 'Are you sure you want to log out?', [
      { text: 'Cancel', style: 'cancel' },
      { text: 'Log Out', style: 'destructive', onPress: () => dispatch(logout()) },
    ]);
  };

  const renderQuoteItem = ({ item }: { item: QuoteRequest }) => {
    let statusColor = '#f39c12'; // pending
    if (item.status === 'contacted') statusColor = '#3498db';
    if (item.status === 'completed') statusColor = '#2ecc71';

    return (
      <View style={styles.quoteCard}>
        <View style={styles.quoteCardHeader}>
          <Text style={styles.quoteItemName}>{item.quantity} units</Text>
          <View style={[styles.statusBadge, { backgroundColor: statusColor }]}>
            <Text style={styles.statusText}>{item.status?.toUpperCase() || 'PENDING'}</Text>
          </View>
        </View>
        <Text style={styles.quoteDetails}>Submitted: {new Date(item.createdAt || '').toLocaleDateString()}</Text>
        <Text style={styles.quoteDetails}>Delivery: {item.location}</Text>
      </View>
    );
  };

  if (!isAuthenticated) {
    return (
      <View style={styles.guestContainer}>
        <Icon name="person-circle-outline" size={100} color="#bdc3c7" />
        <Text style={styles.guestTitle}>Your Personalized Hub</Text>
        <Text style={styles.guestSubtitle}>Log in to automatically fill checkout details and track your past hardware quote history.</Text>
        <TouchableOpacity style={styles.primaryButton} onPress={() => navigation.navigate('Login')}>
          <Text style={styles.buttonText}>Log In</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.secondaryButton} onPress={() => navigation.navigate('Register')}>
          <Text style={styles.secondaryButtonText}>Create Account</Text>
        </TouchableOpacity>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <View style={styles.headerArea}>
        <View>
          <Text style={styles.welcomeText}>Hello, {fullName}</Text>
          <Text style={styles.phoneText}>{phone}</Text>
        </View>
        <TouchableOpacity onPress={handleLogout} style={styles.logoutBtn}>
          <Icon name="log-out-outline" size={28} color="#e74c3c" />
        </TouchableOpacity>
      </View>

      <Text style={styles.sectionTitle}>Your Quote History</Text>
      
      {loading ? (
        <ActivityIndicator size="large" color="#3498db" style={{ marginTop: 20 }} />
      ) : history.length === 0 ? (
        <View style={styles.emptyBox}>
          <Text style={styles.emptyText}>You haven't requested any quotes yet.</Text>
        </View>
      ) : (
        <FlatList
          data={history}
          keyExtractor={(item) => item.id?.toString() || Math.random().toString()}
          renderItem={renderQuoteItem}
          contentContainerStyle={{ paddingBottom: 20 }}
          onRefresh={loadHistory}
          refreshing={loading}
        />
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#f5f5f5', padding: 15 },
  headerArea: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 25, backgroundColor: '#fff', padding: 15, borderRadius: 10, elevation: 2 },
  welcomeText: { fontSize: 20, fontWeight: 'bold', color: '#2c3e50' },
  phoneText: { fontSize: 14, color: '#7f8c8d', marginTop: 3 },
  logoutBtn: { padding: 5 },
  sectionTitle: { fontSize: 18, fontWeight: 'bold', color: '#2c3e50', marginBottom: 15 },
  quoteCard: { backgroundColor: '#fff', padding: 15, borderRadius: 8, marginBottom: 10, elevation: 1 },
  quoteCardHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 10 },
  quoteItemName: { fontSize: 16, fontWeight: 'bold', color: '#34495e' },
  statusBadge: { paddingHorizontal: 8, paddingVertical: 4, borderRadius: 4 },
  statusText: { color: '#fff', fontSize: 12, fontWeight: 'bold' },
  quoteDetails: { fontSize: 14, color: '#7f8c8d', marginTop: 2 },
  emptyBox: { alignItems: 'center', marginTop: 40 },
  emptyText: { color: '#bdc3c7', fontSize: 16 },

  guestContainer: { flex: 1, backgroundColor: '#f5f5f5', justifyContent: 'center', alignItems: 'center', padding: 20 },
  guestTitle: { fontSize: 22, fontWeight: 'bold', color: '#2c3e50', marginTop: 20, marginBottom: 10 },
  guestSubtitle: { fontSize: 16, color: '#7f8c8d', textAlign: 'center', marginBottom: 40, paddingHorizontal: 20 },
  primaryButton: { backgroundColor: '#3498db', width: '100%', padding: 15, borderRadius: 8, alignItems: 'center', marginBottom: 15 },
  buttonText: { color: '#fff', fontSize: 18, fontWeight: 'bold' },
  secondaryButton: { backgroundColor: '#fff', width: '100%', padding: 15, borderRadius: 8, alignItems: 'center', borderWidth: 1, borderColor: '#3498db' },
  secondaryButtonText: { color: '#3498db', fontSize: 18, fontWeight: 'bold' },
});

export default ProfileScreen;
